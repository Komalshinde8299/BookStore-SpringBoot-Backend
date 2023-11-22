package com.bookStore.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bookStore.Response;

@RestControllerAdvice
public class GlobalException {
	@Autowired
	Response response;
	
	@ExceptionHandler(value = BookStoreException.class)
	public Response getException( BookStoreException exception) {
		response.setMsg(exception.getMessage());
//		response.setObj(exception);
		return response;
		
	}
	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<String> findUserException(UserException userExcption){
		return new ResponseEntity<>(userExcption.getMsg(), HttpStatus.valueOf(userExcption.getStatusCode()));
		
	}
	

}
