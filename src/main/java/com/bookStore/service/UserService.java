package com.bookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookStore.Response;
import com.bookStore.dto.UserLoginDto;
import com.bookStore.exception.UserException;
import com.bookStore.model.Book;
import com.bookStore.model.Cart;
import com.bookStore.model.CartBook;
import com.bookStore.model.OrderBook;
import com.bookStore.model.User;
import com.bookStore.repository.BookStoreRepo;
import com.bookStore.repository.ICartBookRepo;
import com.bookStore.repository.UserRepo;
import com.bookStore.utility.JWTUtility;
import com.bookStore.utility.MyEmail;

@Service
public class UserService implements IUserService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	Response response;
	@Autowired
	JWTUtility utility;
	@Autowired
	BookStoreRepo bookRepo;
	@Autowired
	MyEmail mailSender;
	@Autowired
	ICartBookRepo cartBookRepo;

	public ResponseEntity<Response> addUser(User user) {
		if (userRepo.findByUserName(user.getUserName()) != null) {
			throw new UserException("User already present", 404);

		}
		userRepo.save(user);
		mailSender.sendMail(user.getEmail(), "Registration completed", user.getUserName()+"resgistration completed");
		response.setMsg("new user details added");
		response.setObj(user);

		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

	}

	@Override
	public String login(String email, String password) {
		if(userRepo.findByEmailAndPassword(email, password) == null) {
			throw new UserException("Invalid email or password", 404);
		}
		return utility.getTokenGenrated(email, password);
	}

//	@Override
//	public ResponseEntity<Response> decodeToken(String token) {
//		UserLoginDto uerlogindto = utility.decodeToken(token);
//		response.setMsg("Token resolved");
//		response.setObj(uerlogindto);
//		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
//	}

	@Override
	public ResponseEntity<Response> addBook(String bookName, String token) {
		UserLoginDto credentilas = utility.decodeToken(token);
		User user = userRepo.findByEmailAndPassword(credentilas.getEmail(), credentilas.getPassword());
		if ((user == null)) {
			throw new UserException("Invalid Token", 404);

		}
		Book book = bookRepo.findByBookName(bookName);

		if (book == null) {
			throw new UserException("Invalid Book name", 404);
		}
		if (bookRepo.findByBookName(bookName).getBookQuantity() == 0) {
			throw new UserException("book is out of stock", 404);

		}
		Cart cart = user.getCart();

		List<CartBook> booklist = cart.getCartBookList();

		for (CartBook bookcheck : booklist) {
			if (bookcheck.getBookName().equals(bookName)) {
				throw new UserException("book is already added in cart", 404);

			}

		}
		CartBook cartBook = new CartBook();
		cartBook.setBookName(bookName);
		cartBook.setBookPrice(book.getBookPrice());
		cartBook.setBookAuthor(book.getBookAuthor());
		cartBook.setImageUrl(book.getImageUrl());
		cartBook.setBookQuantity(1);

		booklist.add(cartBook);
		cart.setCartBookList(booklist);
		cart.setTotalPrice(book.getBookPrice());
		user.setCart(cart);
		userRepo.save(user);
		response.setObj(cartBook);
		response.setMsg("Book added successfully to cart");
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@Override
	public String increseCartValue(String token, String bookName) {
		UserLoginDto credentilas = utility.decodeToken(token);
		User user = userRepo.findByEmailAndPassword(credentilas.getEmail(), credentilas.getPassword());
		if ((user == null)) {
			throw new UserException("Invalid Token", 404);

		}
		Book book = bookRepo.findByBookName(bookName);

		if (book == null) {
			throw new UserException("Invalid Book name", 404);
		}
		if (book.getBookQuantity() == 0) {
			throw new UserException("Book is out of stock", 404);

		}
		List<CartBook> bookList = user.getCart().getCartBookList();
		Cart cart = user.getCart();

		int bookIndex = 0;
		int bookCount = 0;
		CartBook cartBook1 = new CartBook();
		for (int i = 0; i < bookList.size(); i++) {
			if (bookList.get(i).getBookName().equals(bookName)) {
				bookIndex = i;
				bookCount = 1;
				cartBook1 = bookList.get(i);
			}

		}
		if (bookCount == 0) {
			throw new UserException("Book is not added to cart add book to cart first", 404);

		}
		cartBook1.setBookQuantity(cartBook1.getBookQuantity() + 1);
		bookList.set(bookIndex, cartBook1);
		int cartPrice = cart.getTotalPrice();
		cartPrice += cartBook1.getBookPrice();
		cart.setCartBookList(bookList);
		cart.setTotalPrice(cartPrice);
		user.setCart(cart);
		userRepo.save(user);

		return "Cart value is increasd";
	}

	@Override
	public String decreaseCartValue(String token, String bookName) {
		UserLoginDto credentilas = utility.decodeToken(token);
		User user = userRepo.findByEmailAndPassword(credentilas.getEmail(), credentilas.getPassword());
		if ((user == null)) {
			throw new UserException("Invalid Token", 404);

		}
		Book book = bookRepo.findByBookName(bookName);

		if (book == null) {
			throw new UserException("Invalid Book name", 404);
		}
		Cart cart = user.getCart();
		List<CartBook>bookList = cart.getCartBookList();
		int bookIndex = 0;
		int bookCount = 0;
		CartBook cartBook1 = new CartBook();
		for(int i =0; i<bookList.size(); i++) {
			if(bookList.get(i).getBookName().equals(bookName)) {
				bookIndex = i;
				bookCount = 1;
				cartBook1 = bookList.get(i);
			}
		}
		if(bookCount == 0) {
			throw new UserException("Book is not in the cart", 404);
			
		}
		if(cartBook1.getBookQuantity() == 0) {
			throw new UserException("Book is not in the cart", 404);
			
			
		}
		cartBook1.setBookQuantity(cartBook1.getBookQuantity() - 1);
		int totalPrice = cart.getTotalPrice();
		totalPrice -= cartBook1.getBookPrice();
		if(cartBook1.getBookQuantity() == 0) {
		      bookList.remove(bookIndex);
			
		}
		else {
			bookList.set(bookIndex, cartBook1);
			
		}
		cart.setTotalPrice(totalPrice);
		cart.setCartBookList(bookList);
		user.setCart(cart);
		userRepo.save(user);
		
		
		return "Book quantity reduced by one";
	}

	@Override
	public ResponseEntity<Response> removeFromCart(String token, String bookName) {
		UserLoginDto credentilas = utility.decodeToken(token);
		User user = userRepo.findByEmailAndPassword(credentilas.getEmail(), credentilas.getPassword());
		if ((user == null)) {
			throw new UserException("Invalid Token", 404);

		}
		Book book = bookRepo.findByBookName(bookName);

		if (book == null) {
			throw new UserException("Invalid Book name", 404);
		}
		Cart cart = user.getCart();
		List<CartBook>bookList = cart.getCartBookList();
		int bookIndex = 0;
		int bookCount = 0;
		CartBook cartBook1 = new CartBook();
		for(int i =0; i<bookList.size(); i++) {
			if(bookList.get(i).getBookName().equals(bookName)) {
				bookIndex = i;
				bookCount = 1;
				cartBook1 = bookList.get(i);
			}
		}
		if(bookCount == 0) {
			throw new UserException("Book is not in the cart", 404);
			
		}
		
		int cartPrice = cart.getTotalPrice()-(cartBook1.getBookPrice()* cartBook1.getBookQuantity());
		cart.setTotalPrice(cartPrice);
		bookList.remove(bookIndex);
		cart.setCartBookList(bookList);
		
		user.setCart(cart);
		userRepo.save(user);
		response.setMsg("Book removed from cart");
		response.setObj(cartBook1);
		
		return new  ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
		
		
	}

	@Override
	public ResponseEntity<Response> buyBook(String token) {
		UserLoginDto credentilas = utility.decodeToken(token);
		User user = userRepo.findByEmailAndPassword(credentilas.getEmail(), credentilas.getPassword());
		if ((user == null)) {
			throw new UserException("Invalid Token", 404);

		}
		Cart cart = user.getCart();
		if(cart.getTotalPrice() == 0) {
			throw new UserException("Your cart is empty", 404);
			
		}
		List<CartBook> bookList = cart.getCartBookList();
		for(CartBook cartBook : bookList) {
			Book book = bookRepo.findByBookName(cartBook.getBookName());
			book.setBookQuantity(book.getBookQuantity()-cartBook.getBookQuantity());;
			bookRepo.save(book);
		}
		
		OrderBook order = new OrderBook();
		order.setOrderList(bookList);
		order.setTotalPrice(cart.getTotalPrice());
		List<OrderBook> orderList = user.getOrderBook();
		orderList.add(order);
		user.setOrderBook(orderList);
		userRepo.save(user);
		
//		for(CartBook cartBook : bookList) {
//			cartBookRepo.deleteById(cartBook.getBookId());
//		}
		cart.setTotalPrice(0);
		cart.setCartBookList(null);
		
		user.setCart(cart);
		userRepo.save(user);
		mailSender.sendMail(user.getEmail(), "Order Plcaed Successfully", "Your order is dispalced for "+user.getOrderBook());
		response.setObj(order);
		response.setMsg("Your order is placed successfully");
		return new  ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
	}
	
	public Cart showCart(String token) {
		UserLoginDto credentilas = utility.decodeToken(token);
		User user = userRepo.findByEmailAndPassword(credentilas.getEmail(), credentilas.getPassword());
		if ((user == null)) {
			throw new UserException("Invalid Token", 404);

		}
		return user.getCart();
	}
	
	
	public ResponseEntity<Response> showOrderHistory(String token){
		UserLoginDto credentilas = utility.decodeToken(token);
		User user = userRepo.findByEmailAndPassword(credentilas.getEmail(), credentilas.getPassword());
		if ((user == null)) {
			throw new UserException("Invalid Token", 404);

		}
		response.setObj(user.getOrderBook());
		response.setMsg("Your previous order");
		return  new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		
		
	}
	
	
	
	
	
	
	
	
	

}
