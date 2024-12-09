package com.shopper.cart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopper.cart.model.Product;
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


}
