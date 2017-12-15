FROM openjdk:8u151-jdk

ADD . /tmp/app
WORKDIR /tmp/app
ENTRYPOINT ./gradlew test