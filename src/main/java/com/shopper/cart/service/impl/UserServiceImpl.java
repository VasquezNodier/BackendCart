package com.shopper.cart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopper.cart.model.User;
import com.shopper.cart.repository.UserRepository;
import com.shopper.cart.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long user_id) {
		return userRepository.findById(user_id).
				orElseThrow(() -> new RuntimeException
						("User not found with id: " + user_id));
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

}
