package com.bzf.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bzf.authservice.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	Optional<Users> findByUserName(String userName);

}
