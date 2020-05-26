package com.epo.trainingproject.orderservice.service;

import com.epo.trainingproject.orderservice.exception.OrderServiceException;
import com.epo.trainingproject.orderservice.model.OrderModel;

import java.util.List;

public interface OrderService {
    List<OrderModel> makeOrder(List<OrderModel> orders) throws OrderServiceException;
}
