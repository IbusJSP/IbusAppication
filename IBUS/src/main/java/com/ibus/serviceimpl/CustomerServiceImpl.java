package com.ibus.serviceimpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibus.dto.CustomerDto;
import com.ibus.emailservice.EmailService;
import com.ibus.exception.CustomerException;
import com.ibus.module.Address;
import com.ibus.module.Customer;
import com.ibus.repository.CustomerRepository;
import com.ibus.service.CustomerService;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
public class CustomerServiceImpl  implements CustomerService{
	
	private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private EmailService emailService;
	 
	private final CustomerRepository customerRepo;
	
	//constructor injection is the best practise and we can make data as immutable
    public CustomerServiceImpl(final CustomerRepository customerRepository) {
        this.customerRepo = customerRepository;
    }
	
	//mapping  Customer to CustomerDto  and returning
	public Customer mapCustomerDtoToCustomer(final Customer customer, final CustomerDto customerDto) {
		customer.setMailId(customerDto.getMailId());
		customer.setPassword(customerDto.getPassword());
		customer.setCustomerName(customerDto.getCustomerName());
		customer.setAge(customerDto.getAge());
		customer.setGender(customerDto.getGender());
		customer.setMobileNumber(customerDto.getMobileNumber());
		customer.setDateOfBirth(customerDto.getDateOfBirth());
		customer.setAdhaar(customerDto.getAdhaar());
		customer.setDateCreated(OffsetDateTime.now());
		customer.setLastUpdated(OffsetDateTime.now());
		
		Address address = new Address();
        address.setHouseNumber(customerDto.getAddress().getHouseNumber());
        address.setStreet(customerDto.getAddress().getStreet());
        address.setCity(customerDto.getAddress().getCity());
        address.setPinCode(customerDto.getAddress().getPinCode());
        address.setState(customerDto.getAddress().getState());
        address.setCountry(customerDto.getAddress().getCountry());

        customer.setAddress(address);
		return customer;
	}

	//service method to create the Create customer 
	
	@Override
	public Customer createCustomer(final CustomerDto customerDto) throws CustomerException {
	    if (customerRepo.existsByUserIdIgnoreCase(customerDto.getMailId())) {
	        throw CustomerException.duplicateUserId(customerDto.getMailId());
	    }

	    if (customerRepo.existsByUserIdIgnoreCase(customerDto.getMobileNumber())) {
	        throw CustomerException.duplicateUserId(customerDto.getMobileNumber());
	    }

	    Customer customer = new Customer();
	    try {
	        mapCustomerDtoToCustomer(customer, customerDto);
	        return customerRepo.save(customer);
	    } catch (Exception e) {
	        //logger.error("Failed to save customer", e.getMessage());
	        throw new CustomerException("Failed to save customer");
	    }
	}


	

	@Override
	public void resetPassword(String userId, String newPassword) throws CustomerException {
	    // Assuming userId can be either mailId or mobileNumber
	    Customer customer = customerRepo.findByUserIdIgnoreCase(userId)
	            .orElseThrow(() -> CustomerException.userIdNotFound(userId));

	    try {
	        customer.setPassword(newPassword);
	        customer.setLastUpdated(OffsetDateTime.now());
	        customerRepo.save(customer);
	    } catch (Exception e) {
	        throw new CustomerException("Failed to reset password: " + e.getMessage());
	    }
	}
	
	
	// still needed to do changes lot
	@Override
	public void uploadImage(String userId, MultipartFile file) throws CustomerException {
	    Customer customer = customerRepo.findByUserIdIgnoreCase(userId)
	            .orElseThrow(() -> new CustomerException("Customer not found with userId: " + userId));

	    try {
	    	
	        // Upload the image to S3
	        String key = uploadImageToS3(file);

	        // Set the image details in the Customer entity
	        customer.setFilePath(key);
	        customer.setFileSize(String.valueOf(file.getSize()));

	        customerRepo.save(customer);
	        
	        
	    } catch (IOException e) {
	        throw new CustomerException("Failed to process and save the image: " + e.getMessage());
	    }
	}

	//upload file to s3 cloud 
	private String uploadImageToS3(MultipartFile file) throws IOException {
		
	    // Set up your AWS S3 client and other configurations
		S3Client s3 = S3Client.builder()
		        .region(Region.US_EAST_1)  // Replace with your desired AWS region
		        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
		        .build();


	    // Your existing S3 upload logic here, replacing the dummy values
		
	    String key = "images/" + file.getOriginalFilename();  // Set the S3 key (path) for the image

	    PutObjectResponse response = s3.putObject(PutObjectRequest.builder()
	            .bucket("replace s3 cloud name")  // Replace with your actual S3 bucket name
	            .key(key)
	            .contentType(file.getContentType())
	            .build(), RequestBody.fromBytes(file.getBytes()));
	    
	    // Return the key (file path) for storing in the database
	    return key; 
	}
	

	
	//checking user id is present in DB
	@Override
    public boolean authenticateUser(String userId, String password) {
		
		Optional<Customer> optionalCustomer = customerRepo.findByUserIdIgnoreCase(userId);

        // Check if the user exists and the provided password matches then stored password
        return optionalCustomer.isPresent() && optionalCustomer.get().getPassword().equals(password);
        
    }

	//  Logic to Generate a random OTP (6-digit number)
    @Override
    public String generateRandomOtp() {
    	
     
        Random random = new Random();
        int otp = 100000 + random.nextInt(899999);
        return String.valueOf(otp);
    }

    //logic to send otp the user mail id
    @Override
    public void sendOtpByEmail(String userId, String otp) {
        Customer customer = customerRepo.findByUserIdIgnoreCase(userId)
                .orElseThrow(() -> new CustomerException("Customer not found with userId: " + userId));

        // Save the provided OTP and timestamp to the database
        customer.setOtp(otp);
        customer.setOtpGeneratedTime(LocalDateTime.now());
        customerRepo.save(customer);

        // Send OTP via email
        String subject = " Login OTP ";
        String body = "Your one time password (OTP) for log in is : " + otp;
        emailService.sendOtpEmail(customer.getMailId(), subject, body);
    }

 
    
    //log for the customer
    @Override
    public void verifyOtpAndLogin(String userId, String enteredOtp, String password) throws CustomerException {
        Customer customer = customerRepo.findByUserIdIgnoreCase(userId)
                .orElseThrow(() -> CustomerException.invalidLonginCredentials(userId, password));

        // Check if the entered OTP matches the stored OTP
        if (!customer.getOtp().equals(enteredOtp)) {
            throw new CustomerException("Invalid OTP please enter the correct otp! Thanks");
        }

        // Check if the provided password matches the stored password
        if (!customer.getPassword().equals(password)) {
            throw CustomerException.invalidLonginCredentials(userId, password);
        }

        // Reset the OTP after successful login
        customer.setOtp(null);
        customer.setOtpGeneratedTime(null);
        customerRepo.save(customer);
    }

	 
	 
	
}
