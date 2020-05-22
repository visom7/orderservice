package com.epo.trainingproject.orderservice.service.implementation;

import com.epo.trainingproject.orderservice.model.ProductOrderModel;
import com.epo.trainingproject.orderservice.service.OrderService;
import com.epo.trainingproject.orderservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
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
                log.info("Not enough stock available for productId: " + productOrderModel.getProductId());
                log.info("Calling provider for " + productOrderModel.getQuantity() + " units");
                productService.addStock(productOrderModel.getProductId(), productOrderModel.getQuantity());
            }
        });
        log.info("Enough stock available, sending order to shipping!");
        httpPostProductOrderModelsTo("http://localhost:8091", "shipping/ship", productOrderModels);
        productOrderModels.forEach(productOrderModel -> {
            productService.decreaseStock(productOrderModel.getProductId(), productOrderModel.getQuantity());
        });
    }

    private void httpPostProductOrderModelsTo(String baseUrl, String endpoint, List<ProductOrderModel> productOrderModels) {
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        WebClient.RequestHeadersSpec<?> requestHeadersSpec = client
                .method(HttpMethod.POST)
                .uri(endpoint)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(productOrderModels));
        log.info("#### Sending request to " + baseUrl + endpoint);
        String response = requestHeadersSpec.exchange().block().bodyToMono(String.class).block();
        log.info("#### Received response: " + response);
    }
}
