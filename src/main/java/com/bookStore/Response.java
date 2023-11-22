package com.bookStore;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Response {
	
	String msg;
	Object obj;

}
