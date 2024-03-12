package com.ibus.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibus.dto.AdminDto;
import com.ibus.exception.AdminException;
import com.ibus.serviceimpl.AdminServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/ibus", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AdminController {
    
	@Autowired
	private AdminServiceImpl adminService;
	
	//Create Admin
	@PostMapping(path = "/createAdmin")
	public void createAdmin(@RequestBody @Valid AdminDto adminDto, HttpServletResponse response) throws IOException{
		   
		try {
              adminService.createAdmin(adminDto);
              response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (AdminException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Failed to create admin: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions if needed
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal Server Error: " + e.getMessage());
        }
	}  
}
