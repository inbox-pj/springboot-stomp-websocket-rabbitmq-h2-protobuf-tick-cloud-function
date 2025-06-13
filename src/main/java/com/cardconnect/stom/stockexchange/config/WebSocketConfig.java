package com.cardconnect.stom.stockexchange.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final BrokerProperties brokerProperties;

    public WebSocketConfig(BrokerProperties brokerProperties) {
        this.brokerProperties = brokerProperties;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                .setApplicationDestinationPrefixes(brokerProperties.getApplicationDestinationPrefix())
                .enableStompBrokerRelay(brokerProperties.getDestinationPrefix())
                .setRelayHost(brokerProperties.getRelayHost())
                .setRelayPort(brokerProperties.getRelayPort())
                .setVirtualHost(brokerProperties.getVirtualHost())
                .setSystemLogin(brokerProperties.getClientLogin())
                .setSystemPasscode(brokerProperties.getClientPasscode())
                .setClientLogin(brokerProperties.getClientLogin())
                .setClientPasscode(brokerProperties.getClientPasscode());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the "/ws" endpoint for WebSocket connections
        registry.addEndpoint(brokerProperties.getEndpoint());
    }

}
