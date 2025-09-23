package com.bzf.authservice.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

	@Autowired
	private EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Users> user = userRepository.findByUserName(username);
		if (user.isPresent()) {
			Users usersObj = user.get();
			return User.builder().username(usersObj.getUserName()).password(usersObj.getPassword())
					.roles(getRoles(usersObj)).build();

		} else {
			throw new UsernameNotFoundException(username);
		}

	}

	private String[] getRoles(Users user) {
		// TODO Auto-generated method stub
		if (user.getRole() == null) {
			return new String[] { "USER" };
		}
		return user.getRole().split(",");
	}

	public Users save(Users user) {
		// TODO Auto-generated method stub
		Optional<Users> userNameExists = userRepository.findByUserName(user.getUserName());
		if (!userNameExists.isEmpty()) {
			throw new IllegalArgumentException("User Name is already exists");
		}

		Optional<Users> byEmail = userRepository.findByUserName(user.getEmail());
		if (!byEmail.isEmpty()) {
			throw new IllegalArgumentException("e-mail is already exists");
		} else {

			user.setConfirmationToken(UUID.randomUUID().toString());
			user.setTokenExpireTime(LocalDateTime.now().plusMinutes(3));

			emailService.sendEmail(user.getUserName(), user.getEmail(), user.getConfirmationToken());
		}

		user.setLastSeen(LocalDateTime.now());

		return userRepository.save(user);
	}

	public Users findByConfirmationToken(String token) {
		// TODO Auto-generated method stub
		return userRepository.findByConfirmationToken(token);
	}

	public boolean activateUser(String token, Users user) {
		// TODO Auto-generated method stub
		Users confirmationToken = userRepository.findByConfirmationToken(token);

		if (confirmationToken == null) {
			return false;
		}

		if (confirmationToken.getTokenExpireTime().isBefore(LocalDateTime.now())) {
			return false;
		}

		confirmationToken.setStatus(true);
		userRepository.save(confirmationToken);

		return true;

	}

	public String delete(String token) {
		// TODO Auto-generated method stub
		Users confirmationToken = userRepository.findByConfirmationToken(token);
		userRepository.delete(confirmationToken);
		return "User deleted";
	}

}
