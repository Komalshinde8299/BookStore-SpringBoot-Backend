package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookStore.model.OrderBook;
@Repository
public interface OrderRepo extends JpaRepository<OrderBook, Integer> {

}
