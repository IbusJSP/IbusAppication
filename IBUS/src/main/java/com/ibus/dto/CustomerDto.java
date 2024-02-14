package com.ibus.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ibus.module.Address;

import jakarta.persistence.Column;
import jakarta.validation.Valid;


@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerDto {
	
	
   //private long customerId;
   //@NotNull and @Size is optional its not a mandatory to give 
    @NotNull
    @Size(max=40)
	private String mailId;
	
    @NotNull
    @Size(max=20)
	private String password;
	
    @NotNull
    @Size(max=50)
	private String customerName;
	
    @NotNull
    @Size(max=3)
	private int age;
	
    @NotNull
    @Size(max=6)
	private String gender;
	
    @NotNull
    @Size(max=13)
	private String mobileNumber;
	
    @Column(name="DATE_OF_BIRTH", nullable=false)
    @JsonFormat(pattern="yyyyMMdd")
	private LocalDate dateOfBirth;
    
    @Size(max=15)
	private  String adhaar;
    
    @Valid
    @NotNull
    private Address address;
}
