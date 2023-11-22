package com.bookStore.exception;

import lombok.Data;

@Data
public class BookStoreException extends RuntimeException{
	
	private String msg;

	public BookStoreException(String massage) {
		super(massage);
	}
	
	
	

}
