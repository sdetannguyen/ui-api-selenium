# QA Assessment - UI & API Automation 

Some simple test scripts related to UI and API Automation for https://fundingsocieties.com application



## How it works

The skeleton/framework built on top of [Selenium](https://www.selenium.dev/) for UI test, [RestAssured](https://rest-assured.io/) for API test, [TestNG](https://testng.org/doc/) for assertion and other supporting libraries. The base build tool is [Maven](https://maven.apache.org/) to manage project's build, artifacts, reporting system and Java as following the main programing language.

## Required installation

- [JDK 1.8](https://www.oracle.com/java/technologies/downloads/) or higher
- [Apache Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)


## API test instruction

- Compile the project

```
mvn compile
```

- Run the API test suite by single thread

```
mvn test -Dtest="api_test.UserManagementTest"
```

## UI test instruction

- Compile the project

```
mvn compile
```

- Run the UI test suite by single thread

```
mvn test -Dtest="acceptance_test.FundingSocietiesTest"
```

## Parallel run command

```
mvn test -DsuiteXmlFile="src/test/java/parallelRun.xml"
```

### Pros and cons

#### Pros
- We use RestAssured for API test so the framework is centralized with one repository and one programing language. This helps the engineer easy to implement and getting familiar with automation testing more faster.
- With API test written by RestAssured, it can be reuseable by using common API script, create/storing pre-condition data or create/storing cookie/sessions to support the UI Test running more faster.
- We use Selenium for UI test and it has a ton of document on the internet that helps the junior/fresher engineer quickly catchup on how to work with the framework and UI automation. Beside that, Selenium supports many browsers and cloud testing platforms (BrowserStack, SauceLabs...) for automated testing pipeline.

#### Cons
- Time consuming with API test implementation using RestAssured due to the engineer should understand the core components, common function stuffs before start to implement 
- The framework built from scatch so it does support some great stuffs like reporting system, wrappers, tools, BDD...
- The testing scripts written by Selenium are not easy to handle. Especially, some UI components that only able to interact by javascript, jquery or hidden elements.




