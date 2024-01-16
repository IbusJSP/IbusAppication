	package com.ibus.repository;
	
	import java.util.Optional;
	
	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.data.jpa.repository.Query;
	import org.springframework.data.repository.query.Param;
	import org.springframework.stereotype.Repository;
	
	import com.ibus.module.Customer;
	
	@Repository
	public interface CustomerRepository extends JpaRepository<Customer, Long>{
		
		@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.mailId = :userId OR c.mobileNumber = :userId")
		boolean existsByUserIdIgnoreCase(@Param("userId") String userId);


	    @Query(nativeQuery = true, value = "select * from CUSTOMER where MAIL_ID = :userId or MOBILE_NUMBER = :userId")
	    Optional<Customer> findByUserIdIgnoreCase(String userId);
	}
