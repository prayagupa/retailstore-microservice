# Build & Run in Docker (Single Core)

End-to-end commands to compile the project, build the Docker image, and run the
container pinned to **1 CPU core**.

The full workflow is scripted in [`docker-containerization.sh`](../docker-containerization.sh):

## Tomcat 11 Config

```ini
server.tomcat.threads.max=4
server.tomcat.accept-count=1000000
```

---

### `server.tomcat.threads.max=4`

**What it does:** caps the maximum number of worker threads Tomcat will create to serve requests.

| Aspect | Assessment |
|--------|------------|
| Alignment with deployment | ✅ Intentional — container is pinned to `--cpus=1`, so >4 threads would just context-switch and waste CPU |
| Default (Tomcat 11 / Spring Boot 4) | Default is **200**. Setting 4 is a deliberate single-core tuning |
| Missing: `server.tomcat.threads.min-spare` | ⚠️ Not set — defaults to **10**, meaning Tomcat pre-warms **10 threads** at startup even though max is 4. Set `server.tomcat.threads.min-spare=2` or `=4` to stop idle threads above your max |
| Virtual threads (Tomcat 10.1.x+) | ℹ️ Tomcat 11 supports virtual thread executor via `server.tomcat.use-virtual-threads=true` (Spring Boot 3.2+). Not enabled here — the 4-thread platform pool is deliberate per the blocking servlet stack |
| Reference comment is stale | ⚠️ Comment links to the Tomcat **8.5** executor doc. Should point to [Tomcat 11 docs](https://tomcat.apache.org/tomcat-11.0-doc/config/executor.html) |

---

### `server.tomcat.accept-count=1000000`

**What it does:** sets the OS-level backlog queue length for connections waiting for a free thread.

| Aspect | Assessment |
|--------|------------|
| Value | ⚠️ 1,000,000 is effectively unbounded. With only 4 worker threads, a queue this deep means clients will wait indefinitely under load rather than getting a fast `503`. This masks backpressure |
| Recommended | A value proportional to expected burst — e.g. `1000`–`10000` — so overload is visible and clients fail-fast. The perf benchmark shows ~22K req/s max, so a queue of `50000`–`100000` is a more honest ceiling |
| Reference comment is stale | ⚠️ Links to Spring Boot **3.0.5** source. The property is unchanged in Boot 4 but the reference should be updated |

---

### Missing configs worth adding for Tomcat 11 on a single core

| Property | Suggested value | Reason |
|----------|----------------|--------|
| `server.tomcat.threads.min-spare` | `2` | Don't pre-warm more idle threads than your max |
| `server.tomcat.connection-timeout` | `5000` (ms) | Prevent slow-client threads from being held open indefinitely |
| `server.tomcat.max-connections` | `200`–`500` | Caps total open NIO connections on the connector (default 8192), appropriate for a single-core container |
| `server.tomcat.keep-alive-timeout` | `20000` (ms) | Explicit Keep-Alive drain time; currently relying on Tomcat default |

```bash
./docker-containerization.sh
```

The script does the following in order:

1. **Stops & removes** any running `retailstore-microservice` container and image.
2. **Builds the JAR** — `./gradlew build`
3. **Builds the Docker image** — `docker build -t retailstore-microservice .`  
   Base image: `eclipse-temurin:21-jre-alpine`
4. **Runs the container** on a single core:
   ```bash
   docker run -it -p 8080:8080 --cpus=1 --memory=768m retailstore-microservice
   ```
5. **Tears down** the container and image on exit.

| Flag | Description |
|------|-------------|
| `--cpus=1` | Restrict container to 1 CPU core |
| `--memory=768m` | Cap heap + off-heap to 768 MB |
| `-p 8080:8080` | Map host port 8080 → container port 8080 |

> **Tip:** pass `-e SPRING_PROFILES_ACTIVE=dev` to activate a Spring profile.

---

## Verify the service is up

```bash
curl -s http://localhost:8080/retailstore/health-blocking | python3 -m json.tool
```

Expected response:

```json
{
    "timestamp": 1672433750,
    "applicationName": "retailstore-rest",
    "applicationVersion": "1.0"
}
```

---

## See Also

- [Performance results](perf/README.md)
- [Deployment & K8s](../devops/README.md)
