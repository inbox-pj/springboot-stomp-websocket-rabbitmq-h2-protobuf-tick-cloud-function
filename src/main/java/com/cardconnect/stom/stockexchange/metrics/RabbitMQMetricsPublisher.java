package com.cardconnect.stom.stockexchange.metrics;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQMetricsPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public RabbitMQMetricsPublisher(RabbitTemplate rabbitTemplate,
                                    @Value("${rabbitmq.exchange}") String exchange,
                                    @Value("${rabbitmq.routing-key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void send(String metricName, double value) {
        String message = metricName + ":" + value;
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}