package com.epo.trainingproject.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ORDER_ID")
    private int orderId;
    @Column(name = "PRODUCT_ID")
    private int productId;
    @Column(name = "AMOUNT")
    private int amount;
    @Column(name = "CUSTOMER_ID")
    private int customerId;
}
