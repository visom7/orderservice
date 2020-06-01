package com.epo.trainingproject.orderservice.clients;

import com.epo.trainingproject.orderservice.model.OrderRequest;
import feign.Headers;
import feign.RequestLine;

public interface ShippingClient {
    @RequestLine("POST")
    @Headers("Content-type: application/json")
    String shipProduct(OrderRequest orderRequest);
}
