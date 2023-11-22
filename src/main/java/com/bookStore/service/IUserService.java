package com.bookStore.service;

import org.springframework.http.ResponseEntity;

import com.bookStore.Response;
import com.bookStore.dto.UserLoginDto;
import com.bookStore.model.User;

public interface IUserService {
	
	public ResponseEntity<Response>addUser(User user);
	public String login(String email, String password);
//	public ResponseEntity<Response> decodeToken(String token);
	public ResponseEntity<Response> addBook(String bookName, String token);
	public String increseCartValue(String token, String bookName);
	public String decreaseCartValue(String token, String bookName);
	public ResponseEntity<Response> removeFromCart(String token, String bookName);
	public ResponseEntity<Response> buyBook(String token);
	

}
