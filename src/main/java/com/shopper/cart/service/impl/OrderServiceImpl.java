package com.shopper.cart.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopper.cart.model.Order;
import com.shopper.cart.repository.OrderRepository;
import com.shopper.cart.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepository;
	
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Order getOrderById(Long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException
						("Order not found with id: " + orderId));
	}

	@Override
	public Order createOrder(Order order) {
		return orderRepository.save(order);
	}

}
