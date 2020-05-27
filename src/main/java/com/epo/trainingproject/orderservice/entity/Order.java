package com.epo.trainingproject.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private int id;

    @JoinTable(name = "REL_ORDER_PRODUCTS",
    joinColumns = @JoinColumn(name = "FK_ORDER_ID"),
    inverseJoinColumns = @JoinColumn(name = "FK_PRODUCT_ID"))
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> products;
}
