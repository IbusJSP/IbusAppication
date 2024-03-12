package com.ibus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibus.module.Admin;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
           
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Admin c WHERE c.email = :userId OR c.mobile = :userId")
	boolean existsByUserIdIgnoreCase(@Param("userId") String userId);


    @Query(nativeQuery = true, value = "select * from Admin where MAIL_ID = :userId or MOBILE_NUMBER = :userId")
    Optional<Admin> findByUserIdIgnoreCase(String userId);
}
