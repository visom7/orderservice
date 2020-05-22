package com.epo.trainingproject.orderservice.controller;

import com.epo.trainingproject.orderservice.model.ProductOrderModel;
import com.epo.trainingproject.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public void createOrder(@RequestBody List<ProductOrderModel> productOrderModels) {
        orderService.makeOrder(productOrderModels);
    }

}
