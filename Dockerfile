FROM tomcat:9.0.62-jdk11-corretto
COPY /target/pdclientmanager.war /usr/local/tomcat/webapps/
COPY /credentials.json /secrets/credentials.json
