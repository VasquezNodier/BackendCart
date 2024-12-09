package com.shopper.cart.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopper.cart.dto.SeasonalDiscountDTO;
import com.shopper.cart.model.Product;
import com.shopper.cart.model.SeasonalDiscount;
import com.shopper.cart.repository.ProductRepository;
import com.shopper.cart.repository.SeasonalDiscountRepository;
import com.shopper.cart.service.SeasonalDiscountService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SeasonalDiscountServiceImpl implements SeasonalDiscountService {
	
	private final SeasonalDiscountRepository discountRepository;
	private final ProductRepository productRepository;
	
	
	@Autowired
	public SeasonalDiscountServiceImpl(SeasonalDiscountRepository discountRepository, ProductRepository productRepository) {
        this.discountRepository = discountRepository;
        this.productRepository = productRepository;
    }

	@Override
	public List<SeasonalDiscount> getAllDiscounts() {
	    /**
	     * Retrieves all seasonal discounts from the database.
	     *
	     * @return List of all SeasonalDiscount objects.
	     */
	    return discountRepository.findAll();
	}

	@Override
	public SeasonalDiscount getDiscountById(Long discount_id) {
	    /**
	     * Retrieves a specific seasonal discount by its unique ID.
	     *
	     * @param discount_id The ID of the seasonal discount to be retrieved.
	     * @return The corresponding SeasonalDiscount object if found.
	     * @throws RuntimeException If the discount is not found.
	     */
	    return discountRepository.findById(discount_id)
	            .orElseThrow(() -> new RuntimeException("Discount not found with id: " + discount_id));
	}

	@Override
	public SeasonalDiscount createDiscount(SeasonalDiscountDTO discountDTO) {
	    /**
	     * Creates a new seasonal discount associated with a specific product.
	     * Validates the existence of the product before creating the discount.
	     *
	     * @param discountDTO The data transfer object containing discount details.
	     * @return The created SeasonalDiscount object saved in the database.
	     * @throws RuntimeException If the product associated with the discount is not found.
	     */
	    Product product = productRepository.findById(discountDTO.getProductId())
	            .orElseThrow(() -> new RuntimeException(
	                    "Product not found with id: " + discountDTO.getProductId()));

	    SeasonalDiscount discount = new SeasonalDiscount(
	            product,
	            discountDTO.getPercentage(),
	            discountDTO.getStartDate(),
	            discountDTO.getEndDate(),
	            LocalDateTime.now()
	    );

	    return discountRepository.save(discount);
	}

}
