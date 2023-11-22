package com.bookStore.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookStore.Response;
import com.bookStore.dto.BookDto;
import com.bookStore.exception.BookStoreException;
import com.bookStore.model.Book;
import com.bookStore.repository.BookStoreRepo;


@Service
public class BookStoreService implements IBookStoreService {
	@Autowired
	BookStoreRepo repo;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	Response response;

	@Override
	public ResponseEntity<Response> addBook(BookDto bookdto) {
		if(repo.findByBookName(bookdto.getBookName()) != null){
			throw new BookStoreException("Book is already presesnt");
			
		}
		Book book = modelMapper.map(bookdto, Book.class);
		repo.save(book);
		
		response.setMsg("new book added");
		response.setObj(bookdto);
		return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
		
		
	}

	@Override
	public ResponseEntity<Response> updateBook(Book book, int bookId) {
		if(repo.findById(bookId) == null ) {
			throw new BookStoreException("Book with this id not found");
			
		}else {
			book.setBookId(bookId);
			repo.save(book);
			response.setMsg("Book details updated");
			response.setObj(book);
		}
		return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
	}

	@Override
	public void deleteBook(int id) {
		repo.deleteById(id);
		
		
		
	}

	@Override
	public ResponseEntity<Response> getAllBooks() {
		List<Book> list = repo.findAll();
		response.setMsg("all books from list");
		response.setObj(list);
		
		return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
	}
	

}
