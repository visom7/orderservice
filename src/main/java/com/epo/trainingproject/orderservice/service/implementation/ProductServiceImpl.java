package com.epo.trainingproject.orderservice.service.implementation;

import com.epo.trainingproject.orderservice.converter.ProductConverter;
import com.epo.trainingproject.orderservice.converter.StockConverter;
import com.epo.trainingproject.orderservice.entity.Product;
import com.epo.trainingproject.orderservice.entity.Stock;
import com.epo.trainingproject.orderservice.exception.OrderServiceException;
import com.epo.trainingproject.orderservice.model.ProductModel;
import com.epo.trainingproject.orderservice.model.StockModel;
import com.epo.trainingproject.orderservice.repository.ProductRepository;
import com.epo.trainingproject.orderservice.repository.StockRepository;
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
    private StockRepository stockRepository;
    @Autowired
    private ProductConverter productConverter;
    @Autowired
    private StockConverter stockConverter;

    @Override
    public int checkAvailability(int productId) {
        return productRepository.findById(productId)
                .map(Product::getStock)
                .map(Stock::getAmount)
                .orElseThrow(() -> new NoSuchElementException("No product found"));
    }

    @Override
    public ProductModel registerProduct(ProductModel productModel) {
        Product product = productConverter.convertToEntity(productModel);
        product.setStock(Stock.builder().product(product).build());
        Product productInserted = productRepository.save(product);
        LOGGER.info("Product: " + productInserted.getName()
                + " with ID: " + productInserted.getId()
                + ", registered succesfully!");
        return productConverter.convertToModel(productInserted);
    }

    @Override
    public StockModel updateStock(int productId, int quantity) throws OrderServiceException {
        Product product = productRepository.findById(productId).get();
        Stock stock = product.getStock();
        stock.setAmount(stock.getAmount() + quantity);
        Stock updatedStock = stockRepository.save(stock);
        LOGGER.info("Product: " + updatedStock.getProduct().getName() + " correctly updated"
                + " -> Actual stock is " + updatedStock.getAmount() + " units");
        return stockConverter.convertToModel(updatedStock);

//        if (productRepository.findById(stockModel.getProductModel().getId()).isPresent()) {
//            Stock updatedStock = stockRepository.save(stockConverter.convertToEntity(stockModel));
//            LOGGER.info("Product: " + updatedStock.getProduct().getName() + " correctly updated"
//                    + " -> Actual stock is " + updatedStock.getAmount() + " units");
//            return stockConverter.convertToModel(updatedStock);
//        } else {
//            throw new OrderServiceException("Non existing productId: " + stockModel.getProductModel().getId(), new NoSuchElementException());
//        }
    }
}
