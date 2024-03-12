package com.ibus.module;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

public class BusOwner {
        
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_Id", nullable = false, updatable = false)
	private long adminId;
	
	@Column(name = "Email_Id", nullable = false, updatable = false, length = 50)
	private String email;
	
	@Column(name = "Admin_name", length = 40)
	private String name;
	
	@Column(name = "Mobile_Number", nullable = false, length = 13)
    private String mobile;
	
	@CreatedDate
	@Column(name = "Date_Created", nullable = false, updatable = false)
	private OffsetDateTime dateCreated;
	
	@LastModifiedDate
	@Column(name = "Last_Update", nullable = false)
	private OffsetDateTime dateUpated;
	
	@Column(name = "Last_Login")
	@Temporal(TemporalType.TIMESTAMP)
	private  OffsetDateTime lastLoginDate;
	
	@Column(name = "Is_Active")
	private char isActive;
	
	@Column(name="DATE_OF_BIRTH", nullable=false)
	@JsonFormat(pattern="yyyyMMdd")
	private LocalDate dateOfBirth;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Address_ID", nullable = false )
	private Address address;
	
//	@OneToMany
//	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
//	private Customer customer;
}
