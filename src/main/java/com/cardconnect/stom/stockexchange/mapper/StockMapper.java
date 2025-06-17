package com.cardconnect.stom.stockexchange.mapper;

import com.cardconnect.stom.stockexchange.entity.Stock;
import com.cardconnect.stom.stockexchange.model.StockRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockRequest toModel(Stock stock);

    Stock toEntity(StockRequest stockRequest);
}