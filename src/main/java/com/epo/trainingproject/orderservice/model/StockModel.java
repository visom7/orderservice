package com.epo.trainingproject.orderservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockModel {
    private int id;
    private ProductModel productModel;
    private int amount;
}
