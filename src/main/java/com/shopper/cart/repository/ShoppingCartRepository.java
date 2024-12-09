package com.shopper.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopper.cart.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
	@Query("SELECT c FROM ShoppingCart c WHERE c.active = true")
	List<ShoppingCart> findAllActiveCarts();
}
