### Prerequisites
- follow [12 factors app rules](https://12factor.net)
- cover 100% of code with unit tests in modules (except for facade)
- keep facade dumb and lightweight (as a glue for joining and configuring code from internal modules)

---

### Project structure
  - `.` - root module for aggregation and simple build start
  - `modules/base` - module for sharing build routines among all sibling modules (here reside all common dependencies, plugins, etc.)
  - `modules/toolbox` - module for mics tools and utils
  - `modules/core` - module for business logic isolation
  - `modules/face` - module for developing web API and configuring interactions with external services


### Materials
Additional materials are placed in `materials` directory
- `code_quality` - configuration of code quality tools
   - `checkstyle_java_config.xml` can be converted to IDE codestyle.


### Build and run

##### build and run in Docker way
- `make` - Build and run the application locally
- `make destroy` - Remove application
- `make logs` - Get application's logs
- `make root` - Go into the container with the application
- `make show` - Show application status
- `make stop` - Stop application

**`docker`, `docker-compose` and `make` packages are required**

##### build artifact
- `mvn clean package` \
  *produces main artifact in `modules/face/target`*

##### run facade locally
- `mvn clean install` \
  *!!! ensure the modules that `facade` depends on are installed in the local repo*
- `mvn clean spring-boot:run -pl modules/face -D spring-boot.run.profiles=local` \
  *start application on port 8080*

##### run facade locally in debug mode
- similar to section [run facade](#run-facade)
- `mvn clean spring-boot:run -pl modules/face -D spring-boot.run.profiles=local -P debug` \
  *start application on port 8443 eligible for debugging connections* \
  *with `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005`*

---

### TODOs
1. Facade is a SpringBoot application
   and [nobody uses modules with SpringBoot](https://stackoverflow.com/questions/60632148). \
   When in Maven modular project at least one module is not a JavaModule,
   then [all modules can access internal parts of each other](https://stackoverflow.com/questions/61888059). \
   In fact, it is a bug of Maven (javac works fine here),
   so it is expected that developers respect modules encapsulation, although it cannot be enforced at compile time.\
   **TODO:** Fix the problem or find a workaround to check modular encapsulation in compile time.

2. JUnit 5 allows omitting `public` modiufier from class and test methods. \
   Unfortunately, [in modular projects tests still need this boilerplate word](https://stackoverflow.com/questions/65218523). \
   **TODO:** Find a way to remove `public` from test mathods and keep the `core` subproject modular.

   **SOLUTION**:
   0. this solution is tricky, but it works (in maven and in IDEA); modules underlined with red by IDEA a bit annoying \
      SO A BETTER SOLUTION IS STILL WECLOME
   1. use different root package in src/test, i.e. `test.supercalifragilistic`
   2. add src/test/java/module-info.java
        ```
        open module test.supercalifragilistic.sample {

          requires org.junit.jupiter.api;
          requires org.junit.jupiter.engine;
          requires org.junit.jupiter.params;

          requires my_project.sample;
        }
        ```

3. PMD does not allow overriding rules. Every ruleset seems to be executed independently.
   Currently, there's no obvious way to [exclude rules when having multiple rulesets](https://stackoverflow.com/questions/65373000/)
   **TODO:** Find a way to override rules properties (i.e. priority or include/exclude) while using multiple rulesets (files).
