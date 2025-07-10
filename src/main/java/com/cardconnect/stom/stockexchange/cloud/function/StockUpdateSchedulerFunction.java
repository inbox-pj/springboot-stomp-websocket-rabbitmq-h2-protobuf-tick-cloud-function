package com.cardconnect.stom.stockexchange.cloud.function;

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

    public StockUpdateSchedulerFunction(StockService stockService, SimpMessagingTemplate template) {
        this.stockService = stockService;
        this.template = template;
    }

    @Override
    public String apply(Void input) {
        List<StockRequest> stocks = stockService.getAllStocks();

        stocks.forEach(stock -> {
            stock.setPrice(stock.getPrice() + Math.random() * 10);
            stockService.saveStock(stock);
            template.convertAndSend("/topic/stockPrices", stock);
        });

        return "Stock prices updated successfully.";
    }
}