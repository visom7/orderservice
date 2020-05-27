package com.epo.trainingproject.orderservice.repository;

import com.epo.trainingproject.orderservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //TODO añadir query manual
}
