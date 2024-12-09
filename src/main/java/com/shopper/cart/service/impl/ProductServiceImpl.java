package com.shopper.cart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopper.cart.model.Product;
import com.shopper.cart.model.ShoppingCart;
import com.shopper.cart.repository.ProductRepository;
import com.shopper.cart.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	@Override
	public Product getProductById(Long product_id){
		return productRepository.findById(product_id)
				.orElseThrow(() -> new RuntimeException
						("Product not found with id: " + product_id));
	}

	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}
	
	/**
	 * Updates the stock of products based on the shopping cart details.
	 *
	 * @param shoppingCart The shopping cart whose product stock will be updated.
	 * @throws RuntimeException If there is insufficient product stock for any item.
	 */
	public void updateProductStock(ShoppingCart shoppingCart) {
	    shoppingCart.getShoppingCartDetails().forEach(cartDetail -> {
	        Product product = cartDetail.getProduct();

	        if (product.getStock() < cartDetail.getQuant()) {
	            throw new RuntimeException("Insufficient stock for product: " + product.getName());
	        }

	        product.setStock(product.getStock() - cartDetail.getQuant());

	        productRepository.save(product);
	    });
	}


}
