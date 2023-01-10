# UI & API Automation with Selenium 

Some simple test scripts related to UI and API Automation for https://fundingsocieties.com application



## How it works

The skeleton/framework built on top of [Selenium](https://www.selenium.dev/) for UI test, [RestAssured](https://rest-assured.io/) for API test, [TestNG](https://testng.org/doc/) for assertion and other supporting libraries. The base build tool is [Maven](https://maven.apache.org/) to manage project's build, artifacts, reporting system and Java as following the main programing language.

## Required installation

- [JDK 1.8](https://www.oracle.com/java/technologies/downloads/) or higher
- [Apache Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)

## Supported Browser/OS
- Chrome (latest version) [WebDriverManager](https://github.com/bonigarcia/webdrivermanager) for driver management and versions.
- Test script has been tested and ran on Window 10 and MacOS.

## UI & API test instruction

- Clone the source code and access the source code
```
git clone https://github.com/sdetannguyen/qa-assessment.git
```
```
cd [sourceCode]
```

- Compile the project

```
mvn compile
```

- Run the API test suite by single thread

```
mvn test -Dtest="api_test.UserManagementTest"
```

- Run the UI test suite by single thread

```
mvn test -Dtest="acceptance_test.FundingSocietiesTest"
```

- Run parallel (the current config will trigger all UI & API test suites)

```
mvn test -DsuiteXmlFile="src/test/java/parallelRun.xml"
```

*Note:* 
  * Please make sure your local machine is completed the setup for JAVA_HOME and MAVEN_HOME environment variables before running the test
  * When running the test, if the cursor hover on the UI charts then the test could be failed due to getting the incorrect chart point so please move the cursor out of the chart.

## Problems and solutions

### API Test

- **Test cases:** The API Test simulate the full workflow start from user logins successfully, store the response's' token to header and try then to create/get/update/delete a new user on [Reqres](https://reqres.in/). At the end, verify the status codes, response body by specific properties or response's object comparation.

- **Problem:**
   * we have one issue with the API at [here](https://github.com/sdetannguyen/qa-assessment/blob/696512ae4e7473d166dcbedabca29f60e0198d7b/src/test/java/api_test/UserManagementTest.java#L40), the issue seems related to the application.
   * The parallelism for current API test doesn't support for testing data, methods so in the real case, it may will failed if we enrich our testing scripts

- **Solution:** We use normal approach to write the API test like common REST APIs, specific/business logic APIs, Utils to convert json file to object, configuration handler for common config. Some details implementation:
   * `BaseAPIs` - Common REST APIs protocols and logging 
   * `LoginAPIs` `UserAPIs` - Specific APIs that extended the BaseAPIs
   * `Cleanup` - To cleanup test data after completing test
   * `AutomationConfigs` - To read the config from config.properties, available at global level and singleton
   * Parallelism using `TestNG Parallel Execution` feature

### UI Test

- **Test cases:** The UI test cases includes all test cases from the assessment document

- **Problem:**
   * We need to interact with UI chart (rendering by Highcharts v9.2.2) so the Selenium scripts should be developed carefully especially with some elements need to handle by javascript/jquery.
   * The parallelism for UI is more complicated than other due to we need to handle the Selenium driver (get, set) by multiple threads, isolate testing data, common function
   * Flaky could be remaining if we trigger the test by parallelism. The known issue related to stale element when catching elments on pie charts. We already added retry at the selenium steps but it still be a risk in the future. Logs for issue

```
org.openqa.selenium.StaleElementReferenceException: stale element reference: element is not attached to the page document
```

- **Solution:** We use normal approach to write the UI test Page Object Model, Driver Manager, Selenium Utils, configuration handler for common config. Some details implementation:
   * `POM` - `BasePage` `HomePage` `StatisticPage` contains web elements and actions on specific/common page
   * `DriverManager` `DriverFactory` `DriverStorage` - to init, manage drivers
   * `SeleniumActionUtils` - contains Selenium compilation actions
   * `BaseTest` - contains TestNG hooks, driver(s) initialization
   * `AutomationConfigs` - To read the config from config.properties, available at global level and singleton
   * Parallelism using `TestNG Parallel Execution` feature and java `ThreadLocal` to handle the driver initialization 


## Pros and cons with selected tech stacks, trades off

#### Pros
- We use RestAssured for API test so the framework is centralized with one repository and one programing language. This helps the engineer easy to implement and getting familiar with automation testing more faster on both UI and API.
- With API test written by RestAssured, it can be reuseable by using common API script, create/store pre-condition data or create/store cookie/sessions to support the UI Test running more faster.
- We use Selenium for UI test and it has a ton of document on the internet that helps the engineer quickly catchup on how to work with the framework and UI automation. Beside that, Selenium supports many browsers and cloud testing platforms (BrowserStack, SauceLabs...) for automated testing pipeline.

#### Cons
- Time consuming with API test implementation using RestAssured due to the engineer should understand the core components, common function stuffs before start to implement.
- The framework built from scatch so it does support some great stuffs like reporting system, wrappers, tools, BDD...
- The testing scripts written by Selenium are not easy to handle. Especially, some UI components that only able to interact by javascript, jquery or hidden elements.

#### Personal views
- As a QA, before we start anything related to build up automation testing, we should consider the value that the tech stacks/framework can bring to our target application testing. Every framework/testing tools has their own pros and cons so we should be flexible and open minded to pick one. Cheers!


