package com.shopper.cart.service;

import java.util.List;

import com.shopper.cart.dto.OrderDTO;
import com.shopper.cart.model.Order;

public interface OrderService {
	
	List<Order> getAllOrders();
	Order getOrderById(Long orderId);
	Order createOrder(OrderDTO order);
	
}
