package com.epo.trainingproject.orderservice.service.implementation;

import com.epo.trainingproject.orderservice.converter.ProductConverter;
import com.epo.trainingproject.orderservice.entity.Product;
import com.epo.trainingproject.orderservice.model.ProductModel;
import com.epo.trainingproject.orderservice.repository.ProductRepository;
import com.epo.trainingproject.orderservice.service.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Log LOGGER = LogFactory.getLog(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductConverter converter;

    @Override
    public int checkAvailability(int productId) {
        return productRepository.findById(productId)
                .map(Product::getQuantity)
                .orElseThrow(() -> new NoSuchElementException("No product found"));
    }

    @Override
    public ProductModel registerProduct(ProductModel productModel) {
        Product productInserted = productRepository.save(converter.convertToEntity(productModel));
        LOGGER.info("Product: " + productInserted.getName()
                + " with ID: " + productInserted.getId()
                + ", registered succesfully!");
        return converter.convertToModel(productInserted);
    }

    @Override
    public ProductModel addStock(int id, int quantity) throws NoSuchElementException {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional
                .orElseThrow(() -> new NoSuchElementException("Non existing product"));

        Product updatedProduct = productRepository.save(
                Product.builder()
                        .id(id)
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .quantity(product.getQuantity() + quantity)
                        .build());

        LOGGER.info("Product: " + updatedProduct.getName() + " correctly updated"
                + " -> Actual stock is " + updatedProduct.getQuantity() + " units");
        return converter.convertToModel(updatedProduct);
    }

    @Override
    public ProductModel decreaseStock(int id, int quantity) {
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional
                .orElseThrow(() -> new NoSuchElementException("Non existing product"));

        Product updatedProduct = productRepository.save(
                Product.builder()
                        .id(id)
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .quantity(product.getQuantity() - quantity)
                        .build());

        LOGGER.info("Product: " + updatedProduct.getName() + " correctly updated"
                + " -> Actual stock is " + updatedProduct.getQuantity() + " units");
        return converter.convertToModel(updatedProduct);
    }
}
