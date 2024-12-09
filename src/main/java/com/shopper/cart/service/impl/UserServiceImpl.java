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
	    /**
	     * Retrieves all users from the database.
	     *
	     * @return List of all User objects.
	     */
	    return userRepository.findAll();
	}

	@Override
	public User getUserById(Long user_id) {
	    /**
	     * Retrieves a specific user by their unique ID.
	     *
	     * @param user_id The ID of the user to be retrieved.
	     * @return The corresponding User object if found.
	     * @throws RuntimeException If the user is not found.
	     */
	    return userRepository.findById(user_id)
	            .orElseThrow(() -> new RuntimeException("User not found with id: " + user_id));
	}

	@Override
	public User createUser(User user) {
	    /**
	     * Creates a new user and saves them to the database.
	     *
	     * @param user The User object containing user details to be created.
	     * @return The created User object saved in the database.
	     */
	    return userRepository.save(user);
	}

}
