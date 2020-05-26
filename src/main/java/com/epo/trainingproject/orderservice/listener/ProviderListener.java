package com.epo.trainingproject.orderservice.listener;

import com.epo.trainingproject.orderservice.exception.OrderServiceException;
import com.epo.trainingproject.orderservice.model.OrderModel;
import com.epo.trainingproject.orderservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class ProviderListener {


    @Autowired
    private ProductService productService;

    @KafkaListener(topics = "provider-topic")
    private void incomingProviderMessage(OrderModel orderModel) {
        try {
            productService.updateStock(orderModel.getProductId(), orderModel.getAmount());
        } catch (OrderServiceException e) {
            log.error("Unable to update the stock for " + orderModel + "Error: " + e.getMessage(), e.getCause());
        }
    }
}
