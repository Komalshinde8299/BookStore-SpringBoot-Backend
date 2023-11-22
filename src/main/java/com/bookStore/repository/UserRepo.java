package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookStore.model.User;
@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	public User findByUserName(String userName);
	public User findByEmailAndPassword(String email, String password);
	
	
	

}
