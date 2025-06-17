package com.cardconnect.stom.stockexchange.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "scheduler")
@Getter
@Setter
public class SchedulerProperties {

    private boolean enabled;
    private Pool pool;
    private Tasks tasks;

    @Getter
    @Setter
    public static class Pool {
        private int size;
        private String threadNamePrefix;
    }

    @Getter
    @Setter
    public static class Tasks {
        private StockPriceUpdate stockPriceUpdate;

        @Getter
        @Setter
        public static class StockPriceUpdate {
            private long initialDelay;
            private long fixedDelay;
            private boolean enabled;
        }
    }
}