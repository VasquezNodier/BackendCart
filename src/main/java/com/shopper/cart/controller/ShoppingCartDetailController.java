package com.shopper.cart.controller;

import java.util.List;

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

import com.shopper.cart.dto.ShoppingCartDetailDTO;
import com.shopper.cart.model.ShoppingCartDetail;
import com.shopper.cart.model.ShoppingCartDetailKey;
import com.shopper.cart.service.ShoppingCartDetailService;
import com.shopper.cart.service.impl.ShoppingCartDetailServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/cart-details")
@Tag(name = "Shopping Cart Details", description = "Controller for cart details")
public class ShoppingCartDetailController {
	
	private final ShoppingCartDetailService cartDetailService;

	public ShoppingCartDetailController(ShoppingCartDetailService cartDetailService) {
		this.cartDetailService = cartDetailService;
	}
	
	@GetMapping
	@Operation(
	        summary = "Get all cart details",
	        description = "Retrieves a list of all shopping cart details.",
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Successfully retrieved all cart details",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = ShoppingCartDetail.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "500",
	                        description = "Internal server error"
	                )
	        }
	)
	public ResponseEntity<List<ShoppingCartDetail>> getAllCartDetails(){
		return ResponseEntity.ok(cartDetailService.getAllCartDetails());
	}
	
	@GetMapping("/{cartId}/{productId}")
	@Operation(
	        summary = "Get cart detail by ID",
	        description = "Retrieves a specific cart detail using its compound ID.",
	        parameters = {
	                @Parameter(
	                        name = "cartId",
	                        description = "The ID of the shopping cart",
	                        required = true
	                ),
	                @Parameter(
	                        name = "productId",
	                        description = "The ID of the product",
	                        required = true
	                )
	        },
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Cart detail successfully retrieved",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = ShoppingCartDetailDTO.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "Cart detail not found"
	                )
	        }
	)
    public ResponseEntity<ShoppingCartDetailDTO> getCartDetailById(
            @PathVariable Long cartId, 
            @PathVariable Long productId) {
        
        ShoppingCartDetailKey key = new ShoppingCartDetailKey(cartId, productId);
        return ResponseEntity.ok(cartDetailService.getCartDetailById(key));
    }
	
	@PostMapping
	@Operation(
	        summary = "Create a new cart detail",
	        description = "Creates a new entry in the shopping cart details.",
	        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	                description = "Cart detail information",
	                required = true,
	                content = @Content(
	                        mediaType = "application/json",
	                        schema = @Schema(implementation = ShoppingCartDetailDTO.class)
	                )
	        ),
	        responses = {
	                @ApiResponse(
	                        responseCode = "201",
	                        description = "Cart detail successfully created",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = ShoppingCartDetail.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "400",
	                        description = "Invalid request data"
	                )
	        }
	)
    public ResponseEntity<ShoppingCartDetail> createCartDetail(
            @RequestBody ShoppingCartDetailDTO shoppingCartDetailDTO) {
        
        ShoppingCartDetail createdCartDetail = cartDetailService.createCartDetail(shoppingCartDetailDTO);
        return new ResponseEntity<>(createdCartDetail, HttpStatus.CREATED);
    }
	
	@PatchMapping("/{cartId}/{productId}")
	@Operation(
	        summary = "Update the quantity of a cart detail",
	        description = "Updates the quantity of a specific cart detail using its compound ID.",
	        parameters = {
	                @Parameter(name = "cartId", description = "The ID of the shopping cart", required = true),
	                @Parameter(name = "productId", description = "The ID of the product", required = true),
	                @Parameter(name = "quantity", description = "The updated quantity", required = true)
	        },
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Cart detail successfully updated",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = ShoppingCartDetail.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "Cart detail not found"
	                ),
	                @ApiResponse(
	                        responseCode = "400",
	                        description = "Invalid request"
	                )
	        }
	)
    public ResponseEntity<ShoppingCartDetail> patchCartDetail(
            @PathVariable Long cartId, 
            @PathVariable Long productId, 
            @RequestParam Integer quantity) {
        
        ShoppingCartDetailKey key = new ShoppingCartDetailKey(cartId, productId);
        ShoppingCartDetail patchedCartDetail = cartDetailService.patchCartDetail(key, quantity);
        
        return ResponseEntity.ok(patchedCartDetail);
    }
	

	@DeleteMapping("/{cartId}/{productId}")
	@Operation(
	        summary = "Delete a cart detail",
	        description = "Deletes a specific cart detail using its compound ID.",
	        parameters = {
	                @Parameter(name = "cartId", description = "The ID of the shopping cart", required = true),
	                @Parameter(name = "productId", description = "The ID of the product", required = true)
	        },
	        responses = {
	                @ApiResponse(
	                        responseCode = "204",
	                        description = "Cart detail successfully deleted"
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "Cart detail not found"
	                )
	        }
	)
    public ResponseEntity<Void> deleteCartDetail(
            @PathVariable Long cartId, 
            @PathVariable Long productId) {
        
        ShoppingCartDetailKey key = new ShoppingCartDetailKey(cartId, productId);
        cartDetailService.deleteCartDetail(key);
        return ResponseEntity.noContent().build();
    }
}
