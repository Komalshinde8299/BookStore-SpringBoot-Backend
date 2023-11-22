package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookStore.Response;
import com.bookStore.model.Cart;
import com.bookStore.model.User;
import com.bookStore.service.UserService;
@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	
	
	
	@PostMapping("/getUser")
	public ResponseEntity<Response> getUserDetails(@RequestBody User user){
		return userService.addUser(user);
		
	}
	@GetMapping("/getToken")
	public String getToken(@RequestParam String email, @RequestParam String password) {
		return userService.login(email, password);
		
	}
//	
//	@GetMapping("/decode")
//	public ResponseEntity<Response>  decodeToken(@RequestHeader String token){
//		return userService.decodeToken(token);
//		
//	}
	
	@PutMapping("/addToCart")
	public ResponseEntity<Response> addBook(@RequestParam String bookName, @RequestHeader String token){
		return userService.addBook(bookName, token);
	}

	@GetMapping("/addValue")
	public String increaseValue(@RequestHeader String token, @RequestParam String bookName) {
		return userService.increseCartValue(token, bookName);
	}
	
	@GetMapping("/reduceValue")
	public String decreaseCartValue(@RequestHeader String token, @RequestParam String bookName) {
		return userService.decreaseCartValue(token, bookName);
	}
	
	@GetMapping("/remove")
	public ResponseEntity<Response> removeFromCart(@RequestHeader String token, @RequestParam String bookName){
		return userService.removeFromCart(token, bookName);
	}
	
	@GetMapping("/buy")
	public ResponseEntity<Response> buyBook(@RequestHeader String token){
		return userService.buyBook(token);
	}
	
	@GetMapping("/showCart")
	public Cart ShowCart(@RequestHeader String token) {
		return userService.showCart(token);
	}
	
	@GetMapping("/history")
	public ResponseEntity<Response>showHistory(@RequestHeader String token){
		return userService.showOrderHistory(token);
		
	}
	

}
