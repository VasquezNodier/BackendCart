package com.shopper.cart.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ShoppingCartDetailKey implements Serializable {
	
	@Column(name = "cart_id")
    private Long cartId;

    @Column(name = "product_id")
    private Long productId;
    
	public ShoppingCartDetailKey() { }

	public ShoppingCartDetailKey(Long cartId, Long productId) {
		this.cartId = cartId;
		this.productId = productId;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(cartId, productId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ShoppingCartDetailKey that = (ShoppingCartDetailKey) obj;
        return Objects.equals(cartId, that.cartId) && Objects.equals(productId, that.productId);
    }
    
    

}
