package com.shopper.cart.dto;

import java.math.BigDecimal;

public class ShoppingCartDTO {
	
	private Long userId;
	private BigDecimal total;
	private boolean active;
	
	public ShoppingCartDTO(Long userId, BigDecimal total, boolean active) {
		super();
		this.userId = userId;
		this.total = total;
		this.active = active;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
    
}
