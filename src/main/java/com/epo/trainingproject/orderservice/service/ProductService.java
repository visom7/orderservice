package com.epo.trainingproject.orderservice.service;

import com.epo.trainingproject.orderservice.model.ProductModel;

public interface ProductService {
    int checkAvailability(int productId);
    ProductModel registerProduct(ProductModel ProductModel);
    ProductModel addStock(int id, int quantity);
    ProductModel decreaseStock(int id, int quantity);

}
