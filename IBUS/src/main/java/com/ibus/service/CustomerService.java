package com.ibus.service;

import org.springframework.web.multipart.MultipartFile;

import com.ibus.dto.CustomerDto;
import com.ibus.exception.CustomerException;
import com.ibus.module.Customer;
public interface CustomerService {
	
	
	//abstract methods to be implemented in service implemetation class
	
	public Customer createCustomer(CustomerDto customer);
	
	//public void loginCustomer(String userId, String password);
	
	public void resetPassword(String userId, String password);

	void uploadImage(String userId, MultipartFile file) throws CustomerException;
	
	//to check valid user or not
	boolean authenticateUser(String userId, String password);
	
	//generate random 6 digit otp
	public String generateRandomOtp();
    
    // method to send otp to user id
    void sendOtpByEmail(String userId, String otp);

	void verifyOtpAndLogin(String userId, String enteredOtp, String password) throws CustomerException;
}
