package com.shopper.cart.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "Shopping Cart API",
				description = "This API provides some functionalities for shopping cart implementation",
				version = "1.0.0",
				contact = @Contact(
						name = "Nodier Alexander Vasquez",
						email = "vasqueznap@gmail.com"
						)
				)
)
public class SwaggerConfig {

}
