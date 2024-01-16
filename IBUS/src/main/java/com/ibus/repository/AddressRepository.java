package com.ibus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibus.module.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
