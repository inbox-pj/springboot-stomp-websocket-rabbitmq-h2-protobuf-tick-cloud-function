package com.cardconnect.stom.stockexchange.service;

import com.cardconnect.stom.stockexchange.entity.Stock;
import com.cardconnect.stom.stockexchange.mapper.StockMapper;
import com.cardconnect.stom.stockexchange.model.StockRequest;
import com.cardconnect.stom.stockexchange.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public StockService(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    public Stock saveStock(StockRequest stock) {
        Stock entity = stockMapper.toEntity(stock);
        return stockRepository.save(entity);
    }

    public List<StockRequest> getAllStocks() {
        return stockRepository.findAll()
                .stream()
                .map(stockMapper::toModel) // Use mapper to convert Stock to StockRequest
                .collect(Collectors.toList());

    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
}