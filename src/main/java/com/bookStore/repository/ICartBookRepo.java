package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookStore.model.Book;
import com.bookStore.model.Cart;
@Repository
public interface ICartBookRepo extends JpaRepository<Cart, Integer>{
	
	

}
