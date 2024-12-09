package com.shopper.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopper.cart.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
