package com.buildit.tdd.workshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.buildit.tdd.workshop.entity.CartEntity;

@Repository
@EnableJpaRepositories
public interface CartRepository extends JpaRepository<CartEntity, Long> {
	/**
	 * Method to fetch cart associated with customer
	 * 
	 * @param customerId
	 * @return CartEntity
	 */
	//@Query("select u from cart u where u.customerId = ?1")
	Optional<CartEntity> findByCustomerId(long customerId);
}
