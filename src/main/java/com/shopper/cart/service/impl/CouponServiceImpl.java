package com.shopper.cart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopper.cart.model.Coupon;
import com.shopper.cart.repository.CouponRepository;
import com.shopper.cart.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {
	
	private final CouponRepository couponRepository;
	
	@Autowired
	public CouponServiceImpl(CouponRepository couponRepository) {
		this.couponRepository = couponRepository;
	}
	@Override
	public List<Coupon> getAllCoupons() {
	    /**
	     * Retrieves all coupons from the database.
	     *
	     * @return List of all Coupon objects.
	     */
	    return couponRepository.findAll();
	}

	@Override
	public Coupon getCouponById(Long coupon_id) {
	    /**
	     * Retrieves a specific coupon by its unique ID.
	     *
	     * @param coupon_id The ID of the coupon to be retrieved.
	     * @return The corresponding Coupon object if found.
	     * @throws RuntimeException If the coupon is not found.
	     */
	    return couponRepository.findById(coupon_id)
	            .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + coupon_id));
	}

	@Override
	public Coupon createCoupon(Coupon coupon) {
	    /**
	     * Creates a new coupon and saves it to the database.
	     *
	     * @param coupon The Coupon object containing coupon details to be created.
	     * @return The created Coupon object saved in the database.
	     */
	    return couponRepository.save(coupon);
	}

}
