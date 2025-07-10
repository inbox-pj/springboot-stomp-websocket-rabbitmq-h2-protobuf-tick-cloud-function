package com.cardconnect.stom.stockexchange.aspect;

import com.cardconnect.stom.stockexchange.config.datasource.DataSourceContextHolder;
import com.cardconnect.stom.stockexchange.entity.enums.DataSourceType;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {

//    @Before("@annotation(com.cardconnect.stom.stockexchange.annotations.ReadOnly)")
//    public void setReadDataSourceType() {
//        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
//    }
//
//    @Before("@annotation(com.cardconnect.stom.stockexchange.annotations.WriteOnly)")
//    public void setWriteDataSourceType() {
//        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
//    }
//
//    @After("@annotation(com.cardconnect.stom.stockexchange.annotations.ReadOnly) || @annotation(com.cardconnect.stom.stockexchange.annotations.WriteOnly)")
//    public void clearDataSourceType() {
//        DataSourceContextHolder.clearDataSourceType();
//    }


    @Before("execution(* com.cardconnect.stom.stockexchange.repository..*.find*(..)) || " +
            "execution(* com.cardconnect.stom.stockexchange.repository..*.get*(..)) || " +
            "execution(* com.cardconnect.stom.stockexchange.repository..*.read*(..))")
    public void setReadDataSourceType() {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
    }

    @Before("execution(* com.cardconnect.stom.stockexchange.repository..*.save*(..)) || " +
            "execution(* com.cardconnect.stom.stockexchange.repository..*.update*(..)) || " +
            "execution(* com.cardconnect.stom.stockexchange.repository..*.delete*(..))")
    public void setWriteDataSourceType() {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
    }
}