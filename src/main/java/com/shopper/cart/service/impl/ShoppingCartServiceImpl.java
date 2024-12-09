package com.shopper.cart.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopper.cart.dto.ShoppingCartDTO;
import com.shopper.cart.model.ShoppingCart;
import com.shopper.cart.model.User;
import com.shopper.cart.repository.ShoppingCartRepository;
import com.shopper.cart.repository.UserRepository;
import com.shopper.cart.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private final ShoppingCartRepository cartRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public ShoppingCartServiceImpl(ShoppingCartRepository cartRepository, UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<ShoppingCart> getAllCarts() {
		return cartRepository.findAll();
	}

	@Override
	public ShoppingCart getCartById(Long cart_id) {
		return cartRepository.findById(cart_id)
				.orElseThrow(() -> new RuntimeException
						("Shopping cart not found with id: "+ cart_id));
	}

	@Override
	public ShoppingCart createCart(ShoppingCartDTO cartDTO) {
		
		User user = userRepository.findById(cartDTO.getUserId())
				.orElseThrow(() -> new RuntimeException
						("User not found with id: "+cartDTO.getUserId()));
		
		ShoppingCart cart = new ShoppingCart(
				user,
				cartDTO.getTotal(),
				cartDTO.isActive(),
				LocalDateTime.now(),
				LocalDateTime.now()
		);
		
		return cartRepository.save(cart);
	}
	
	@Override
	public ShoppingCart updateCart(Long cart_id, ShoppingCart cartActualizado) {
		ShoppingCart cartExistente = getCartById(cart_id);

		cartExistente.setUser(cartActualizado.getUser());
		cartExistente.setTotal(cartActualizado.getTotal());
		cartExistente.setActive(cartActualizado.isActive());
		cartExistente.setUpdatedAt(LocalDateTime.now());

		return cartRepository.save(cartExistente);
	}
	
	@Override
	public ShoppingCart partialUpdateCart(Long cart_id, BigDecimal total, Boolean active) {
		ShoppingCart cartExistente = getCartById(cart_id);

		if (total != null) {
			cartExistente.setTotal(total);
		}
		if (active != null) {
			cartExistente.setActive(active);
		}
		cartExistente.setUpdatedAt(LocalDateTime.now());
		
		return cartRepository.save(cartExistente);
	}

	@Override
	public void deleteCart(Long cart_id) {
		ShoppingCart cartExistente = getCartById(cart_id);
		cartRepository.delete(cartExistente);
	}

}
