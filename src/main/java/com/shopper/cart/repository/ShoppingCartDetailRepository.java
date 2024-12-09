package com.shopper.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopper.cart.model.ShoppingCartDetail;
import com.shopper.cart.model.ShoppingCartDetailKey;

@Repository
public interface ShoppingCartDetailRepository extends JpaRepository<ShoppingCartDetail, ShoppingCartDetailKey> {

}
