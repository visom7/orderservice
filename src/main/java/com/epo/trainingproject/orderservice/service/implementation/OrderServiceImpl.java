package com.epo.trainingproject.orderservice.service.implementation;

import com.epo.trainingproject.orderservice.clients.ShippingClient;
import com.epo.trainingproject.orderservice.converter.OrderConverter;
import com.epo.trainingproject.orderservice.entity.Order;
import com.epo.trainingproject.orderservice.entity.Product;
import com.epo.trainingproject.orderservice.exception.OrderServiceException;
import com.epo.trainingproject.orderservice.model.OrderModel;
import com.epo.trainingproject.orderservice.repository.OrderRepository;
import com.epo.trainingproject.orderservice.service.OrderService;
import com.epo.trainingproject.orderservice.service.ProductService;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderConverter orderConverter;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderModel makeOrder(OrderModel orderModel) throws OrderServiceException {
        try {
            for (Integer productId : orderModel.getProductIds()) {
                if (productService.checkAvailability(productId) < 1) {
                    log.info("Not enough stock available for productId: " + productId);
                    log.info("Calling provider for 5 more units");
                    productService.updateStock(productId, 5);
                }
            }
            log.info("Enough stock available, sending order to shipping!");
            httpPostOrderModelsTo("http://localhost:8091", "/shipping/ship", orderModel);
            Order storedOrder = orderRepository.save(orderConverter.modelToEntity(orderModel));
            for (Product product : storedOrder.getProducts()) {
                productService.updateStock(product.getId(), -1);
            }
            return orderConverter.entityToModel(storedOrder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OrderServiceException(e.getMessage(), e.getCause());
        }
    }

    private void httpPostOrderModelsTo(String baseUrl, String endpoint, OrderModel orderModel) {
        log.info("#### Sending request to " + baseUrl + endpoint);
        ShippingClient shippingClient = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .logger(new Slf4jLogger(ShippingClient.class))
                .logLevel(Logger.Level.FULL)
                .target(ShippingClient.class, baseUrl + endpoint);
        String response = shippingClient.shipProduct(orderModel);
        log.info("#### Received response -> " + response);
    }
}
