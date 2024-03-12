package com.ibus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdminException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	//CustomerException Constructor 
		public AdminException(String message) {
			super(message);
		}
		
		//duplicate userId msg
			public static AdminException duplicateUserId(String userId) {
				return new AdminException("User id : "+userId+" already taken please try choose different user id thanks");
			}
		
		//customer not found exception msg
		public static AdminException adminNotFound(long adminId) {
			return new AdminException("Admin with Id : "+adminId+" not found");
		}

		public static AdminException userIdNotFound(String userId) {
			return new AdminException("User with id : "+userId+" not found");
		}
		//invalid credential exception msg
		public static AdminException invalidLonginCredentials(String userName, String password) {
			return new AdminException("Invalid Credetials please check User Name: "+userName+"  Password: "+password);
		}
	
	
	

}
