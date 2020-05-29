package com.epo.trainingproject.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductTypeModel productTypeModel;
}
