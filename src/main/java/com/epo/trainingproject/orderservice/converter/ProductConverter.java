package com.epo.trainingproject.orderservice.converter;

import com.epo.trainingproject.orderservice.entity.Product;
import com.epo.trainingproject.orderservice.model.ProductModel;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public Product convertToEntity(ProductModel productModel) {
        return Product.builder()
                .id(productModel.getId())
                .name(productModel.getName())
                .description(productModel.getDescription())
                .price(productModel.getPrice())
                .build();
    }

    public ProductModel convertToModel(Product product) {
        return ProductModel.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
