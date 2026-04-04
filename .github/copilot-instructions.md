# GitHub Copilot Instructions

## Repository Overview

`retailstore-microservice` is a Java 21 / Spring Boot 4.0 REST microservice with three Gradle subprojects:

| Module | Role |
|--------|------|
| `retailstore-rest-schema` | Shared domain/schema POJOs, published as a JAR |
| `retailstore-rest-server` | Spring Boot application — controllers, services, config |
| `retailstore-rest-httpclient` | Consumer-side HTTP client + Pact contract tests |

---

## Language & Runtime

- **Java 21** (Temurin `21.0.2-tem` via `.sdkmanrc`)
- Use **records** for immutable data transfer objects.
- Use **sealed interfaces / pattern matching** where it improves clarity.
- Use **`var`** only when the type is obvious from the right-hand side.
- Prefer **`Thread.ofVirtual()`** over raw `ExecutorService` for I/O-bound concurrency.
- **Never** use raw types. Always parameterise generics.

## Framework

- **Spring Boot 4.0.x** — use constructor injection, never `@Autowired` on fields.
- Endpoint mapping lives in `com.api.rest.endpoints`.
- Business logic lives in `com.api.rest.service` — keep controllers thin.
- Schema/response types live in `retailstore-rest-schema` — never define response POJOs inside the server module.
- Use `@Value` for scalar config; use `@ConfigurationProperties` for grouped config.
- Metrics via **Micrometer** (`MeterRegistry`). Prefer `@Timed` on endpoints.
- Rate limiting via **Resilience4j** (`RateLimiterRegistry`).
- Logging via **Log4j 2** (`LogManager.getLogger()`). Never use `System.out`.

## Build

- Build tool: **Gradle** (wrapper `./gradlew`). Do not suggest Maven commands unless explicitly asked.
- Run tests: `./gradlew test jacocoTestReport`
- Full build (skip tests): `./gradlew build -x test`
- Coverage report: `retailstore-rest-server/build/jacocoHtml/index.html`
- Version is injected at build time: `-PprojectVersion=<version>`

## Docker

- Base image: `eclipse-temurin:21-jre-alpine`
- User: `retailuser` (Alpine — use `addgroup`/`adduser`, never `useradd`)
- Single-core constraint: `--cpus=1 --memory=768m`
- Full build + run: `./docker-containerization.sh`

## Code Style

- Follow `google_checks.xml` (Checkstyle).
- Suppress only with `@SuppressWarnings` + Sonar rule ID (e.g. `"java:S117"`).
- No wildcard imports.
- `final` on method parameters and local variables where reassignment is not needed.
- Max method length: 20 lines. Extract helpers rather than adding inline comments.

## Testing

- Unit tests: JUnit 5 + Mockito. Mirror the source tree under `src/test/java`.
- Contract tests: Pact (consumer side in `retailstore-rest-httpclient`). Pact files live in `target/pacts/`.
- Coverage threshold: enforce via Jacoco in CI — do not lower existing thresholds.
- Test class naming: `<Subject>Test.java` for unit, `<Subject>ContractTest.java` for Pact.

## CI/CD

- GitHub Actions workflow: `.github/workflows/cicd.yaml`
- Pipeline: `build (skip tests)` → `test + jacoco` → `upload coverage artifact`
- Do not modify the workflow without also updating `devops/CIPipeline.groovy`.

## Performance

- Benchmark results live in `docs/perf/`. Keep raw `ab` output in `<details>` blocks.
- Single-core baseline: ~22K req/s at 100K requests (Java 21 + Tomcat 11, Keep-Alive on).
- Do not add blocking `Thread.sleep` outside of test/benchmark code paths.

## What NOT to do

- Do not add `lombok` usages beyond `@Getter`/`@Builder` — the team is migrating to records.
- Do not switch the build to Maven.
- Do not downgrade the Java toolchain below 21.
- Do not introduce `javax.*` imports — this project targets `jakarta.*` (Spring Boot 4 / Jakarta EE 10).
- Do not add `spring-boot-starter-webflux` — the server uses the Tomcat (servlet) stack intentionally.

