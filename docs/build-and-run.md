# Build & Run in Docker (Single Core)

End-to-end commands to compile the project, build the Docker image, and run the
container pinned to **1 CPU core**.

The full workflow is scripted in [`docker-containerization.sh`](../docker-containerization.sh):

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
