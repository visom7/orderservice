package com.epo.trainingproject.orderservice.service;

import com.epo.trainingproject.orderservice.model.ProductModel;

public interface ProductService {
    boolean checkAvailability(ProductModel ProductModel);
    ProductModel addProduct(ProductModel ProductModel);
    ProductModel addStock(int id, int quantity);
}
