package com.epo.trainingproject.orderservice.controller;

import com.epo.trainingproject.orderservice.exception.OrderServiceException;
import com.epo.trainingproject.orderservice.model.ProductModel;
import com.epo.trainingproject.orderservice.model.StockModel;
import com.epo.trainingproject.orderservice.service.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    //TODO change to lombok's one
    private static final Log LOGGER = LogFactory.getLog(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping("/register")
    public void registerProduct(@RequestBody ProductModel productModel) {
        LOGGER.info("Product received -> " + productModel);
        productService.registerProduct(productModel);
    }

    @PutMapping("/add-stock")
    public ResponseEntity<String> addStock(@RequestParam int productId, @RequestParam int quantity) {
        if (quantity > 0) {
            try {
                LOGGER.info("Add " + quantity + " units for product id: " + productId);
                productService.updateStock(productId, quantity);
                return new ResponseEntity<>("OK", HttpStatus.OK);
            } catch (OrderServiceException e) {
                LOGGER.error("The product with ID " + productId + " does not exist");
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Quantity should be a possitive value above zero", HttpStatus.BAD_REQUEST);
        }
    }
}
