package com.shopper.cart.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "shopping_carts", schema = "shopper")
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal total;
	
	@Column(nullable = false)
	private boolean active;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at", updatable = true)
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ShoppingCartDetail> shoppingCartDetails = new ArrayList<>();
	
	@OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Order> order = new ArrayList<>();

	public ShoppingCart() {

	}

	public ShoppingCart(User user, BigDecimal total, boolean active, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.user = user;
		this.total = total;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	public List<ShoppingCartDetail> getShoppingCartDetails() {
		return shoppingCartDetails;
	}

	public void setShoppingCartDetails(List<ShoppingCartDetail> shoppingCartDetails) {
		this.shoppingCartDetails = shoppingCartDetails;
	}
	
	public void addCartDetail(ShoppingCartDetail cartDetail) {
        shoppingCartDetails.add(cartDetail);
        cartDetail.setShoppingCart(this);
    }

    public void removeCartDetail(ShoppingCartDetail cartDetail) {
        shoppingCartDetails.remove(cartDetail);
        cartDetail.setShoppingCart(null);
    }

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", user=" + user + ", total=" + total + ", active=" + active + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
	

}
