package com.epo.trainingproject.orderservice.converter;

import com.epo.trainingproject.orderservice.entity.Product;
import com.epo.trainingproject.orderservice.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    @Autowired
    private ProductTypeConverter productTypeConverter;

    public Product convertToEntity(ProductModel productModel) {
        return Product.builder()
                .id(productModel.getId())
                .name(productModel.getName())
                .description(productModel.getDescription())
                .price(productModel.getPrice())
                .productType(productTypeConverter.convertToEntity(productModel.getProductTypeModel()))
                .build();
    }

    public ProductModel convertToModel(Product product) {
        return ProductModel.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .productTypeModel(productTypeConverter.convertToModel(product.getProductType()))
                .build();
    }
}
