# restpro

Test RestAPIS like a PRO.

![build status](https://img.shields.io/github/actions/workflow/status/pramodkumaryadav/restpro/trigger-tests-on-pull-request.yml?logo=GitHub)
![open issues](https://img.shields.io/github/issues/PramodKumarYadav/restpro)
![forks](https://img.shields.io/github/forks/PramodKumarYadav/restpro)
![stars](https://img.shields.io/github/stars/PramodKumarYadav/restpro)
![license](https://img.shields.io/github/license/PramodKumarYadav/restpro?style=flat-square)
![languages](https://img.shields.io/github/languages/count/pramodkumaryadav/restpro)

![info](https://img.shields.io/static/v1?label=with-love&message=from-power-tester&color=blue?style=plastic&logo=appveyor)

> NOTE: If you see errors while running the project, make sure that you have 
> completed the getting started steps from below section (especially decrypting 
> encrypted config files using `git-crypt`.

### Requiring (one time) manual setup by user

1. [**JDK 11**](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html) - as language of choice for writing this test framework.
2. [**Maven 3.8.6+**](https://maven.apache.org/) - for project dependency management and running tests in CI.
3. [**git-crypt**](docs/README-GIT-CRYPT.md) - to encrypt/decrypt secret files.
4. [**pre-commit**](docs/README-CODE-FORMATTING.md) - To have code automatically and uniformly formatted (JAVA, JSON, XML, YAML).

## Tool Set

Key tools to be used in this core framework are:

- [x] **Java** (As the core programming language)
- [x] **Maven** (for automatic dependency management)
- [x] **Junit 5** (for assertions)
- [x] **RestAssured**  (library for Rest API automation)
- [x] **Slf4J/Log4J** (for logging interface and as a logging library)
- [x] **Typesafe** (for application configuration for multiple test environments)
- [x] **Git crypt** (for managing secrets)
- [ ] **Surefire** (for xml reports in CI)
- [ ] **Surefire Site plugin** (for html reports in CI)
- [x] **GitHub** (for version control)
- [x] **GitHub actions** (for continuous integration)
- [ ] **Faker library** (for generating random test data for different locales - germany, france, netherlands, english)
- [x] **Slack integration** (for giving notifications on pull requests)
- [ ] **Elastic and Kibana** (for test monitoring)
- [ ] **Docker** (for automating test framework's environment)
- [ ] **Powershell or bash Script** (for automating building test environment)
- [ ] **SonarQube/SonarLint** (for keeping your code clean and safe)
- [x] **Badges** (for a quick view on your project meta and build status)

## api-test-design

![api-test-design](./images/api-test-framework-design.png)

## end-to-end-test-workflow

![end-to-end-test-workflow](./images/end-to-end-test-workflow.png)

## References

- [Rest-assured](https://rest-assured.io/)
- [Application under test (restful-booker)](https://restful-booker.herokuapp.com/apidoc/index.html)
- [Info on HTTP Client](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html)
- [Exploring-http-syntax](https://www.jetbrains.com/help/idea/exploring-http-syntax.html)
- [http-response-handling-api-reference](https://www.jetbrains.com/help/idea/http-response-handling-api-reference.html)
- [Install HOCON Plugin on intellij to highlight .conf files](https://plugins.jetbrains.com/plugin/10481-hocon).