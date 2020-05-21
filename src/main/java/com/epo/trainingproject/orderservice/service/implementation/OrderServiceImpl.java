package com.epo.trainingproject.orderservice.service.implementation;

import com.epo.trainingproject.orderservice.model.ProductOrderModel;
import com.epo.trainingproject.orderservice.service.OrderService;
import com.epo.trainingproject.orderservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Override
    public void makeOrder(List<ProductOrderModel> productOrderModels) {
        productOrderModels.forEach(productOrderModel -> {
            if (productService.checkAvailability(productOrderModel.getProductId()) < productOrderModel.getQuantity()) {
                productService.addStock(productOrderModel.getProductId(), productOrderModel.getQuantity());
            }
            getHttpCall();
            productService.decreaseStock(productOrderModel.getProductId(), productOrderModel.getQuantity());
        });
    }

    private void getHttpCall() {
        WebClient client = WebClient.builder().baseUrl("http://localhost:8090").build();
        WebClient.RequestBodySpec requestBodySpec = client.method(HttpMethod.GET).uri("/order/ship");
        String response = requestBodySpec.exchange().block().bodyToMono(String.class).block();
        log.info(response);
    }
}
