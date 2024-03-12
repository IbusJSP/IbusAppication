package com.ibus.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ibus.module.Address;
import com.ibus.module.Customer;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AdminDto {
     
	
	@NotNull
	@Size(max = 40)
	private String email;
	
	@NotNull
	@Size(max = 40)
	private String name;
	
	@NotNull
	@Size(max = 13)
	private String mobile;
	
	@Column(name="DATE_OF_BIRTH", nullable=false)
	@JsonFormat(pattern="yyyyMMdd")
	private LocalDate dateOfBirth;
	
	@NotNull
	private char isActive;
	
	@Valid
	@NotNull
	private Address address;
	
//	@Valid
//	@NotNull
//	private Customer customer;
}
