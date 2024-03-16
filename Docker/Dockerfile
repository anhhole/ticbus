FROM openjdk:8-jdk-alpine
LABEL maintainer="Anhlh"
ENV REFRESHED_AT 2019-08-27
EXPOSE 80
RUN set -ex \
  && apk update \
  && apk upgrade \
  && apk add curl jq
RUN mkdir /ticbus
COPY /target/*.jar /ticbus/backend-0.4.5.jar
WORKDIR /ticbus
CMD ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom","backend-0.4.5.jar"]
