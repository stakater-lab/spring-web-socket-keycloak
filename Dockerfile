FROM stakater/maven-centos:3.5.4-jdk1.8-centos7-v0.0.5

LABEL name="Spring Websocket Keycloack app" \
      maintainer="Stakater <stakater@aurorasolutions.io>" \
      vendor="Stakater" \
      release="1" \
      summary="An application based on Java Websocket and Keycloack"

ARG USER=1001
# Add code for application
ADD [--chown=$USER:root] /application /$HOME/application/
WORKDIR $HOME/application

# Exposing 8080 port as we want application to run on port 8080
EXPOSE 8080

# Change to root user so that we can change ownership of the $HOME dir to root group
USER root

# Creating Maven Package
RUN mvn clean -Duser.home=$HOME/application package -DskipTests=true

# Change the group to root group
RUN chgrp -R 0 /$HOME && \
    chmod -R g=u /$HOME

# Change back to USER
USER $USER

# Running Application
CMD mvn -Duser.home=$HOME/application spring-boot:run