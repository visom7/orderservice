package com.epo.trainingproject.orderservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductOrderModel {
    private int productId;
    private int quantity;
}
