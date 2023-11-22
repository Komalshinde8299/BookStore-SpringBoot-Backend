package com.bookStore.dto;

import lombok.Data;

@Data
public class BookDto {
	private String bookName;
	private String bookAuthor;
	private int bookPrice;
	private boolean avilable;
	private int bookQuantity;
	private String imageUrl;

}
