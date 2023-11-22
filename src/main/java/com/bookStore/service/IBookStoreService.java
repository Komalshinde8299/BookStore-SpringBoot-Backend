package com.bookStore.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bookStore.Response;
import com.bookStore.dto.BookDto;
import com.bookStore.model.Book;

public interface IBookStoreService {
	public ResponseEntity<Response> addBook(BookDto bookdto);
	public ResponseEntity<Response> updateBook(Book book, int bookId);
	public void deleteBook(int id);
	public ResponseEntity<Response> getAllBooks();

}
