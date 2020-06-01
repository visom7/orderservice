package com.epo.trainingproject.orderservice.converter;

import com.epo.trainingproject.orderservice.entity.Order;
import com.epo.trainingproject.orderservice.entity.Product;
import com.epo.trainingproject.orderservice.model.OrderRequest;
import com.epo.trainingproject.orderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    @Autowired
    private ProductRepository productRepository;

    public Order modelToEntity(OrderRequest orderRequest) {
        List<Product> products = new ArrayList<>();
        orderRequest.getProductIds()
                .forEach(p -> products.add(productRepository.findById(p).orElseThrow(() -> new NoSuchElementException("Non existing product")))
        );
        return Order.builder()
                .products(products)
                .build();
    }

    public OrderRequest entityToModel(Order order) {
        return OrderRequest.builder()
                .productIds(order.getProducts()
                        .stream()
                        .map(Product::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}
