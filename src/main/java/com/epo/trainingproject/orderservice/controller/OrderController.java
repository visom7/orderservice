package com.epo.trainingproject.orderservice.controller;

import com.epo.trainingproject.orderservice.model.ProductModel;
import com.epo.trainingproject.orderservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public void createOrder(ProductModel productModel) {

    }

}
