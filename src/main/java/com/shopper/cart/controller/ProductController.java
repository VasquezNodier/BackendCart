package com.shopper.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopper.cart.model.Product;
import com.shopper.cart.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product Controller", description = "Controller for products")
public class ProductController {
	
	private final ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	@Operation(
	        summary = "Get all products",
	        description = "Retrieves a list of all available products.",
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Successfully retrieved all products",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = Product.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "500",
	                        description = "Internal server error"
	                )
	        }
	)
	public ResponseEntity<List<Product>> getAllProducts() {
	    List<Product> products = productService.getAllProducts();
	    return ResponseEntity.ok(products);
	}

	@GetMapping("/{product_id}")
	@Operation(
	        summary = "Get a product by ID",
	        description = "Retrieves a specific product by its unique ID.",
	        parameters = {
	                @Parameter(
	                        name = "product_id",
	                        description = "The unique ID of the product",
	                        required = true
	                )
	        },
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Product successfully retrieved",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = Product.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "Product not found"
	                )
	        }
	)
	public ResponseEntity<Product> getProductById(@PathVariable Long product_id) {
	    return ResponseEntity.ok(productService.getProductById(product_id));
	}

	@PostMapping
	@Operation(
	        summary = "Create a new product",
	        description = "Creates a new product with the provided details.",
	        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	                description = "Product details for the new product",
	                required = true,
	                content = @Content(
	                        mediaType = "application/json",
	                        schema = @Schema(implementation = Product.class)
	                )
	        ),
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Product successfully created",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = Product.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "400",
	                        description = "Invalid input data"
	                )
	        }
	)
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
	    return ResponseEntity.ok(productService.createProduct(product));
	}


	
	

}
