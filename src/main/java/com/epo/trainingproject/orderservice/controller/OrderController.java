package com.epo.trainingproject.orderservice.controller;

import com.epo.trainingproject.orderservice.exception.OrderServiceException;
import com.epo.trainingproject.orderservice.model.OrderRequest;
import com.epo.trainingproject.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Order received! -> Products:");
        orderRequest.getProductIds().forEach(p -> log.info("*** ID: " + p));
        try {
            orderService.makeOrder(orderRequest);
        } catch (OrderServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }

}
