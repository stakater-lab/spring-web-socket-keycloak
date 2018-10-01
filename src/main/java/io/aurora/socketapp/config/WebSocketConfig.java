package io.aurora.socketapp.config;

import io.aurora.socketapp.tokenauthenication.JWSAuthenticationToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.Optional;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig
        extends AbstractSessionWebSocketMessageBrokerConfigurer<Session>
{

    @Qualifier("websocket")
    private AuthenticationManager authenticationManager;

    @Value("${relay.host}")
    private String relayHost;

    @Value("${relay.port}")
    private Integer relayPort;

    public WebSocketConfig(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry)
    {
        registry
                .enableSimpleBroker("/queue/", "/topic/");
                /*
                .enableStompBrokerRelay("/queue/", "/topic/")
                .setUserDestinationBroadcast("/topic/unresolved.user.dest")
                .setUserRegistryBroadcast("/topic/registry.broadcast")
                .setRelayHost(relayHost)
                .setRelayPort(relayPort);*/
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration)
    {
        registration.interceptors(new ChannelInterceptor()
        {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel)
            {
                StompHeaderAccessor accessor = SimpMessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand()))
                {
                    Optional.ofNullable(accessor.getNativeHeader("Authorization")).ifPresent(ah ->
                    {
                        String bearerToken = ah.get(0).replace("Bearer ", "");
                        JWSAuthenticationToken token = (JWSAuthenticationToken) authenticationManager
                                .authenticate(new JWSAuthenticationToken(bearerToken));
                        accessor.setUser(token);
                    });
                }
                return message;
            }
        });
    }
}