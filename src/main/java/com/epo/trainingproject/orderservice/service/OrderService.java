package com.epo.trainingproject.orderservice.service;

import com.epo.trainingproject.orderservice.exception.CommunicationException;
import com.epo.trainingproject.orderservice.model.ProductOrderModel;

import java.util.List;

public interface OrderService {
    void makeOrder(List<ProductOrderModel> orders) throws CommunicationException;
}
