package com.cardconnect.stom.stockexchange.scheduler;

import com.cardconnect.stom.stockexchange.config.SchedulerProperties;
import com.cardconnect.stom.stockexchange.model.StockRequest;
import com.cardconnect.stom.stockexchange.service.StockService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(
        name = {"scheduler.enabled"},
        havingValue = "true"
)
public class StockUpdateScheduler {

    private final StockService stockService;
    private final SimpMessagingTemplate template;
    private final SchedulerProperties schedulerProperties;
    private final ThreadPoolTaskScheduler taskScheduler;

    public StockUpdateScheduler(StockService stockService,
                                SimpMessagingTemplate template,
                                SchedulerProperties schedulerProperties,
                                ThreadPoolTaskScheduler taskScheduler) {
        this.stockService = stockService;
        this.template = template;
        this.schedulerProperties = schedulerProperties;
        this.taskScheduler = taskScheduler;
    }

    @Scheduled(initialDelayString = "#{@schedulerProperties.tasks.stockPriceUpdate.initialDelay}",
            fixedDelayString = "#{@schedulerProperties.tasks.stockPriceUpdate.fixedDelay}")
    @ConditionalOnProperty(prefix = "scheduler.tasks.stock-price-update", name = "enabled", havingValue = "true")
    public void updateStockPrices() {
        List<StockRequest> stocks = stockService.getAllStocks();

        stocks.forEach(stock -> {
            stock.setPrice(stock.getPrice() + Math.random() * 10);
            stockService.saveStock(stock);
            template.convertAndSend("/topic/stockPrices", stock);
        });
    }
}