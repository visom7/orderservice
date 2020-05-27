package com.epo.trainingproject.orderservice.converter;

import com.epo.trainingproject.orderservice.entity.ProductType;
import com.epo.trainingproject.orderservice.model.ProductTypeModel;
import org.springframework.stereotype.Component;

@Component
public class ProductTypeConverter {

    public ProductType convertToEntity(ProductTypeModel productTypeModel) {
        return ProductType.builder()
                .type(productTypeModel.getType())
                .build();
    }

    public ProductTypeModel convertToModel(ProductType productType) {
        return ProductTypeModel.builder()
                .type(productType.getType())
                .build();
    }
}
