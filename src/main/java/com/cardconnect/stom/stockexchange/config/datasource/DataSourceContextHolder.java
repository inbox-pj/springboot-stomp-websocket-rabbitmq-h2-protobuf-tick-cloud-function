package com.cardconnect.stom.stockexchange.config.datasource;


import com.cardconnect.stom.stockexchange.entity.enums.DataSourceType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataSourceContextHolder {
    private DataSourceContextHolder(){}

    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(DataSourceType dataSourceType) {
        log.debug("Setting data source type to: {}", dataSourceType);
        contextHolder.set(dataSourceType);
    }

    public static DataSourceType getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}