package com.buildit.tdd.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buildit.tdd.workshop.entity.AddressEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
	
	List<AddressEntity> findAddressesByCustomerId(Long customerId);

}
