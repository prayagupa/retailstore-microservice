# AGENTS.md — Agentic AI Instructions for retailstore-microservice

This file provides grounding context for any agentic AI system (Copilot Workspace,
Claude, GPT-Engineer, Devin, etc.) operating on this repository.

---

## Repository Topology

```
retailstore-microservice/
├── retailstore-rest-schema/     # Shared POJOs / schema JAR
├── retailstore-rest-server/     # Spring Boot application (main deliverable)
├── retailstore-rest-httpclient/ # Consumer HTTP client + Pact contract tests
├── devops/                      # Terraform, K8s manifests, Jenkins pipeline
├── docs/perf/                   # Benchmark results (ab output, analysis)
├── .github/workflows/           # GitHub Actions CI/CD
├── Dockerfile                   # eclipse-temurin:21-jre-alpine
└── docker-containerization.sh   # Full build + run script
```

---

## Non-Negotiable Constraints

An agent **must not** violate these without an explicit override from the user:

1. **Java 21 only.** Toolchain is pinned in `build.gradle` and `.sdkmanrc`. Do not downgrade.
2. **Gradle only.** Do not generate Maven wrapper, `pom.xml` goals, or Maven commands.
3. **Tomcat servlet stack.** Do not add `spring-boot-starter-webflux` or Netty as the primary server.
4. **`jakarta.*` namespace.** Spring Boot 4 targets Jakarta EE 10. `javax.*` imports will fail at compile time.
5. **Alpine user creation.** `useradd` does not exist on Alpine. Use `addgroup`/`adduser`.
6. **Schema module boundary.** Response/request types belong in `retailstore-rest-schema`. Never define them inside `retailstore-rest-server`.
7. **No coverage regression.** Jacoco thresholds are enforced in CI. Do not lower them.

---

## Preferred Patterns

### Dependency Injection
```java
// CORRECT — constructor injection
@RestController
public class ServiceController {
    private final RetailService retailService;

    public ServiceController(RetailService retailService) {
        this.retailService = retailService;
    }
}

// WRONG — field injection
@Autowired
private RetailService retailService;
```

### Concurrency (Java 21)
```java
// PREFERRED — virtual threads for I/O-bound work
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    executor.submit(() -> retailService.readDataBlocking(100));
}

// AVOID — platform thread pool for I/O tasks
ExecutorService pool = Executors.newFixedThreadPool(200);
```

### Logging
```java
// CORRECT
private static final Logger logger = LogManager.getLogger();

// WRONG
System.out.println(...);
private static final Logger log = LoggerFactory.getLogger(Foo.class); // SLF4J — not used here
```

### Data Transfer Objects
```java
// PREFERRED (Java 21 record)
public record HealthStatus(long timestamp, String applicationName, String applicationVersion) {}

// AVOID (Lombok on new code)
@Data
public class HealthStatus { ... }
```

---

## Running the Project

### Tests
```bash
./gradlew test jacocoTestReport
```

### Build (skip tests)
```bash
./gradlew build -x test -PprojectVersion=1.0.0
```

### Full Docker lifecycle (build JAR → build image → run on 1 CPU)
```bash
./docker-containerization.sh
```

### Manual run
```bash
docker run -it -p 8080:8080 --cpus=1 --memory=768m retailstore-microservice
```

### Verify
```bash
curl -s http://localhost:8080/retailstore/health-blocking | python3 -m json.tool
```

---

## Making Code Changes

### Adding a new endpoint
1. Define the request/response record in `retailstore-rest-schema/src/main/java/`.
2. Add business logic to a `@Service` class under `com.api.rest.service`.
3. Add the `@GetMapping` / `@PostMapping` in `ServiceController` or a new controller under `com.api.rest.endpoints`.
4. Add a `@Timed` annotation and register a `Counter` in `MeterRegistry` for observability.
5. Write a unit test under `retailstore-rest-server/src/test/java/` mirroring the source path.
6. Run `./gradlew test jacocoTestReport` and confirm coverage does not drop.

### Modifying Docker image
- Base image changes: update `FROM` in `Dockerfile`.
- User/permission changes: use `addgroup`/`adduser` syntax (Alpine BusyBox).
- Document container flag rationale in `docs/build-and-run.md`.

### Adding dependencies
- Add to `retailstore-rest-server/build.gradle` under the appropriate configuration (`implementation`, `testImplementation`).
- Check for CVEs before committing (`./gradlew dependencyCheckAnalyze` if OWASP plugin is present).
- Record significant additions in `gradle-dependencies.deps`.

### Performance changes
- Run benchmarks with `ab` against the Docker container (`--cpus=1 --memory=768m`).
- Document results in `docs/perf/` following the existing format (environment table, per-run throughput + latency tables, `<details>` for raw `ab` output, observations).

---

## CI Pipeline

`.github/workflows/cicd.yaml` runs on every push and PR:

```
checkout → setup JDK 21 (Temurin) → validate Gradle wrapper
    → build -x test → test + jacocoTestReport
    → upload coverage artifact → upload test results artifact
```

An agent that modifies the workflow must ensure:
- JDK version remains `21` with distribution `temurin`.
- `./gradlew build -x test` precedes `./gradlew test` (compile errors surface before test failures).
- Coverage and test artifacts are still uploaded with `if: always()`.

---

## Performance Baseline (single core, Docker)

| Stack | Req/sec @ 100K | p99 @ 100K |
|-------|---------------:|----------:|
| Java 12 / Spring Boot 2.5 / Tomcat 9 (2023) | 391 | 1 029 ms |
| Java 21 / Spring Boot 4.0 / Tomcat 11 (2026) | 21 978 | 13 ms |

A regression below 15K req/s at 100K requests on `GET /health-blocking-sync` is a signal
that something has introduced blocking on the request thread or excessive GC pressure.

---

## Key Files Quick Reference

| File | Purpose |
|------|---------|
| `build.gradle` | Root build — Java 21 toolchain, common plugins |
| `retailstore-rest-server/build.gradle` | Spring Boot 4.0, Jacoco, application config |
| `settings.gradle` | Multi-project wiring, build cache config |
| `Dockerfile` | Container image definition |
| `docker-containerization.sh` | End-to-end build + run + teardown script |
| `.github/copilot-instructions.md` | GitHub Copilot custom instructions |
| `.cursorrules` | Cursor IDE rules |
| `google_checks.xml` | Checkstyle ruleset |
| `docs/perf/README.md` | I/O model theory + benchmark index |
| `docs/perf/2026/README.md` | 2026 benchmark results with 2023 comparison |
| `docs/build-and-run.md` | Build, Docker, and verification guide |
| `devops/Jenkinsfile` | Jenkins declarative pipeline |
| `devops/rest-api.tf` | Terraform infrastructure definition |

