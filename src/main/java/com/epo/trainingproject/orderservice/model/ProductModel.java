package com.epo.trainingproject.orderservice.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductModel {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductTypeModel productTypeModel;
}
