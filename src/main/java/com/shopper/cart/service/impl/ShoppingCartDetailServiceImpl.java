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
		
		ShoppingCartDetail detail = findCartByCompoundId(key);
		
		return convertToDTO(detail);
	}

	@Override
	public ShoppingCartDetail createCartDetail(ShoppingCartDetailDTO shoppingCartDetailDTO) {
		
		Product product =  findProductById(shoppingCartDetailDTO.getProductId());
		
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
		ShoppingCartDetail existingDetail = findCartByCompoundId(id);
		
		existingDetail.setQuant(quantity);
	    existingDetail.setSubtotal(existingDetail.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
	    
	    ShoppingCart cart = existingDetail.getShoppingCart();
	    
	    if (quantity == 0) {
	        deleteCartDetail(id);
	        return null;
	    }
	    
	    if (quantity < 0) {
	    	throw new RuntimeException("Quantity should be greather than 0");
	    }

        updateCartTotal(cart);
        return cartDetailRepository.save(existingDetail);
	}

	@Override
	public void deleteCartDetail(ShoppingCartDetailKey id) {
	    ShoppingCartDetail existingDetail = findCartByCompoundId(id);
	    
	    cartDetailRepository.delete(existingDetail);
	    
	    ShoppingCart cart = existingDetail.getShoppingCart();
	    updateCartTotal(cart);
	}
	
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
	
	
	private Product findProductById(Long productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException
                		("Product not found with id: " + productId));
	}
	
	private ShoppingCart findCartById(Long cartId) {
		return cartRepository.findById(cartId)
				.orElseThrow(() -> new RuntimeException
                		("Cart not found with id: " + cartId));
	}
	
	private ShoppingCartDetail findCartByCompoundId(ShoppingCartDetailKey key) {
		return cartDetailRepository.findById(key)
	            .orElseThrow(() -> new RuntimeException("Cart detail not found with key: " + key));
	}


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
	
	private ShoppingCartDetail createNewCartDetail(
	        ShoppingCart cart, 
	        Product product, 
	        ShoppingCartDetailDTO shoppingCartDetailDTO) {
		
		if (shoppingCartDetailDTO.getQuantity() <= 0) {
			throw new RuntimeException("Quantity should be greather than 0");
		}

	    BigDecimal unitPrice = product.getPrice();
	    BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(shoppingCartDetailDTO.getQuantity()));

	    ShoppingCartDetail newCartDetail = new ShoppingCartDetail(
	            cart, product, shoppingCartDetailDTO.getQuantity(), unitPrice, subtotal, LocalDateTime.now());

	    return cartDetailRepository.save(newCartDetail);
	}
	
	private void updateCartTotal(ShoppingCart cart) {
		
		BigDecimal total = cart.getShoppingCartDetails()
				.stream()
				.filter(Objects::nonNull)
	            .map(detail -> 
	                detail.getSubtotal() != null ? detail.getSubtotal() : BigDecimal.ZERO
	            )
	            .reduce(BigDecimal.ZERO, BigDecimal::add);
		
		cart.setTotal(total);
		cart.setUpdatedAt(LocalDateTime.now());
		
		cartRepository.save(cart);
		
	}
	
	

}
