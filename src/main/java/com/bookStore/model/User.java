package com.bookStore.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import java.util.List;
import com.bookStore.model.Cart;



@Data
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	private String userName;
	private String email;
	private String password;
	private String contactNo;
	@OneToMany(cascade = CascadeType.ALL)
	List<Address> adressList;
	@OneToMany(cascade = CascadeType.ALL)
	List<OrderBook> orderBook;
	@OneToOne(cascade = CascadeType.ALL)
	Cart cart;
	

}
