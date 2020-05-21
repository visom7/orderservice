package com.epo.trainingproject.orderservice.controller;

import com.epo.trainingproject.orderservice.model.ProductOrderModel;
import com.epo.trainingproject.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/ship")
    public ResponseEntity shippingMock() {
        log.info("Send product");
        return ResponseEntity.ok().build();
    }
}
