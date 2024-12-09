package com.shopper.cart.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopper.cart.dto.ShoppingCartDTO;
import com.shopper.cart.model.ShoppingCart;
import com.shopper.cart.service.ShoppingCartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/carts")
@Tag(name = "Shopping Cart Controller", description = "Controller for shoppoing cart")
public class ShoppingCartController {
	
	private final ShoppingCartService cartService;
	
	@Autowired
	public ShoppingCartController(ShoppingCartService cartService) {
		this.cartService = cartService;
	}
	
	@GetMapping
	@Operation(
	        summary = "Get all shopping carts",
	        description = "Retrieves a list of all shopping carts.",
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Successfully retrieved all shopping carts",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = ShoppingCart.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "500",
	                        description = "Internal server error"
	                )
	        }
	)
	public ResponseEntity<List<ShoppingCart>> getAllCarts() {
	    List<ShoppingCart> carts = cartService.getAllCarts();
	    return ResponseEntity.ok(carts);
	}

	@GetMapping("/{cart_id}")
	@Operation(
	        summary = "Get shopping cart by ID",
	        description = "Retrieves a specific shopping cart by its ID.",
	        parameters = {
	                @Parameter(
	                        name = "cart_id",
	                        description = "The ID of the shopping cart",
	                        required = true
	                )
	        },
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Successfully retrieved the shopping cart",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = ShoppingCart.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "Shopping cart not found"
	                )
	        }
	)
	public ResponseEntity<ShoppingCart> getCartById(@PathVariable Long cart_id) {
	    return ResponseEntity.ok(cartService.getCartById(cart_id));
	}

	@PostMapping
	@Operation(
	        summary = "Create a new shopping cart",
	        description = "Creates a new shopping cart with the provided details.",
	        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	                description = "Shopping cart details to be created",
	                required = true,
	                content = @Content(
	                        mediaType = "application/json",
	                        schema = @Schema(implementation = ShoppingCartDTO.class)
	                )
	        ),
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Successfully created the shopping cart",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = ShoppingCart.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "400",
	                        description = "Invalid input data"
	                )
	        }
	)
	public ResponseEntity<ShoppingCart> createCart(@RequestBody ShoppingCartDTO cart) {
	    return ResponseEntity.ok(cartService.createCart(cart));
	}

	@PutMapping("/{cart_id}")
	@Operation(
	        summary = "Update a shopping cart",
	        description = "Updates a shopping cart by its ID.",
	        parameters = {
	                @Parameter(
	                        name = "cart_id",
	                        description = "The ID of the shopping cart to be updated",
	                        required = true
	                )
	        },
	        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	                description = "Updated shopping cart details",
	                required = true,
	                content = @Content(
	                        mediaType = "application/json",
	                        schema = @Schema(implementation = ShoppingCart.class)
	                )
	        ),
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Successfully updated the shopping cart",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = ShoppingCart.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "Shopping cart not found"
	                )
	        }
	)
	public ResponseEntity<ShoppingCart> updateCart(
	        @PathVariable Long cart_id, 
	        @RequestBody ShoppingCart cart) {
	    return ResponseEntity.ok(cartService.updateCart(cart_id, cart));
	}

	@PatchMapping("/{cart_id}")
	@Operation(
	        summary = "Partially update a shopping cart",
	        description = "Partially updates the total and/or active status of a shopping cart by its ID.",
	        parameters = {
	                @Parameter(
	                        name = "cart_id",
	                        description = "The ID of the shopping cart to be partially updated",
	                        required = true
	                ),
	                @Parameter(
	                        name = "total",
	                        description = "The updated total amount",
	                        required = false
	                ),
	                @Parameter(
	                        name = "active",
	                        description = "The updated active status",
	                        required = false
	                )
	        },
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Successfully updated the shopping cart",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = ShoppingCart.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "Shopping cart not found"
	                )
	        }
	)
	public ResponseEntity<ShoppingCart> partialUpdateCart(
	        @PathVariable Long cart_id, 
	        @RequestParam(required = false) BigDecimal total, 
	        @RequestParam(required = false) Boolean active) {
	    return ResponseEntity.ok(cartService.partialUpdateCart(cart_id, total, active));
	}

	@DeleteMapping("/{cart_id}")
	@Operation(
	        summary = "Delete a shopping cart",
	        description = "Deletes a shopping cart by its ID.",
	        parameters = {
	                @Parameter(
	                        name = "cart_id",
	                        description = "The ID of the shopping cart to be deleted",
	                        required = true
	                )
	        },
	        responses = {
	                @ApiResponse(
	                        responseCode = "204",
	                        description = "Shopping cart successfully deleted"
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "Shopping cart not found"
	                )
	        }
	)
	public ResponseEntity<Void> deleteCart(@PathVariable Long cart_id) {
	    cartService.deleteCart(cart_id);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


}
