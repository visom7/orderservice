package com.epo.trainingproject.orderservice.service;

import com.epo.trainingproject.orderservice.exception.OrderServiceException;
import com.epo.trainingproject.orderservice.model.OrderRequest;

public interface OrderService {
    OrderRequest makeOrder(OrderRequest order) throws OrderServiceException;
}
