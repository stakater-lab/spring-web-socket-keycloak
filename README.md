<h1>Spring Web Socket KeyCloak</h1>

<h3>A sample Application of Websockets, Hazelcast, and Keycloak in Spring.</h4>

Tools:
<ul>
<li>Spring Boot 2.0.4
<li>Hazelcast 3.9.4
<li>Websockets with SockJs
<li>STOMP
<li>Keycloak
</ul>
How to run:
<ul>
<li>Clone and build java project

<li>git clone https://github.com/stakater-lab/spring-web-socket-keycloak.git

<li>cd spring-web-socket-keycloak

<li>mvn clean package

<li>Change the keycloak , Stomp Relay configuration 

<li>Run spring boot Apllication

<li>Apllication will starts on localhost:9002

<li>Connect to the websocket using "/ws" Endpoint

<li>Send bearer token from keycloak with connect message
</ul>
