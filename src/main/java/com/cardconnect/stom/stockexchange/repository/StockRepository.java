package com.cardconnect.stom.stockexchange.repository;

import com.cardconnect.stom.stockexchange.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}