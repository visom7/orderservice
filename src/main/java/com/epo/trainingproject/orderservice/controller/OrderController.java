package com.epo.trainingproject.orderservice.controller;

import com.epo.trainingproject.orderservice.exception.CommunicationException;
import com.epo.trainingproject.orderservice.model.ProductOrderModel;
import com.epo.trainingproject.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public void createOrder(@RequestBody List<ProductOrderModel> productOrderModels) {
        log.info("Order received! -> Products:");
        productOrderModels.forEach(p -> log.info("*** ID: " + p.getProductId()));
        try {
            orderService.makeOrder(productOrderModels);
        } catch (NoSuchElementException e) {
            log.error("One of the products in the order does not exist, please try again");
        } catch (CommunicationException e) {
            log.error("Error connecting with shipping service, please try again");
        }
    }

}
