package com.ibus.module;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="ADDRESS")
public class Address {
	
	    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ADDRESS_ID", nullable = false, updatable = false)
        private long addressId;

	    @Column(name = "HOUSE_NUMBER",nullable = false)
	    private String houseNumber;

	    @Column(name = "STREET",nullable = false)
	    private String street;

	    @Column(name = "CITY",nullable = false)
	    private String city;

	    @Column(name = "PIN_CODE",nullable = false)
	    private int pinCode;

	    @Column(name = "STATE",nullable = false)
	    private String state;

	    @Column(name = "COUNTRY",nullable = false)
	    private String country;

}
