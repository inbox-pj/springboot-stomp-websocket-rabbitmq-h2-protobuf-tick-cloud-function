package com.cardconnect.stom.stockexchange.controller;

import com.cardconnect.stom.stockexchange.model.Stock;
import com.cardconnect.stom.stockexchange.service.StockService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockMarketController {

    private final SimpMessagingTemplate template;
    private final StockService stockService;

    public StockMarketController(SimpMessagingTemplate template, StockService stockService) {
        this.template = template;
        this.stockService = stockService;
    }

    @MessageMapping("/updateStock")
    //@SendTo("/topic/stockPrices")
    @PostMapping("/stocks")
    public Stock updateStockPrice(@RequestBody Stock stock) {
        stock.setPrice(stock.getPrice() + Math.random() * 10);
        stockService.saveStock(stock);
        template.convertAndSend("/topic/stockPrices", stock);
        return stock;
    }

    @GetMapping("/stocks")
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @DeleteMapping("/stocks/{id}")
    public void deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
    }
}