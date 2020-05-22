package com.epo.trainingproject.orderservice.repository;

import com.epo.trainingproject.orderservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //TODO Añadir otra tabla para la relación producto-stock
    //TODO añadir algun tipo de query de JPA tipo findBy...
    //TODO añadir query manual
}
