package de.othr.securityproject.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import de.othr.securityproject.config.jwt.JwtService;
import de.othr.securityproject.model.Authority;
import de.othr.securityproject.model.User;
import de.othr.securityproject.model.dto.AuthenticationRequest;
import de.othr.securityproject.model.dto.AuthenticationResponse;
import de.othr.securityproject.model.dto.RegisterRequest;
import de.othr.securityproject.repository.AuthorityRepository;
import de.othr.securityproject.repository.UserRepository;
import de.othr.securityproject.service.MyUserDetailsService;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    MyUserDetailsService userDetailsService;
	
	@Autowired
	JwtService jwtServiceUtils;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthorityRepository authorityRepository;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest
			registerRequest){
		
		User user = new User();
		user.setActive(1);
		user.setEmail(registerRequest.getEmail());
		user.setLogin(registerRequest.getUsername());
		user.setMyauthorities(new ArrayList<Authority> ());
		
		for (int i = 0; i< registerRequest.getMyauthorities().size(); i++) {
			System.out.println("received auth "+i+ " "+ registerRequest.getMyauthorities().get(i).getId());
			Optional<Authority> authorityOp = authorityRepository.findById(registerRequest.getMyauthorities().get(i).getId());
			user.getMyauthorities().add(authorityOp.get());
			System.out.println(authorityOp.get().getDescription());
			
		}
		
		user.setPassword(encoder.encode(registerRequest.getPassword()));
		
		user = userRepository.save(user);
		
				
		
		System.out.println("saved the user per API..");		
				
		System.out.println(user.getLogin());
		
		 UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin()); 	
			
		 String jwt = jwtServiceUtils.generateToken(userDetails);
				 
		 return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}
	
	
	@RequestMapping(value ="/authenticate", method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken (@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		
		 try {	
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
			
		 }catch (BadCredentialsException e) {
			 throw new Exception ("Incorrect username or password!", e);
		 }
			
		 UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername()); 	
		
		 String jwt = jwtServiceUtils.generateToken(userDetails);
			
		 return ResponseEntity.ok(new AuthenticationResponse(jwt));
	 
	}
	
	
	
}