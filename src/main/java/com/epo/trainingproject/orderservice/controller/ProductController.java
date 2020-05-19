package com.epo.trainingproject.orderservice.controller;

import com.epo.trainingproject.orderservice.model.ProductModel;
import com.epo.trainingproject.orderservice.service.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Log LOGGER = LogFactory.getLog(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public void registerProduct(@RequestBody ProductModel productModel) {
        LOGGER.info("Product received -> " + productModel);
        productService.addProduct(productModel);
    }
    @PostMapping("/add-stock")
    public void registerProduct(@RequestParam int id, @RequestParam int quantity) {
        LOGGER.info("Add " + quantity + " units for product id: " + id);
        productService.addStock(id, quantity);
    }

}
