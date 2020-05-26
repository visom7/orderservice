package com.epo.trainingproject.orderservice.repository;

import com.epo.trainingproject.orderservice.entity.Product;
import com.epo.trainingproject.orderservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    Stock findByProduct(Product product);
}
