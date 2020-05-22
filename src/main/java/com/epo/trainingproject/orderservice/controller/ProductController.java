package com.epo.trainingproject.orderservice.controller;

import com.epo.trainingproject.orderservice.model.ProductModel;
import com.epo.trainingproject.orderservice.service.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Log LOGGER = LogFactory.getLog(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public void registerProduct(@RequestBody ProductModel productModel) {
        LOGGER.info("Product received -> " + productModel);
        productService.registerProduct(productModel);
    }
    @PutMapping("/add-stock")
    public void registerProduct(@RequestParam int productId, @RequestParam int quantity) {
        if (quantity > 0) {
            try {
                LOGGER.info("Add " + quantity + " units for product id: " + productId);
                productService.addStock(productId, quantity);
            } catch (NoSuchElementException e) {
                LOGGER.error("The product with ID " + productId + " does not exist");
            }
        } else {
            LOGGER.error("Quantity should be a possitive value above zero");
        }
    }
}
