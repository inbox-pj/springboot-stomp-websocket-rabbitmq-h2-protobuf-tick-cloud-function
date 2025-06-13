package com.cardconnect.stom.stockexchange.scheduler;

import com.cardconnect.stom.stockexchange.model.Stock;
import com.cardconnect.stom.stockexchange.service.StockService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockUpdateScheduler {

    private final StockService stockService;
    private final SimpMessagingTemplate template;

    public StockUpdateScheduler(StockService stockService, SimpMessagingTemplate template) {
        this.stockService = stockService;
        this.template = template;
    }

    @Scheduled(fixedRate = 60000)
    public void updateStockPrices() {
        List<Stock> stocks = stockService.getAllStocks();
        for (Stock stock : stocks) {
            stock.setPrice(stock.getPrice() + Math.random() * 10);
            stockService.saveStock(stock);
            template.convertAndSend("/topic/stockPrices", stock);
        }
    }
}