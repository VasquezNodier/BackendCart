package com.shopper.cart.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopper.cart.dto.SeasonalDiscountDTO;
import com.shopper.cart.model.SeasonalDiscount;
import com.shopper.cart.service.SeasonalDiscountService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Hidden
@RequestMapping("/api/v1/discounts")
@Tag(name = "Seasonal Discount Controller", description = "Controller for seasonal discount")
public class SeasonalDiscountController {

	private final SeasonalDiscountService discountService;

	@Autowired
	public SeasonalDiscountController(SeasonalDiscountService discountService) {
		this.discountService = discountService;
	}
	
	@GetMapping
	@Operation(
	        summary = "Get all seasonal discounts",
	        description = "Retrieves a list of all available seasonal discounts.",
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Successfully retrieved all seasonal discounts",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = SeasonalDiscount.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "500",
	                        description = "Internal server error"
	                )
	        }
	)
	public ResponseEntity<List<SeasonalDiscount>> getAllSeasonalDiscounts() {
	    List<SeasonalDiscount> discounts = discountService.getAllDiscounts();
	    return ResponseEntity.ok(discounts);
	}

	@GetMapping("/{discount_id}")
	@Operation(
	        summary = "Get seasonal discount by ID",
	        description = "Retrieves a specific seasonal discount by its unique ID.",
	        parameters = {
	                @Parameter(
	                        name = "discount_id",
	                        description = "The unique ID of the seasonal discount",
	                        required = true
	                )
	        },
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Seasonal discount successfully retrieved",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = SeasonalDiscount.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "Seasonal discount not found"
	                )
	        }
	)
	public ResponseEntity<SeasonalDiscount> getSeasonalDiscountById(@PathVariable Long discount_id) {
	    return ResponseEntity.ok(discountService.getDiscountById(discount_id));
	}

	@PostMapping
	@Operation(
	        summary = "Create a new seasonal discount",
	        description = "Creates a new seasonal discount with the provided details.",
	        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	                description = "Seasonal discount details to be created",
	                required = true,
	                content = @Content(
	                        mediaType = "application/json",
	                        schema = @Schema(implementation = SeasonalDiscountDTO.class)
	                )
	        ),
	        responses = {
	                @ApiResponse(
	                        responseCode = "201",
	                        description = "Seasonal discount successfully created",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = SeasonalDiscount.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "400",
	                        description = "Invalid input data"
	                )
	        }
	)
	public ResponseEntity<SeasonalDiscount> createSeasonalDiscount(@RequestBody SeasonalDiscountDTO discountDTO) {
	    SeasonalDiscount createdDiscount = discountService.createDiscount(discountDTO);
	    return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
	}
}
