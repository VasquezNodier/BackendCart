package com.shopper.cart.model;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity
@Table(name = "coupons", schema = "shopper")
public class Coupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50, unique = true)
    private String code;

    @Column(name = "discount_percent", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountPercent;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean used;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
   
    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Order> order = new ArrayList<>();

	public Coupon() {
	}

	public Coupon(String code, BigDecimal discountPercent, LocalDate startDate, LocalDate endDate, boolean used,
			LocalDateTime createdAt) {
		this.code = code;
		this.discountPercent = discountPercent;
		this.startDate = startDate;
		this.endDate = endDate;
		this.used = used;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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
		return "Coupon [id=" + id + ", code=" + code + ", discountPercent=" + discountPercent + ", startDate="
				+ startDate + ", endDate=" + endDate + ", used=" + used + "]";
	}
    
}
