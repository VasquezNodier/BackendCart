package com.shopper.cart.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {
	
	private Long shoppingCartId;
	private Long couponId;
	private BigDecimal subtotal;
	private BigDecimal discount;
	private BigDecimal total;
	private LocalDateTime orderDate;
	
	public OrderDTO() {
    }
	
	public OrderDTO(Long shoppingCartId, Long couponId, BigDecimal subtotal, BigDecimal discount, BigDecimal total,
			LocalDateTime orderDate) {
		this.shoppingCartId = shoppingCartId;
		this.couponId = couponId;
		this.subtotal = subtotal;
		this.discount = discount;
		this.total = total;
		this.orderDate = orderDate;
	}
	
	public OrderDTO(Long shoppingCartId, BigDecimal subtotal, BigDecimal discount, BigDecimal total,
			LocalDateTime orderDate) {
		this.shoppingCartId = shoppingCartId;
		this.subtotal = subtotal;
		this.discount = discount;
		this.total = total;
		this.orderDate = orderDate;
	}



	public Long getShoppingCartId() {
		return shoppingCartId;
	}
	public void setShoppingCartId(Long shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public LocalDateTime getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	
	
}
