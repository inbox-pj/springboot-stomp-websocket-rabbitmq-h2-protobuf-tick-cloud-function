package com.cardconnect.stom.stockexchange.cloud.function;

import com.cardconnect.stom.stockexchange.metrics.MetricsService;
import com.cardconnect.stom.stockexchange.model.StockRequest;
import com.cardconnect.stom.stockexchange.service.StockService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service("updateStockPricesFunction")
public class StockUpdateSchedulerFunction implements Function<Void, String> {

    private final StockService stockService;
    private final SimpMessagingTemplate template;
    private final MetricsService metricsService;

    public StockUpdateSchedulerFunction(StockService stockService, SimpMessagingTemplate template, MetricsService metricsService) {
        this.stockService = stockService;
        this.template = template;
        this.metricsService = metricsService;
    }

    @Override
    public String apply(Void input) {
        List<StockRequest> stocks = stockService.getAllStocks();

        stocks.forEach(stock -> {
            metricsService.send("stock.updates", 1);
            stock.setPrice(stock.getPrice() + Math.random() * 10);
            stockService.saveStock(stock);
            template.convertAndSend("/topic/stockPrices", stock);
        });

        return "Stock prices updated successfully.";
    }
}