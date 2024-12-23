package com.shopper.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopper.cart.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
