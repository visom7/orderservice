package com.epo.trainingproject.orderservice.service.implementation;

import com.epo.trainingproject.orderservice.model.ProductOrderModel;
import com.epo.trainingproject.orderservice.service.OrderService;
import com.epo.trainingproject.orderservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Override
    public void makeOrder(List<ProductOrderModel> productOrderModels) {
        productOrderModels.forEach(productOrderModel -> {
            if (productService.checkAvailability(productOrderModel.getProductId()) < productOrderModel.getQuantity()) {
                productService.addStock(productOrderModel.getProductId(), productOrderModel.getQuantity());
            }
            productService.decreaseStock(productOrderModel.getProductId(), productOrderModel.getQuantity());
        });
    }
}
