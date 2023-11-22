package com.bookStore.model;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orderBook")
public class OrderBook {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orderId;
	private int totalPrice;
	@ManyToMany(cascade = CascadeType.ALL)
	List<CartBook> orderList;
	
	

}
