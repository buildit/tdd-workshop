package com.buildit.tdd.workshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.buildit.tdd.workshop.entity.CartEntity;
import com.buildit.tdd.workshop.entity.CartItemEntity;

@Repository
@EnableJpaRepositories
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
	/**
	 * Query to fetch cart associated with customer
	 * 
	 * @param cartId
	 * @return
	 */
	//@Query("select c from cart c where c.cartId = ?1")
	Optional<CartEntity> findByCartId(long cartId);
}
