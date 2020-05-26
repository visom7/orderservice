package com.epo.trainingproject.orderservice.service;

import com.epo.trainingproject.orderservice.entity.Stock;
import com.epo.trainingproject.orderservice.exception.OrderServiceException;
import com.epo.trainingproject.orderservice.model.ProductModel;
import com.epo.trainingproject.orderservice.model.StockModel;

public interface ProductService {
    int checkAvailability(int productId);
    ProductModel registerProduct(ProductModel ProductModel);
    StockModel updateStock(int productId, int quantity) throws OrderServiceException;
}
