package com.epo.trainingproject.orderservice.converter;

import com.epo.trainingproject.orderservice.entity.Order;
import com.epo.trainingproject.orderservice.model.OrderModel;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    public Order modelToEntity(OrderModel orderModel) {
        return Order.builder()
                .productId(orderModel.getProductId())
                .amount(orderModel.getAmount())
                .orderId(orderModel.getOrderId())
                .build();
    }

    public OrderModel entityToModel(Order order) {
        return OrderModel.builder()
                .orderId(order.getOrderId())
                .amount(order.getAmount())
                .productId(order.getProductId())
                .build();
    }
}
