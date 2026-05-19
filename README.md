# ui-api-selenium

A Java-based test automation framework for UI and API tests, built on Selenium, RestAssured, and TestNG. Designed with scalability and team adoption in mind — a single-language, single-repository platform that any engineer can contribute to without QA specialisation.

Target application: [fundingsocieties.com](https://fundingsocieties.com) (UI) · [reqres.in](https://reqres.in) (API)

---

## Framework Design

### Architecture

```
src/test/java/
  common/                     ← infrastructure layer (shared, opinionated core)
    driver/                   ← WebDriver lifecycle and thread isolation
    apis/                     ← HTTP base client and service-specific clients
    utils/                    ← Selenium actions, JSON, sorting helpers
    cleanup/                  ← test data teardown
    AutomationConfigs.java    ← singleton config reader
  acceptance_test/            ← UI abstraction + test layer
    pom/                      ← page objects and component objects
    BaseTest.java             ← TestNG hooks, driver initialisation
    FundingSocietiesTest.java ← UI test specs
  api_test/                   ← API abstraction + test layer
    BaseTest.java             ← TestNG hooks, API client initialisation
    UserManagementTest.java   ← API test specs
  data/                       ← test data files (per environment)
  pojos/                      ← typed response models
  parallelRun.xml             ← TestNG parallel execution suite
src/resources/
  config.properties           ← environment config
```

### Layer 1 — Infrastructure (`common/`)

Owns everything that should be consistent across teams: how drivers are managed, how HTTP calls are constructed, how configuration is read. These are the non-negotiables — changing them here changes them everywhere.

| Component | Responsibility |
|---|---|
| `AutomationConfigs` | Singleton config reader — reads `config.properties`, accessible globally |
| `DriverManager` / `DriverFactory` | WebDriver lifecycle: start, create, stop |
| `DriverStorage` | `ThreadLocal<WebDriver>` — isolates driver per thread for parallel safety |
| `DriverType` | Enum of supported browsers — add a new entry to support a new browser |
| `BaseAPIs` | HTTP method wrappers (GET, POST, PUT, PATCH, DELETE) with request/response logging |
| `Cleanup` | Removes test data created during test execution |

`DriverStorage` is the key enabler for parallel execution. By storing each driver in a `ThreadLocal`, workers never share state — no locking, no race conditions on driver access.

### Layer 2 — Abstraction (`pom/` and `apis/`)

Tests never call Selenium directly or construct raw HTTP requests. All interactions go through the abstraction layer.

**UI — Page Objects and Components**

```
acceptance_test/pom/
  BasePage.java               ← navigation helpers, common wait logic
  HomePage.java               ← home page actions
  StatisticPage.java          ← statistics page entry point
  component/
    GeneralTab.java           ← General tab chart interactions
    RepaymentTab.java         ← Repayment tab chart interactions
    DisbursementTab.java      ← Disbursement tab chart interactions
```

The component layer exists because the statistics page has embedded Highcharts v9.2.2 charts that require JavaScript interaction. Breaking each tab into its own component class isolates the fragile chart-interaction logic, so a change to one chart doesn't affect the others.

**API — Typed Service Clients**

```
common/apis/
  BaseAPIs.java               ← RestAssured wrapper with logging
  LoginAPIs.java              ← authentication, token storage
  UserAPIs.java               ← user resource CRUD (extends LoginAPIs)
```

`LoginAPIs` handles authentication once and stores the session token. `UserAPIs` injects the token into all subsequent requests. Tests call `userAPIs.createUser(payload)` — not raw HTTP.

**Typed Response Models**

```
pojos/
  UserResponse.java           ← Jackson-mapped API response model
```

Assertions are made against typed objects, not raw JSON strings.

### Layer 3 — Tests (`acceptance_test/` and `api_test/`)

Test specs are thin. Each test arranges its data, acts through the abstraction layer, and asserts on typed results. No Selenium calls, no raw HTTP, no config reads.

```java
// api_test/UserManagementTest.java
@Test
public void createNewUserSuccessful() {
    UserResponse created = userAPIs.createUser(testUser);

    Assert.assertEquals(created.getName(), testUser.getName());
    Assert.assertEquals(created.getJob(), testUser.getJob());
}
```

---

## Scalability & Platform Thinking

### Opinionated Core, Extensible Edges

The `common/` package is the shared core — teams depend on it, they don't fork it. It handles the things that must be consistent: driver management, HTTP base client, config, cleanup. These are not optional.

The edges are where teams have legitimate differences. New page objects live in feature-specific packages. New API clients extend `BaseAPIs`. New browser support means adding a `DriverType` entry and a `DriverManager` subclass. Teams extend through composition and inheritance off stable base classes — not by editing shared infrastructure.

### Parallel Execution and Thread Safety

Parallel test execution is configured in `parallelRun.xml`:

```xml
<suite name="Parallel Run" parallel="methods" thread-count="2">
```

Thread safety is enforced by `DriverStorage`:

```java
// common/driver/DriverStorage.java
private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
```

Each worker thread gets its own `WebDriver` instance. There is no shared mutable state across threads. Increasing `thread-count` scales execution horizontally without code changes.

To scale further — to a Kubernetes-based execution platform like Testkube — the same isolation principle applies. Each test pod gets its own driver and its own data scope. The `AutomationConfigs` singleton reads from environment variables in containerised environments, keeping infra config out of test code.

### Test Isolation and Data Ownership

Every test creates what it needs and cleans up after itself. The `Cleanup` class handles teardown as a TestNG `@AfterMethod` concern, not inside the test body. This means:

- Tests don't share database records or user sessions
- Parallel workers don't collide on test data
- A failing test doesn't leave state that breaks the next run

Test data is externalised in `data/qa/users.json` (per environment), deserialized into typed objects via `JsonUtils`. This separates data from test logic — changing test data doesn't require touching test code.

### Known Failure Mode: Flaky Chart Interactions

At scale, one flaky test becomes alert noise that teams learn to ignore — which is dangerous. The known flakiness in this framework is the `StaleElementReferenceException` on Highcharts pie chart elements:

```
org.openqa.selenium.StaleElementReferenceException: stale element reference:
element is not attached to the page document
```

The current mitigation is retry logic inside `SeleniumActionUtils`. The long-term fix in a scaled platform would be a flaky test detection pipeline: track pass rate per test over time, automatically quarantine tests that fall below a threshold, and file them as bugs rather than re-running indefinitely.

### Observability

Maven Surefire generates test results after each run (`target/surefire-reports`). For a scaled platform, the next layer is integrating JUnit XML output into a metrics pipeline — publishing flaky test rates, execution times, and coverage trends to a dashboard (Grafana, Datadog) so engineering managers see quality signals per squad, not just per-run pass/fail.

---

## Prerequisites

- [JDK 1.8+](https://www.oracle.com/java/technologies/downloads/)
- [Apache Maven 3.x](https://maven.apache.org/) — or use the bundled `apache-maven-3.8.6/` included in the repo
- [Git](https://git-scm.com/)
- Chrome (latest) — WebDriverManager handles driver version matching automatically

**Environment variables required before running:**

```bash
export JAVA_HOME=/path/to/jdk
export MAVEN_HOME=/path/to/maven   # or use ./apache-maven-3.8.6/bin/mvn
export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
```

---

## Local Setup

**1. Clone the repo**
```bash
git clone https://github.com/sdetannguyen/qa-assessment.git
cd ui-api-selenium
```

**2. Compile the project**
```bash
mvn compile
```

---

## Running Tests

**Run the API test suite**
```bash
mvn test -Dtest="api_test.UserManagementTest"
```

**Run the UI test suite**
```bash
mvn test -Dtest="acceptance_test.FundingSocietiesTest"
```

**Run both suites in parallel (2 threads)**
```bash
mvn test -DsuiteXmlFile="src/test/java/parallelRun.xml"
```

**UI test note:** During the run, do not hover your cursor over the statistics charts. Highcharts recalculates tooltip positions on hover and the element reference becomes stale, which can cause the chart interaction tests to fail.

---

## Configuration

`src/resources/config.properties`

| Key | Default | Description |
|---|---|---|
| `application.api.baseUrl` | `https://reqres.in` | Base URL for API tests |
| `application.ui.baseUrl` | `https://fundingsocieties.com` | Base URL for UI tests |
| `application.ui.waitTimeOut` | `60` | Implicit wait timeout in seconds |
| `application.env` | `qa` | Environment selector (controls which data folder is loaded) |
| `application.testUser1.username` | `eve.holt@reqres.in` | API test credential |
| `application.testUser1.password` | `cityslicka` | API test credential |
| `automation.browser` | `chrome` | Browser type (maps to `DriverType` enum) |

---

## Test Reports

Surefire generates reports after each run:

```
target/surefire-reports/
```

To view the HTML summary:
```bash
open target/surefire-reports/index.html
```

---

## Technology Stack

| Layer | Technology | Why |
|---|---|---|
| UI automation | Selenium WebDriver 4.6.0 | Broad browser support, extensive documentation, cloud platform integrations (BrowserStack, SauceLabs) |
| API automation | RestAssured 5.3.0 | Fluent DSL for HTTP, fits naturally in a Java test project |
| Test runner | TestNG 7.4.0 | Built-in parallel execution, flexible suite configuration |
| Driver management | WebDriverManager 5.3.1 | Eliminates manual ChromeDriver version management |
| Serialisation | Jackson 2.14.0 | Typed JSON-to-POJO mapping for API response assertions |
| Build | Maven 3.8.6 | Dependency management, lifecycle, Surefire reporting |

### Trade-offs

**Single language (Java) for both UI and API** — the framework lives in one repository with one language. An engineer contributing to API tests can read UI test code and vice versa. The cost is that RestAssured has a steeper learning curve than simpler HTTP clients.

**TestNG over Cucumber/BDD** — direct assertion-based tests are faster to write and easier to debug. The trade-off is that non-engineers can't read the specs without understanding code. BDD makes sense when product owners are actively involved in test authorship; here, the audience is engineers.

**Selenium for Highcharts** — Selenium is the right tool for cross-browser UI validation, but it struggles with JavaScript-heavy chart libraries. The component object pattern (`GeneralTab`, `RepaymentTab`, `DisbursementTab`) isolates this complexity so the unstable chart interaction code doesn't bleed into the stable page navigation code.
