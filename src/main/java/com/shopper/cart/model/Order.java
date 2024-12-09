package com.shopper.cart.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders", schema = "shopper")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="shopping_cart_id")
	@JsonBackReference
	private ShoppingCart shoppingCart;
	
	@ManyToOne
	@JoinColumn(name="coupon_id")
	@JsonBackReference
	private Coupon coupon;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal subtotal;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal discount;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal total;
	
	@Column(name = "order_date")
    private LocalDateTime orderDate;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at", updatable = true)
	private LocalDateTime updatedAt;
	
	
	public Order() {
	}

	public Order(ShoppingCart shoppingCart, Coupon coupon, BigDecimal subtotal, BigDecimal discount,
			BigDecimal total, LocalDateTime orderDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.shoppingCart = shoppingCart;
		this.coupon = coupon;
		this.subtotal = subtotal;
		this.discount = discount;
		this.total = total;
		this.orderDate = orderDate;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", shoppingCart=" + shoppingCart + ", coupon=" + coupon + ", subtotal=" + subtotal
				+ ", discount=" + discount + ", total=" + total + ", orderDate=" + orderDate + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
}
