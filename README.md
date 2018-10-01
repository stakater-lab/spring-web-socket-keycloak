<h1>Spring Web Socket KeyCloak</h1>

<h3>A sample Application of Websockets, Hazelcast, and Keycloak in Spring.</h4>

Tools:
Spring Boot 2.0.4
Hazelcast 3.9.4
Websockets with SockJs
STOMP
Keycloak

How to run:
<ul>
<li>Clone and build java project<\li>

<li>git clone https://github.com/stakater-lab/spring-web-socket-keycloak.git<\li>

<li>cd spring-web-socket-keycloak<\li>

<li>mvn clean package<\li>

<li>Change the keycloak , Stomp Relay configuration <\li>

<li>Run spring boot Apllication<\li>

<li>Apllication will starts on localhost:9002<\li>

<li>Connect to the websocket using "/ws" Endpoint.<\li>

<li>Send bearer token from keycloak with connect message.<\li>
</ul>
