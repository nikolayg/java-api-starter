# Unopinionated Java API Starter

This is a simple and unopiniated project starter using Java 17.

It is based on the following:
- The [Javalin](https://javalin.io/) embedded web server;
- [JUnit 5](https://junit.org/junit5/);
- Unit tests for the API endpoints with [JUnit 5](https://junit.org/junit5/) and [Mockito](https://site.mockito.org/);
- Integration / Feature tests with [JUnit 5](https://junit.org/junit5/) and [Javalin Test Tools](https://mvnrepository.com/artifact/io.javalin/javalin-testtools);
- [Gradle](https://gradle.org/) build automation;
- Code formatting with [Spotless](https://github.com/diffplug/spotless) via [Gradle Plugin](https://github.com/diffplug/spotless);
- Code scanning with [Spotbugs](https://spotbugs.github.io/) via [Gradle Plugin](https://github.com/spotbugs/spotbugs-gradle-plugin);
- [SonarQube](https://www.sonarsource.com/products/sonarqube/) integration via [Gradle Plugin](https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner-for-gradle/);
- Test coverage via [JaCoCo Gradle Plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
- Dependency vulnerability analysis via [OWASP Gradle plugin](https://plugins.gradle.org/plugin/org.owasp.dependencycheck);
- Externalisation with [Dotenv](https://github.com/cdimascio/dotenv-java);
- Docker build definition.


# Getting Started

[SDKMAN](https://sdkman.io/) is a utility for installing Java and related tools. After installing it, you can list and install a Java distribution:

```bash
# Install SDKMan
curl -s "https://get.sdkman.io" | bash

# View all available Java distributions:
sdk list java

# Install the latest Java 17 Temurin distro
# (Temurin used to be called AdoptOpenJDK)
sdk install java 17.0.8-tem
```

Now we can navigate into the application folder and run 
[gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) commands:

```bash
# Clean up, compile and rebuild
./gradlew clean build

# The above creates an executable uber jar file
# Checkout http://localhost:9090/api/health
java -jar ./app/build/libs/app.jar

# Run the JUnit5 tests
./gradlew test

# Run app locally
# Checkout http://localhost:9090/api/health
./gradlew run

# Check the dependencies for known high or ciritcal vulnerabilities
./gradlew dependencyCheckAnalyze

# Format the code with Spotless
./gradlew spotlessJavaApply

# Analyse the code for issues with SpotBugs
./gradlew spotbugsMain spotbugsTest

# Push the code for analsys to a SonarQube instance
# Note 1: you need to edit "build.gradle" and add your "sonar.host.url"
# Note 2: you need an env var SONAR_TOKEN to authenticate
export SONAR_TOKEN=???
./gradlew sonar

# Checkout all gradle tasks or get help for a task
./gradlew task
./gradlew help --task test
```

# Externalisation

The app uses [Dotenv](https://github.com/cdimascio/dotenv-java)
to read environment variables. 
If an `./app/.env` file is present, it'll be used instead. 
This is recommended only for development

# Docker Build

To build a docker image locally:

```bash
docker build . -t java-api-starter
```

Now we can start the image locally. It's ofent convenient to 
pass all required env variables in a file:

``` bash
# Replace 9090 with the PORT from .env
# Visit http://localhost:9090/api/health
docker run \
  -p 9090:9090 \
  --env-file=./app/.env \
  java-api-starter
```

To debug the image locally, we can open a shell into it:

```bash
# Replace 9090 with the PORT from .env
# Will open a shell terminal
docker run \
  -p 9090:9090 \
  --env-file=./app/.env \
  -it java-api-starter \
  /bin/sh

```