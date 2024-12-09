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

import com.shopper.cart.model.Coupon;
import com.shopper.cart.service.CouponService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/coupons")
@Tag(name = "Coupon Controller", description = "Controller for coupons")
public class CouponController {
	
	private final CouponService couponService;
	
	@Autowired
	public CouponController(CouponService couponService) {
		this.couponService = couponService;
	}
	
	@GetMapping
	@Operation(
	        summary = "Get all coupons",
	        description = "Retrieves a list of all available coupons.",
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Successfully retrieved all coupons",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = Coupon.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "500",
	                        description = "Internal server error"
	                )
	        }
	)
	public ResponseEntity<List<Coupon>> getAllCoupons() {
	    List<Coupon> coupons = couponService.getAllCoupons();
	    return ResponseEntity.ok(coupons);
	}

	@GetMapping("/{coupon_id}")
	@Operation(
	        summary = "Get a coupon by ID",
	        description = "Retrieves a specific coupon by its ID.",
	        parameters = {
	                @Parameter(
	                        name = "coupon_id",
	                        description = "The unique ID of the coupon",
	                        required = true
	                )
	        },
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Coupon successfully retrieved",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = Coupon.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "404",
	                        description = "Coupon not found"
	                )
	        }
	)
	public ResponseEntity<Coupon> getCouponById(@PathVariable Long coupon_id) {
	    return ResponseEntity.ok(couponService.getCouponById(coupon_id));
	}

	@PostMapping
	@Operation(
	        summary = "Create a new coupon",
	        description = "Creates a new coupon with the provided details.",
	        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	                description = "Coupon details to be created",
	                required = true,
	                content = @Content(
	                        mediaType = "application/json",
	                        schema = @Schema(implementation = Coupon.class)
	                )
	        ),
	        responses = {
	                @ApiResponse(
	                        responseCode = "200",
	                        description = "Coupon successfully created",
	                        content = @Content(
	                                mediaType = "application/json",
	                                schema = @Schema(implementation = Coupon.class)
	                        )
	                ),
	                @ApiResponse(
	                        responseCode = "400",
	                        description = "Invalid input data"
	                )
	        }
	)
	public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
	    return ResponseEntity.ok(couponService.createCoupon(coupon));
	}

	
}
