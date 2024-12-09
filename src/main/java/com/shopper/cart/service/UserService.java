package com.shopper.cart.service;

import java.util.List;

import com.shopper.cart.model.User;

public interface UserService {
	List<User> getAllUsers();
	User getUserById(Long user_id);
	User createUser(User user);
}
