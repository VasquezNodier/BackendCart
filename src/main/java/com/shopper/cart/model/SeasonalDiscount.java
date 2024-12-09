package com.shopper.cart.model;

import java.time.LocalDate;
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
@Table(name = "seasonal_discounts", schema = "shopper")
public class SeasonalDiscount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="product_id",nullable = false)
	@JsonBackReference
	private Product product;
	
	private double percentage;
	
	@Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    public SeasonalDiscount() {

	}

	public SeasonalDiscount(Product product_id, double percentage, LocalDate startDate, LocalDate endDate,
			LocalDateTime createdAt) {
		this.product = product_id;
		this.percentage = percentage;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
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

	@Override
	public String toString() {
		return "SeasonalDiscount [id=" + id + ", product_id=" + product + ", percentage=" + percentage + ", startDate="
				+ startDate + ", endDate=" + endDate + ", createdAt=" + createdAt + "]";
	}
	
}
