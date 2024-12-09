package com.shopper.cart.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shopper.cart.dto.OrderDTO;
import com.shopper.cart.model.Coupon;
import com.shopper.cart.model.Order;
import com.shopper.cart.model.Product;
import com.shopper.cart.model.ShoppingCart;
import com.shopper.cart.repository.CouponRepository;
import com.shopper.cart.repository.OrderRepository;
import com.shopper.cart.repository.ProductRepository;
import com.shopper.cart.repository.ShoppingCartRepository;
import com.shopper.cart.service.OrderService;
import com.shopper.cart.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepository;
	private final ShoppingCartRepository cartRepository;
	private final CouponRepository couponRepository;
	private final ProductRepository productRepository;
	private final ProductServiceImpl productService;
	
	public OrderServiceImpl(OrderRepository orderRepository,
			ShoppingCartRepository cartRepository,
			CouponRepository couponRepository,
			ProductRepository productRepository,
			ProductServiceImpl productService) {
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.couponRepository = couponRepository;
		this.productRepository = productRepository;
		this.productService = productService;
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Order getOrderById(Long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException
						("Order not found with id: " + orderId));
	}

	@Override
	public Order createOrder(OrderDTO orderDTO) {
		
		ShoppingCart shoppingCart = findCartById(orderDTO);

		Coupon coupon = null;
	    BigDecimal discount = BigDecimal.ZERO;

		if (orderDTO.getCouponId() != null) {
	        coupon = findCouponById(orderDTO);

	        if (coupon.isUsed()) {
	            throw new RuntimeException("This coupon has been used.");
	        }

	        if (!validateCouponDate(coupon)) {
	            throw new RuntimeException("This coupon is not valid for the current date.");
	        }

	        discount = calculateDiscount(coupon, calculateSubtotal(shoppingCart));
	        
	        coupon.setUsed(true);
	    }
		
		BigDecimal subtotal = calculateSubtotal(shoppingCart);
	    BigDecimal total = subtotal.subtract(discount);
	    
	    Order order = new Order();
	    order.setShoppingCart(shoppingCart);
	    order.setCoupon(coupon);
	    order.setSubtotal(subtotal);
	    order.setDiscount(discount);
	    order.setTotal(total);
	    order.setOrderDate(LocalDateTime.now());
	    
	    shoppingCart.setActive(false);
	    
	    productService.updateProductStock(shoppingCart);
	    
		return orderRepository.save(order);
	}
	
	/**
	 * Calculates the subtotal of a shopping cart based on its details.
	 *
	 * @param shoppingCart The shopping cart whose subtotal is calculated.
	 * @return The calculated subtotal as BigDecimal.
	 */
	private BigDecimal calculateSubtotal(ShoppingCart shoppingCart) {
	    return shoppingCart.getShoppingCartDetails()
	            .stream()
	            .map(detail -> detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuant())))
	            .reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	/**
	 * Calculates the discount based on the provided coupon and subtotal.
	 *
	 * @param coupon   The applied coupon.
	 * @param subtotal The subtotal amount.
	 * @return The calculated discount amount as BigDecimal.
	 */
	private BigDecimal calculateDiscount(Coupon coupon, BigDecimal subtotal) {
	    if (coupon == null) {
	        return BigDecimal.ZERO;
	    }
	    
	    BigDecimal discountPercentage = coupon.getDiscountPercent()
	    		.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);;
	    		
	    return subtotal.multiply(discountPercentage);
	}
	
	/**
	 * Finds a shopping cart by its ID from the provided OrderDTO.
	 *
	 * @param orderDTO The data transfer object containing the shopping cart ID.
	 * @return The found ShoppingCart object.
	 * @throws RuntimeException If the shopping cart is not found.
	 */
	private ShoppingCart findCartById(OrderDTO orderDTO) {
		return cartRepository.findById(orderDTO.getShoppingCartId())
				.orElseThrow(() -> new RuntimeException
		        		("Shopping Cart not found with id: " + orderDTO.getShoppingCartId()));
				
	}
	
	/**
	 * Finds a coupon by its ID from the provided OrderDTO.
	 *
	 * @param orderDTO The data transfer object containing the coupon ID.
	 * @return The found Coupon object.
	 * @throws RuntimeException If the coupon is not found.
	 */
	private Coupon findCouponById(OrderDTO orderDTO) {
		return couponRepository.findById(orderDTO.getCouponId())
		.orElseThrow(() -> new RuntimeException
				("Coupon not found with id: " + orderDTO.getCouponId()));

	}
	
	/**
	 * Validates if a coupon's date range is valid for the current date.
	 *
	 * @param coupon The coupon to be validated.
	 * @return true if the coupon is valid, false otherwise.
	 */
	private boolean validateCouponDate(Coupon coupon) {
		
		LocalDate today = LocalDate.now();
		
	    if (today.isBefore(coupon.getStartDate()) || today.isAfter(coupon.getEndDate())) {
	        return false;
	    }
	    return true;
	}
	
	/**
	 * Updates the stock of products based on the shopping cart details.
	 *
	 * @param shoppingCart The shopping cart whose product stock will be updated.
	 * @throws RuntimeException If there is insufficient product stock for any item.
	 */
	private void updateProductStock(ShoppingCart shoppingCart) {
	    shoppingCart.getShoppingCartDetails().forEach(cartDetail -> {
	        Product product = cartDetail.getProduct();

	        if (product.getStock() < cartDetail.getQuant()) {
	            throw new RuntimeException("Insufficient stock for product: " + product.getName());
	        }

	        product.setStock(product.getStock() - cartDetail.getQuant());

	        productRepository.save(product);
	    });
	}

}
