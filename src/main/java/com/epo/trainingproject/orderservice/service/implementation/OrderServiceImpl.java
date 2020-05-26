package com.epo.trainingproject.orderservice.service.implementation;

import com.epo.trainingproject.orderservice.converter.OrderConverter;
import com.epo.trainingproject.orderservice.entity.Order;
import com.epo.trainingproject.orderservice.exception.OrderServiceException;
import com.epo.trainingproject.orderservice.model.OrderModel;
import com.epo.trainingproject.orderservice.model.ProductModel;
import com.epo.trainingproject.orderservice.model.StockModel;
import com.epo.trainingproject.orderservice.repository.OrderRepository;
import com.epo.trainingproject.orderservice.service.OrderService;
import com.epo.trainingproject.orderservice.service.ProductService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Setter
@ConfigurationProperties(prefix = "service")
public class OrderServiceImpl implements OrderService {

    private static final int MINUS_ONE = -1;

    private String url;

    @Autowired
    private OrderConverter orderConverter;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderModel> makeOrder(List<OrderModel> orderModels) throws OrderServiceException {
        try {
            for (OrderModel orderModel : orderModels) {
                if (productService.checkAvailability(orderModel.getProductId()) < orderModel.getAmount()) {
                    log.info("Not enough stock available for productId: " + orderModel.getProductId());
                    log.info("Calling provider for " + orderModel.getAmount() + " units");
                    updateStock(orderModel, orderModel.getAmount());
                }
            }
            log.info("Enough stock available, sending order to shipping!");
            httpPostProductOrderModelsTo(url, "shipping/ship", orderModels);
            List<OrderModel> processedOrders = new ArrayList<>();
            for (OrderModel orderModel : orderModels) {
                Order processedOrder = orderRepository.save(orderConverter.modelToEntity(orderModel));
                processedOrders.add(orderConverter.entityToModel(processedOrder));
                updateStock(orderModel, orderModel.getAmount() * MINUS_ONE);
            }
            return processedOrders;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrderServiceException(e.getMessage(), e.getCause());
        }
    }

    private void updateStock(OrderModel orderModel, int amount) throws OrderServiceException {
//        productService.updateStock(StockModel.builder()
//                .productModel(ProductModel.builder()
//                        .id(orderModel.getProductId())
//                        .build())
//                .amount(amount)
//                .build());
    }

    private void httpPostProductOrderModelsTo(String baseUrl, String endpoint, List<OrderModel> orderModels) {
        WebClient client = WebClient.builder().baseUrl(baseUrl).build();
        WebClient.RequestHeadersSpec<?> requestHeadersSpec = client
                .method(HttpMethod.POST)
                .uri(endpoint)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(orderModels));
        log.info("#### Sending request to " + baseUrl + endpoint);
        String response = requestHeadersSpec.exchange().block().bodyToMono(String.class).block();
        log.info("#### Received response: " + response);
    }

}
