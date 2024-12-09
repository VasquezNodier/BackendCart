package com.shopper.cart.service;

import java.util.List;
import com.shopper.cart.model.Product;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long product_id);
    Product createProduct(Product product);
//    Product updateProduct(int id, Product product);
//    void deleteProduct(int id);
}
	

