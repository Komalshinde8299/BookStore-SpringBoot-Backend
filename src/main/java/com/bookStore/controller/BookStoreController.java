package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookStore.Response;
import com.bookStore.dto.BookDto;
import com.bookStore.model.Book;
import com.bookStore.service.BookStoreService;

import lombok.Delegate;
@CrossOrigin("*")
@RestController
@RequestMapping("/bookStore")
public class BookStoreController {

	@Autowired
	BookStoreService service;
	@Autowired
	Response response;

	@PostMapping("/add")
	public ResponseEntity<Response> addBooks(@RequestBody BookDto bookdto) {
		return service.addBook(bookdto);

	}

	@PutMapping("/update")
	public ResponseEntity<Response> updateBooks(@RequestBody Book book, @RequestParam int bookId) {
		return service.updateBook(book, bookId);
	}

	@DeleteMapping("/delete")
	public String deleteBooks(@RequestParam int id) {
		service.deleteBook(id);
		return "Book Deleted";

	}
	
	@GetMapping("/all")
	public ResponseEntity<Response> getAllBooks(){
		return service.getAllBooks();
	}
	
	
}
