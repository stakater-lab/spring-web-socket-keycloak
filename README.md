Spring Web Socket KeyCloak
A sample Application of Websockets, Hazelcast, and Keycloak in Spring.

Tools:
Spring Boot 2.0.4
Hazelcast 3.9.4
Websockets with SockJs
STOMP
Keycloak

How to run:

Clone and build java project

git clone https://github.com/stakater-lab/spring-web-socket-keycloak.git

cd spring-web-socket-keycloak

mvn clean package

Change the keycloak , Stomp Relay configuration 

Run spring boot Apllication

Apllication will starts on localhost:9002

Connect to the websocket using "/ws" Endpoint

Send bearer token from keycloak with connect message.

