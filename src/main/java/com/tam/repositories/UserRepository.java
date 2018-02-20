package com.tam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tam.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
		User findByuserNameAndUserPassword(String userName,String userPassword);
}
