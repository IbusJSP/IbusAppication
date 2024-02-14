package com.ibus.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ibus.dto.CustomerDto;
import com.ibus.exception.CustomerException;
import com.ibus.module.Customer;
import com.ibus.serviceimpl.CustomerServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/ibus", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CustomerController {
    
	@Autowired
	private  CustomerServiceImpl customerService;

	//create customer
	@PostMapping(path="/createcustomer")
	public ResponseEntity<Object> createCustomer(@RequestBody @Valid CustomerDto customerDto) {
	    try {
	        Customer createdCustomer = customerService.createCustomer(customerDto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
	    } catch (CustomerException ce) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ce.getMessage());
	    }
	}

	@PostMapping("/resetpassword")
	public ResponseEntity<Object> resetPassword(@RequestBody Map<String, String> credentials) {
		  
		// assuming you'll use userId for both mailId and mobileNumber
	    String userId = credentials.get("userId"); 
	    String newPassword = credentials.get("password");

	    try {
	        customerService.resetPassword(userId, newPassword);
	        return ResponseEntity.status(HttpStatus.OK).body("Password Successfully Changed");
	    } catch (CustomerException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	    }
	}

 
	//upload customer image
	@PostMapping("/uploadImage")
    public ResponseEntity<Object> uploadImage(
            @RequestParam("userId") String userId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            customerService.uploadImage(userId, file);
            return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully");
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
	}
	
	
	//generate otp from afridanwar@gmail.com  send to UserId mailid
	@PostMapping("/generateOtp")
    public ResponseEntity<Object> generateAndSendOtp(@RequestBody Map<String, String> credentials) 
	{
		
        try {
        	String userId=credentials.get("userId");
        	String password= credentials.get("password");
        	
            // Check if the userId and password are correct before generating OTP
            if (customerService.authenticateUser(userId, password)) {
                // Generate OTP
                String generatedOtp = customerService.generateRandomOtp();

                // Send OTP via email
                customerService.sendOtpByEmail(userId, generatedOtp);

                return ResponseEntity.status(HttpStatus.OK).body("OTP generated and sent successfully");
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
	
	
	   //log in for customer
		@PostMapping("/login")
		public ResponseEntity<Object> loginCustomer(@RequestBody Map<String, String> credentials) {
		    String userId = credentials.get("userId");
		    String password = credentials.get("password");
		    String enteredOtp = credentials.get("otp");

		    try {
		        customerService.verifyOtpAndLogin(userId, enteredOtp, password);
		        return ResponseEntity.status(HttpStatus.OK).body("Login successful");
		    } catch (CustomerException e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		    }
		}
}
