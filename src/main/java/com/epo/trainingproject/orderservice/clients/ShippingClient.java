package com.epo.trainingproject.orderservice.clients;

import com.epo.trainingproject.orderservice.model.OrderModel;
import feign.Headers;
import feign.RequestLine;

public interface ShippingClient {
    @RequestLine("POST")
    @Headers("Content-type: application/json")
    String shipProduct(OrderModel orderModel);
}
