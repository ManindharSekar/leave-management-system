package com.bzf.authservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bzf.authservice.entity.Users;
import com.bzf.authservice.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService {
	
	@Autowired
	private UsersRepository userRepository;
	
	public void UsersService(UsersRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Users> user = userRepository.findByUserName(username);
		if(user.isPresent()) {
		Users usersObj = user.get();
		return User.builder()
				.username(usersObj.getUserName())
				.password(usersObj.getPassword())
				.roles(getRoles(usersObj))
				.build();
			
		}else {
			throw new UsernameNotFoundException(username);
		}
		
	}
	

	private String[] getRoles(Users user) {
		// TODO Auto-generated method stub
		if(user.getRole()==null) {
			return new String[]{"USER"};
		}
		return user.getRole().split(",");
	}


}
