package com.shopper.cart.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopper.cart.dto.ShoppingCartDetailDTO;
import com.shopper.cart.model.Product;
import com.shopper.cart.model.ShoppingCart;
import com.shopper.cart.model.ShoppingCartDetail;
import com.shopper.cart.model.ShoppingCartDetailKey;
import com.shopper.cart.repository.ProductRepository;
import com.shopper.cart.repository.ShoppingCartDetailRepository;
import com.shopper.cart.repository.ShoppingCartRepository;
import com.shopper.cart.service.ShoppingCartDetailService;

@Service
public class ShoppingCartDetailServiceImpl implements ShoppingCartDetailService {
	
	private final ShoppingCartDetailRepository cartDetailRepository;
	private final ProductRepository productRepository;
	private final ShoppingCartRepository cartRepository;
	
	@Autowired
	public ShoppingCartDetailServiceImpl(ShoppingCartDetailRepository cartDetailRepository,
			ProductRepository productRepository, ShoppingCartRepository cartRepository) {
		this.cartDetailRepository = cartDetailRepository;
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
	}


	@Override
	public List<ShoppingCartDetail> getAllCartDetails() {
	    return cartDetailRepository.findAll();
	}

	@Override
	public ShoppingCartDetailDTO getCartDetailById(ShoppingCartDetailKey key) {
	    /**
	     * Retrieves a specific shopping cart detail by its compound key.
	     *
	     * @param key The composite key representing the shopping cart detail.
	     * @return The corresponding ShoppingCartDetailDTO if found.
	     * @throws RuntimeException If the shopping cart detail is not found.
	     */
	    ShoppingCartDetail detail = findCartByCompoundId(key);
	    return convertToDTO(detail);
	}

	@Override
	public ShoppingCartDetail createCartDetail(ShoppingCartDetailDTO shoppingCartDetailDTO) {
	    /**
	     * Creates or updates a shopping cart detail.
	     * If the detail already exists, its quantity is updated; otherwise, a new detail is created.
	     * The total amount of the shopping cart is updated accordingly.
	     *
	     * @param shoppingCartDetailDTO The data transfer object containing cart detail information.
	     * @return The created or updated ShoppingCartDetail object.
	     * @throws RuntimeException If the product or cart is not found or the quantity is invalid.
	     */
	    Product product = findProductById(shoppingCartDetailDTO.getProductId());
	    ShoppingCart cart = findCartById(shoppingCartDetailDTO.getCartId());

	    ShoppingCartDetailKey key = new ShoppingCartDetailKey(cart.getId(), product.getId());
	    Optional<ShoppingCartDetail> existingCartDetail = cartDetailRepository.findById(key);

	    ShoppingCartDetail cartDetailToSave = existingCartDetail.isPresent()
	            ? updateExistingCartDetail(existingCartDetail.get(), shoppingCartDetailDTO, product)
	            : createNewCartDetail(cart, product, shoppingCartDetailDTO);

	    updateCartTotal(cart);
	    return cartDetailToSave;
	}

	@Override
	public ShoppingCartDetail patchCartDetail(ShoppingCartDetailKey id, Integer quantity) {
	    /**
	     * Updates the quantity of an existing shopping cart detail.
	     * If the quantity is zero, the detail is deleted.
	     * If the quantity is less than zero, an exception is thrown.
	     *
	     * @param id       The composite key of the shopping cart detail.
	     * @param quantity The new quantity to be set.
	     * @return The updated ShoppingCartDetail object, or null if deleted.
	     * @throws RuntimeException If the quantity is less than zero.
	     */
	    ShoppingCartDetail existingDetail = findCartByCompoundId(id);

	    if (quantity <= 0) {
	        deleteCartDetail(id);
	        return null;
	    }

	    existingDetail.setQuant(quantity);
	    existingDetail.setSubtotal(existingDetail.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));

	    ShoppingCart cart = existingDetail.getShoppingCart();
	    updateCartTotal(cart);

	    return cartDetailRepository.save(existingDetail);
	}

	@Override
	public void deleteCartDetail(ShoppingCartDetailKey id) {
	    /**
	     * Deletes a shopping cart detail based on its composite key.
	     * Updates the shopping cart total after deletion.
	     *
	     * @param id The composite key of the shopping cart detail to be deleted.
	     * @throws RuntimeException If the shopping cart detail is not found.
	     */
	    ShoppingCartDetail existingDetail = findCartByCompoundId(id);
	    cartDetailRepository.delete(existingDetail);

	    ShoppingCart cart = existingDetail.getShoppingCart();
	    updateCartTotal(cart);
	}

	/**
	 * Converts a ShoppingCartDetail object to a ShoppingCartDetailDTO.
	 *
	 * @param detail The ShoppingCartDetail object to convert.
	 * @return The corresponding ShoppingCartDetailDTO object.
	 */
	private ShoppingCartDetailDTO convertToDTO(ShoppingCartDetail detail) {
	    return new ShoppingCartDetailDTO(
	            detail.getShoppingCart().getId(),
	            detail.getProduct().getId(),
	            detail.getProduct().getName(),
	            detail.getQuant(),
	            detail.getUnitPrice(),
	            detail.getSubtotal()
	    );
	}

	/**
	 * Finds a product by its unique ID.
	 *
	 * @param productId The ID of the product to find.
	 * @return The corresponding Product object if found.
	 * @throws RuntimeException If the product is not found.
	 */
	private Product findProductById(Long productId) {
	    return productRepository.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
	}

	/**
	 * Finds a shopping cart by its unique ID.
	 *
	 * @param cartId The ID of the shopping cart to find.
	 * @return The corresponding ShoppingCart object if found.
	 * @throws RuntimeException If the cart is not found.
	 */
	private ShoppingCart findCartById(Long cartId) {
	    return cartRepository.findById(cartId)
	            .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
	}

	/**
	 * Finds a shopping cart detail by its composite key.
	 *
	 * @param key The composite key of the shopping cart detail.
	 * @return The corresponding ShoppingCartDetail object if found.
	 * @throws RuntimeException If the cart detail is not found.
	 */
	private ShoppingCartDetail findCartByCompoundId(ShoppingCartDetailKey key) {
	    return cartDetailRepository.findById(key)
	            .orElseThrow(() -> new RuntimeException("Cart detail not found with key: " + key));
	}

	/**
	 * Updates the quantity and subtotal of an existing shopping cart detail.
	 *
	 * @param existingCartDetail    The existing ShoppingCartDetail object.
	 * @param shoppingCartDetailDTO The DTO containing updated cart detail information.
	 * @param product               The associated Product object.
	 * @return The updated ShoppingCartDetail object.
	 */
	private ShoppingCartDetail updateExistingCartDetail(
	        ShoppingCartDetail existingCartDetail,
	        ShoppingCartDetailDTO shoppingCartDetailDTO,
	        Product product) {

	    int updatedQuantity = existingCartDetail.getQuant() + shoppingCartDetailDTO.getQuantity();
	    BigDecimal updatedSubtotal = product.getPrice().multiply(BigDecimal.valueOf(updatedQuantity));

	    existingCartDetail.setQuant(updatedQuantity);
	    existingCartDetail.setSubtotal(updatedSubtotal);

	    return cartDetailRepository.save(existingCartDetail);
	}

	/**
	 * Creates a new shopping cart detail with the provided data.
	 *
	 * @param cart                  The associated ShoppingCart object.
	 * @param product               The associated Product object.
	 * @param shoppingCartDetailDTO The DTO containing cart detail information.
	 * @return The created ShoppingCartDetail object.
	 * @throws RuntimeException If the quantity is less than or equal to zero.
	 */
	private ShoppingCartDetail createNewCartDetail(
	        ShoppingCart cart,
	        Product product,
	        ShoppingCartDetailDTO shoppingCartDetailDTO) {

	    if (shoppingCartDetailDTO.getQuantity() <= 0) {
	        throw new RuntimeException("Quantity should be greater than 0");
	    }

	    BigDecimal unitPrice = product.getPrice();
	    BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(shoppingCartDetailDTO.getQuantity()));

	    ShoppingCartDetail newCartDetail = new ShoppingCartDetail(
	            cart, product, shoppingCartDetailDTO.getQuantity(), unitPrice, subtotal, LocalDateTime.now());

	    return cartDetailRepository.save(newCartDetail);
	}

	/**
	 * Updates the total amount of a shopping cart based on its details.
	 *
	 * @param cart The shopping cart whose total will be updated.
	 */
	private void updateCartTotal(ShoppingCart cart) {
	    BigDecimal total = cart.getShoppingCartDetails()
	            .stream()
	            .filter(Objects::nonNull)
	            .map(detail -> detail.getSubtotal() != null ? detail.getSubtotal() : BigDecimal.ZERO)
	            .reduce(BigDecimal.ZERO, BigDecimal::add);

	    cart.setTotal(total);
	    cart.setUpdatedAt(LocalDateTime.now());
	    cartRepository.save(cart);
	}

}
