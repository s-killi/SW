package de.othr.securityproject.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.othr.securityproject.config.MyUserDetails;
import de.othr.securityproject.model.User;
import de.othr.securityproject.repository.UserRepositoryI;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService{

	UserRepositoryI userRepository;
	
	public MyUserDetailsServiceImpl (UserRepositoryI userRepository) {
		this.userRepository= userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> oUser= userRepository.findByLoginIgnoreCase(username);
		oUser.orElseThrow(()-> new UsernameNotFoundException("Not found "+username));
		System.out.println("User found at the UserDetailsService="+ oUser.get().getLogin());
		return new MyUserDetails(oUser.get());
	}
}
