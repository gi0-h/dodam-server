package com.example.dodam.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dodam.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
