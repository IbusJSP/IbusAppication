package com.ibus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	//CustomerException Constructor 
	public CustomerException(String message) {
		super(message);
	}
	
	//duplicate userId msg
		public static CustomerException duplicateUserId(String userId) {
			return new CustomerException("User id : "+userId+" already taken please try choose different user id thanks");
		}
	
	//customer not found exception msg
	public static CustomerException customerNotFound(long customerId) {
		return new CustomerException("Customer with Id : "+customerId+" not found");
	}

	public static CustomerException userIdNotFound(String userId) {
		return new CustomerException("User with id : "+userId+" not found");
	}
	//invalid credential exception msg
	public static CustomerException invalidLonginCredentials(String userName, String password) {
		return new CustomerException("Invalid Credetials please check User Name: "+userName+"  Password: "+password);
	}
	
	
}
