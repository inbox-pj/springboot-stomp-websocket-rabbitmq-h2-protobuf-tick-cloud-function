package com.cardconnect.stom.stockexchange.scheduler;

import com.cardconnect.stom.stockexchange.cloud.function.StockUpdateSchedulerFunction;
import com.cardconnect.stom.stockexchange.config.SchedulerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
        name = {"scheduler.enabled"},
        havingValue = "true"
)
public class StockUpdateScheduler {

    private final StockUpdateSchedulerFunction function;
    private final SchedulerProperties schedulerProperties;
    private final ThreadPoolTaskScheduler taskScheduler;

    public StockUpdateScheduler(StockUpdateSchedulerFunction function,
                                SchedulerProperties schedulerProperties,
                                ThreadPoolTaskScheduler taskScheduler) {
        this.schedulerProperties = schedulerProperties;
        this.taskScheduler = taskScheduler;
        this.function = function;
    }

    @Scheduled(initialDelayString = "#{@schedulerProperties.tasks.stockPriceUpdate.initialDelay}",
            fixedDelayString = "#{@schedulerProperties.tasks.stockPriceUpdate.fixedDelay}")
    @ConditionalOnProperty(prefix = "scheduler.tasks.stock-price-update", name = "enabled", havingValue = "true")
    public void updateStockPrices() {
        function.apply(null);
    }
}