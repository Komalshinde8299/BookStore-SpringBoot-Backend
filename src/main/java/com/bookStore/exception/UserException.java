package com.bookStore.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserException extends RuntimeException{
	String msg;
	int statusCode;

	
	
	

}
