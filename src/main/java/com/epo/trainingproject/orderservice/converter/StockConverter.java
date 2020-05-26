package com.epo.trainingproject.orderservice.converter;

import com.epo.trainingproject.orderservice.entity.Stock;
import com.epo.trainingproject.orderservice.model.StockModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockConverter {

    @Autowired
    private ProductConverter productConverter;

    public StockModel convertToModel(Stock stock) {
        return StockModel.builder()
                .productModel(productConverter.convertToModel(stock.getProduct()))
                .amount(stock.getAmount())
                .build();
    }

    public Stock convertToEntity(StockModel stockModel) {
        return Stock.builder()
                .product(productConverter.convertToEntity(stockModel.getProductModel()))
                .amount(stockModel.getAmount())
                .build();
    }
}
