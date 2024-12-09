package com.shopper.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopper.cart.model.SeasonalDiscount;

@Repository
public interface SeasonalDiscountRepository extends JpaRepository<SeasonalDiscount, Long> {

}
