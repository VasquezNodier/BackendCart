package com.shopper.cart.service;

import java.util.List;

import com.shopper.cart.dto.ShoppingCartDetailDTO;
import com.shopper.cart.model.ShoppingCartDetail;
import com.shopper.cart.model.ShoppingCartDetailKey;

public interface ShoppingCartDetailService {
	
	List<ShoppingCartDetail> getAllCartDetails();
//	ShoppingCartDetail getCartDetailById(ShoppingCartDetailKey key);
	ShoppingCartDetailDTO getCartDetailById(ShoppingCartDetailKey key);
	ShoppingCartDetail createCartDetail(ShoppingCartDetailDTO shoppingCartDetailDTO);
	ShoppingCartDetail patchCartDetail(ShoppingCartDetailKey id, Integer quantity);
    void deleteCartDetail(ShoppingCartDetailKey id);
    
}
