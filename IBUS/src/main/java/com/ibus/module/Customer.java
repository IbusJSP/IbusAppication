package com.ibus.module;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity

//used to create the default date created and dateupdated
@EntityListeners(AuditingEntityListener.class)

@Setter
@Getter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name="CUSTOMER")
public class Customer { 
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CUSTOMER_ID", nullable=false, updatable=false)
	private long customerId;
	
	@Column(name="MAIL_ID", nullable=false, updatable=false ,length=20)
	private String mailId;
	
	@Column(name="PASSWORD", nullable=false, length=20)
	private String password;
	
	@Column(name="CUSTOMER_NAME",  length=50)
	private String customerName;
	
	@Column(name="AGE", nullable=false, length=3)
	private int age;
	
	
	@Column(name="GENDER", nullable=false, length=6)
	private String gender;
	
	@Column(name="MOBILE_NUMBER", nullable=false, length=13)
	private String mobileNumber;
	
	@Column(name="DATE_OF_BIRTH", nullable=false)
	@JsonFormat(pattern="yyyyMMdd")
	private LocalDate dateOfBirth;
	
	@Column(name="ADHAAR", length=15)
	private  String adhaar;
	
	
	@CreatedDate 
	@Column(name="DATE_CREATED", nullable=false, updatable=false)
	private OffsetDateTime dateCreated;
	
	@LastModifiedDate
	@Column(name="LAST_UPDATED", nullable=false)
	private OffsetDateTime lastUpdated;
	
	@Column(name="FILE_PATH")
	private String filePath;
	
	@Column(name="FILE_SIZE")
	private String fileSize;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID",nullable=false)
    private Address address;
}
