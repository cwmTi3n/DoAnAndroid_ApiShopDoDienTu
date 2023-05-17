package com.kt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartitemId")
    private int id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "unitPrice")
    private float unitPrice;
    @Column(name = "status")
    private int status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    @JsonIgnore
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartId")
    @JsonIgnore
    private Cart cart;
}
