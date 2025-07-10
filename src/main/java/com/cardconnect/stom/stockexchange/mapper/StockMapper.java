package com.cardconnect.stom.stockexchange.mapper;

import com.cardconnect.stom.stockexchange.entity.Stock;
import com.cardconnect.stom.stockexchange.model.StockRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = SPRING)
public interface StockMapper {
    StockRequest toModel(Stock stock);

    Stock toEntity(StockRequest stockRequest);
}