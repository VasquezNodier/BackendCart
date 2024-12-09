package com.shopper.cart.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.shopper.cart.dto.ShoppingCartDTO;
import com.shopper.cart.model.ShoppingCart;

public interface ShoppingCartService {
	
	List<ShoppingCart> getAllCarts();	
	List<ShoppingCart> findAllActiveCarts();
	ShoppingCart getCartById(Long cart_id);
	ShoppingCart createCart(ShoppingCartDTO cartDTO);
	ShoppingCart updateCart(Long cart_id, ShoppingCart cart);
	ShoppingCart partialUpdateCart(Long cart_id, BigDecimal total, Boolean active);
	void deleteCart(Long cart_id);


}
