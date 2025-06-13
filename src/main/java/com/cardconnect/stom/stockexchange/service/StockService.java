package com.cardconnect.stom.stockexchange.service;

import com.cardconnect.stom.stockexchange.model.Stock;
import com.cardconnect.stom.stockexchange.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
}