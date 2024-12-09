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
	    /**
	     * Retrieves all shopping carts from the database.
	     *
	     * @return List of all ShoppingCart objects.
	     */
	    return cartRepository.findAll();
	}
	
	@Override
	public List<ShoppingCart> findAllActiveCarts() {
		/**
	     * Retrieves all active shopping carts from the database.
	     *
	     * @return List of active ShoppingCart objects.
	     */
	    return cartRepository.findAllActiveCarts();
	}
	

	@Override
	public ShoppingCart getCartById(Long cart_id) {
	    /**
	     * Retrieves a specific shopping cart by its unique ID.
	     *
	     * @param cart_id The ID of the shopping cart to be retrieved.
	     * @return The corresponding ShoppingCart object if found.
	     * @throws RuntimeException If the shopping cart is not found.
	     */
	    return cartRepository.findById(cart_id)
	            .orElseThrow(() -> new RuntimeException("Shopping cart not found with id: " + cart_id));
	}

	@Override
	public ShoppingCart createCart(ShoppingCartDTO cartDTO) {
	    /**
	     * Creates a new shopping cart associated with a specific user.
	     * Validates the existence of the user before creating the cart.
	     *
	     * @param cartDTO The data transfer object containing shopping cart details.
	     * @return The created ShoppingCart object saved in the database.
	     * @throws RuntimeException If the user associated with the cart is not found.
	     */
	    User user = userRepository.findById(cartDTO.getUserId())
	            .orElseThrow(() -> new RuntimeException("User not found with id: " + cartDTO.getUserId()));

	    ShoppingCart cart = new ShoppingCart(
	            user,
	            BigDecimal.ZERO,
	            true,
	            LocalDateTime.now(),
	            LocalDateTime.now()
	    );

	    return cartRepository.save(cart);
	}

	@Override
	public ShoppingCart updateCart(Long cart_id, ShoppingCart cartActualizado) {
	    /**
	     * Updates an existing shopping cart with new details.
	     *
	     * @param cart_id          The ID of the cart to be updated.
	     * @param cartActualizado  The ShoppingCart object containing updated details.
	     * @return The updated ShoppingCart object saved in the database.
	     * @throws RuntimeException If the shopping cart is not found.
	     */
	    ShoppingCart cartExistente = getCartById(cart_id);

	    cartExistente.setUser(cartActualizado.getUser());
	    cartExistente.setTotal(cartActualizado.getTotal());
	    cartExistente.setActive(cartActualizado.isActive());
	    cartExistente.setUpdatedAt(LocalDateTime.now());

	    return cartRepository.save(cartExistente);
	}

	@Override
	public ShoppingCart partialUpdateCart(Long cart_id, BigDecimal total, Boolean active) {
	    /**
	     * Partially updates a shopping cart by modifying only the provided fields.
	     *
	     * @param cart_id The ID of the cart to be updated.
	     * @param total   The new total value, if provided.
	     * @param active  The new active status, if provided.
	     * @return The updated ShoppingCart object saved in the database.
	     * @throws RuntimeException If the shopping cart is not found.
	     */
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
	    /**
	     * Deletes a shopping cart by its unique ID.
	     *
	     * @param cart_id The ID of the cart to be deleted.
	     * @throws RuntimeException If the shopping cart is not found.
	     */
	    ShoppingCart cartExistente = getCartById(cart_id);
	    cartRepository.delete(cartExistente);
	}
}
