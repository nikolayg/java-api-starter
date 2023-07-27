# Init

```bash
Select type of project to generate:
  1: basic
  2: application
  3: library
  4: Gradle plugin
Enter selection (default: basic) [1..4] 2

Select implementation language:
  1: C++
  2: Groovy
  3: Java
  4: Kotlin
  5: Scala
  6: Swift
Enter selection (default: Java) [1..6] 3

Split functionality across multiple subprojects?:
  1: no - only one application project
  2: yes - application and library projects
Enter selection (default: no - only one application project) [1..2] 1

Select build script DSL:
  1: Groovy
  2: Kotlin
Enter selection (default: Groovy) [1..2] 1

Select test framework:
  1: JUnit 4
  2: TestNG
  3: Spock
  4: JUnit Jupiter
Enter selection (default: JUnit Jupiter) [1..4] 4

Project name (default: java-api-starter): 
Source package (default: java.api.starter): com.nikgrozev
```


# Common Command

```bash
# run app locally
./gradlew run

# Checkout tasks
./gradlew task
./gradlew help --task test

# Run Unit tests
./gradlew test

# Clean up and rebuild
./gradlew clean build

# Run the build
java -jar ./app/build/libs/app.jar

./gradlew dependencyCheckAnalyze
```


# Docker

```bash
docker build . -t java-api-starter

# If you need to clear the old Docker caches (debug purposes)
docker build . --no-cache -t java-api-starter
```

If you want to debug the production build:

```bash
# Replace 9090 with the PORT from .env
# Will open a shell terminal
docker run \
  -p 9090:9090 \
  --env-file=./app/.env \
  -it java-api-starter \
  /bin/sh
```

To run the production docker image locally:

```bash
# Replace 9090 with the PORT from .env
# Visit http://localhost:9090/api/health
docker run \
  -p 9090:9090 \
  --env-file=./app/.env \
  java-api-starter
```