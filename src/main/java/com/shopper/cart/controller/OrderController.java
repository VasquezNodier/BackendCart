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

import com.shopper.cart.model.Order;
import com.shopper.cart.model.Order;
import com.shopper.cart.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Order Controller", description = "Controller for orders")
public class OrderController {
	
	private final OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping
	@Operation(
	        summary = "Get all orders",
	        description = "Retrieves a list of all customer orders.",
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Successfully retrieved all orders",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = Order.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "500",
	                        description = "Internal server error"
	                )
	        }
	)
	public ResponseEntity<List<Order>> getAllOrders() {
	    List<Order> orders = orderService.getAllOrders();
	    return ResponseEntity.ok(orders);
	}

	@GetMapping("/{orderId}")
	@Operation(
	        summary = "Get order by ID",
	        description = "Retrieves a specific order by its unique ID.",
	        parameters = {
	                @Parameter(
	                        name = "orderId",
	                        description = "The unique ID of the order",
	                        required = true
	                )
	        },
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Order successfully retrieved",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = Order.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "Order not found"
	                )
	        }
	)
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
	    return ResponseEntity.ok(orderService.getOrderById(orderId));
	}

	@PostMapping
	@Operation(
	        summary = "Create a new order",
	        description = "Creates a new order with the provided details.",
	        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	                description = "Order details to be created",
	                required = true,
	                content = @Content(
	                        mediaType = "application/json",
	                        schema = @Schema(implementation = Order.class)
	                )
	        ),
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Order successfully created",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = Order.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "400",
	                        description = "Invalid input data"
	                )
	        }
	)
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
	    return ResponseEntity.ok(orderService.createOrder(order));
	}

}
