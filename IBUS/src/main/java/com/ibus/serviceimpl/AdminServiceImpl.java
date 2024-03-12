package com.ibus.serviceimpl;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.ibus.dto.AdminDto;
import com.ibus.exception.AdminException;
import com.ibus.exception.CustomerException;
import com.ibus.module.Address;
import com.ibus.module.Admin;
import com.ibus.repository.AdminRepository;
import com.ibus.service.AdminService;


@Service
public class AdminServiceImpl implements AdminService {

	
	private final AdminRepository adminRepo;
	
	public AdminServiceImpl(AdminRepository adminRepo){
		this.adminRepo = adminRepo;
	}
	
	@Override
	public void createAdmin(AdminDto adminDto) {
		
	     if(adminRepo.existsByUserIdIgnoreCase(adminDto.getEmail())){
	    	 throw AdminException.duplicateUserId(adminDto.getEmail());
	    	 
	     }
	     
	     if(adminRepo.existsByUserIdIgnoreCase(adminDto.getMobile())){
	    	 throw AdminException.duplicateUserId(adminDto.getMobile());
	     }
	     
	     Admin admin = new Admin();
	     
	     try{
	    	 
	    	 mapAdminDtoToAdmin(adminDto, admin);
	    	 adminRepo.save(admin);
	     }catch(Exception e){
	    	 throw new AdminException("Failed to Save Admin");
	     }
	
		//return null;
	}
	
	//Mapping AdminDto to Admin Entity
	public Admin mapAdminDtoToAdmin(final AdminDto adminDto, final Admin admin) {
		admin.setName(adminDto.getName());
		admin.setEmail(adminDto.getEmail());
		admin.setMobile(admin.getMobile());
		
		admin.setIsActive(adminDto.getIsActive());
		admin.setDateOfBirth(adminDto.getDateOfBirth());
		admin.setDateCreated(OffsetDateTime.now());
		admin.setDateUpated(OffsetDateTime.now());
		admin.setLastLoginDate(OffsetDateTime.now());
		
		
		
		
		Address address = new Address();
        address.setHouseNumber(adminDto.getAddress().getHouseNumber());
        address.setStreet(adminDto.getAddress().getStreet());
        address.setCity(adminDto.getAddress().getCity());
        address.setPinCode(adminDto.getAddress().getPinCode());
        address.setState(adminDto.getAddress().getState());
        address.setCountry(adminDto.getAddress().getCountry());

         admin.setAddress(address);
		
		return admin;
		
	}
	
	
	

}
