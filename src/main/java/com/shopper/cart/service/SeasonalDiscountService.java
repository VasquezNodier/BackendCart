package com.shopper.cart.service;

import java.util.List;

import com.shopper.cart.dto.SeasonalDiscountDTO;
import com.shopper.cart.model.SeasonalDiscount;

public interface SeasonalDiscountService {
	List<SeasonalDiscount> getAllDiscounts();
	SeasonalDiscount getDiscountById(Long discount_id);
	SeasonalDiscount createDiscount(SeasonalDiscountDTO discountDTO);
}
