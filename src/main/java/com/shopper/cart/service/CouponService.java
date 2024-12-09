package com.shopper.cart.service;

import java.util.List;

import com.shopper.cart.model.Coupon;

public interface CouponService {
	
	List<Coupon> getAllCoupons();
	Coupon getCouponById(Long coupon_id);
	Coupon createCoupon(Coupon coupon);

}
