package com.cardconnect.stom.stockexchange.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "broker")
@Getter
@Setter
public class BrokerProperties {

    private String applicationDestinationPrefix;
    private String destinationPrefix;
    private String endpoint;
    private String relayHost;
    private int relayPort;
    private String clientLogin;
    private String clientPasscode;
    private String virtualHost;
}