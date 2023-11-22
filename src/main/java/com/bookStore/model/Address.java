package com.bookStore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Adress")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int adressId;
	private int houseNo;
	private String city;
	private String state;
	private int pinCode;
	

}
