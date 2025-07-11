package com.cardconnect.stom.stockexchange.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
    private final MeterRegistry meterRegistry;
    private final RabbitMQMetricsPublisher rabbitMQMetricsPublisher;

    @Autowired
    public MetricsService(MeterRegistry meterRegistry, RabbitMQMetricsPublisher rabbitMQMetricsPublisher) {
        this.meterRegistry = meterRegistry;
        this.rabbitMQMetricsPublisher = rabbitMQMetricsPublisher;
    }

    public void send(String metricName, double value) {
        // Increment the counter in MeterRegistry
        meterRegistry.counter(metricName).increment(value);

        // Send the metric to RabbitMQ
        rabbitMQMetricsPublisher.send(metricName, value);
    }
}