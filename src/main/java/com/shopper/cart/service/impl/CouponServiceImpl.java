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
		return couponRepository.findAll();
	}

	@Override
	public Coupon getCouponById(Long coupon_id) {
		return couponRepository.findById(coupon_id)
				.orElseThrow(() -> new RuntimeException
						("Coupon not found with id: " + coupon_id));
	}

	@Override
	public Coupon createCoupon(Coupon coupon) {
		return couponRepository.save(coupon);
	}

}
