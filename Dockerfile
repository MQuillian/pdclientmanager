FROM tomcat:9.0.48-jdk8-openjdk-slim-buster
COPY /target/pdclientmanager.war /usr/local/tomcat/webapps/