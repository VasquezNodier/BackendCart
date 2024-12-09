package com.shopper.cart.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity
@Table(name = "shopping_cart_details", schema = "shopper")
public class ShoppingCartDetail {
	
	@EmbeddedId
	private ShoppingCartDetailKey id;
	
	@ManyToOne
	@MapsId("cartId")
	@JoinColumn(name="cart_id")
	@JsonIgnore
	private ShoppingCart shoppingCart;
	
	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name="product_id")
	@JsonIgnore
	private Product product;
	
	@Column(nullable = false)
	private int quant;
	
	@Column(name="unit_price", nullable = false, precision = 10, scale = 2)
	private BigDecimal unitPrice;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal subtotal;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	public ShoppingCartDetail() {
	}

	public ShoppingCartDetail(ShoppingCart shoppingCart, Product product, int quant, BigDecimal unitPrice,
			BigDecimal subtotal, LocalDateTime createdAt) {
		this.id = new ShoppingCartDetailKey(shoppingCart.getId(), product.getId());
		this.shoppingCart = shoppingCart;
		this.product = product;
		this.quant = quant;
		this.unitPrice = unitPrice;
		this.subtotal = subtotal;
		this.createdAt = createdAt;
	}

	public ShoppingCartDetailKey getId() {
		return id;
	}

	public void setId(ShoppingCartDetailKey id) {
		this.id = id;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuant() {
		return quant;
	}

	public void setQuant(int quant) {
		this.quant = quant;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}
}
