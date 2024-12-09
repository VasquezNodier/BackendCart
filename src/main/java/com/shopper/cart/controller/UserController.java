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

import com.shopper.cart.model.User;
import com.shopper.cart.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller", description = "Controller for users")
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	@Operation(
	        summary = "Get all users",
	        description = "Retrieves a list of all users.",
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Successfully retrieved all users",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = User.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "500",
	                        description = "Internal server error"
	                )
	        }
	)
	public ResponseEntity<List<User>> getAllUsers() {
	    return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/{user_id}")
	@Operation(
	        summary = "Get a user by ID",
	        description = "Retrieves a specific user by their ID.",
	        parameters = {
	                @Parameter(
	                        name = "user_id",
	                        description = "The unique ID of the user",
	                        required = true
	                )
	        },
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "User successfully retrieved",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = User.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "User not found"
	                )
	        }
	)
	public ResponseEntity<User> getUserById(@PathVariable Long user_id) {
	    return ResponseEntity.ok(userService.getUserById(user_id));
	}

	@PostMapping
	@Operation(
	        summary = "Create a new user",
	        description = "Creates a new user with the provided details.",
	        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	                description = "User details for the new user",
	                required = true,
	                content = @Content(
	                        mediaType = "application/json",
	                        schema = @Schema(implementation = User.class)
	                )
	        ),
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "User successfully created",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = User.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "400",
	                        description = "Invalid input data"
	                )
	        }
	)
	public ResponseEntity<User> createUser(@RequestBody User user) {
	    System.out.println(user);
	    return ResponseEntity.ok(userService.createUser(user));
	}


}
