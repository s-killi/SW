package de.othr.jwtclientdemo.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.othr.jwtclientdemo.model.dto.AuthenticationRequest;
import de.othr.jwtclientdemo.model.dto.Authority;
import de.othr.jwtclientdemo.model.dto.RegisterRequest;



@RestController
public class JwtClientController {

	@org.springframework.beans.factory.annotation.Autowired 
	RestTemplate restTemplate; 

	private static final String REGISTRATION_URL = "http://localhost:8080/api/register";
	private static final String AUTHENTICATION_URL = "http://localhost:8080/api/authenticate";
	private static final String DEMO_PROTECTED_URL = "http://localhost:8080/api/demo";

	
	
	
	@RequestMapping(value = "/getresponseregister", method = RequestMethod.GET)
	public String getResponseRegister() throws JsonProcessingException {

		String response = null;
		// create RegisterRequest registration object
		RegisterRequest RegisterRequest = getRegisterRequest();
		// convert the RegisterRequest registration object to JSON
		String registrationBody = getBody(RegisterRequest);
		// create headers specifying that it is JSON request
		org.springframework.http.HttpHeaders registrationHeaders = getHeaders();
		HttpEntity<String> registrationEntity = new HttpEntity<String>(registrationBody, registrationHeaders);

		try {
			// Register RegisterRequest
			ResponseEntity<String> registrationResponse = restTemplate.exchange(REGISTRATION_URL, HttpMethod.POST,
					registrationEntity, String.class);
			
			response = "entity saved!";
			   // if the registration is successful		
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return response;
	}
	
	
	
	@RequestMapping(value = "/getresponseauthentication", method = RequestMethod.GET)
	public String getResponseAuthenticate() throws JsonProcessingException {

		System.out.println("Starting the authentication...");
		
		String response = null;
		// create  authentication object
				AuthenticationRequest authenticationRequest = getAuthenticationRequest();
				// convert the Request authentication object to JSON
				String authenticationBody = getBody(authenticationRequest);
				// create headers specifying that it is JSON request
				HttpHeaders authenticationHeaders = getHeaders();
			
				HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody,
						authenticationHeaders);
			try {
				// Authenticate AuhtenticationRequest and get the JWT
				
					ResponseEntity<String> responseEntity = restTemplate.exchange(AUTHENTICATION_URL,
						HttpMethod.POST, authenticationEntity, String.class);
					
				System.out.println("Status code =" + responseEntity.getStatusCode());
				System.out.println(responseEntity.getBody());
				System.out.println("authentication successful");
				
				
				
				// if the authentication is successful, we will access the /api/demo resource with the token		
				if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
					
					//"Bearer" is standard for JWT 
					String token = "Bearer " + responseEntity.getBody().substring(8,responseEntity.getBody().length()-2 );
					System.out.println("now accessing the endpoint....");
					org.springframework.http.HttpHeaders headers = getHeaders();
					System.out.println("token sent:"+ token);
					headers.set("Authorization", token);
					HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
					// Use Token to get Response
					ResponseEntity<String> demoResponse = restTemplate.exchange(DEMO_PROTECTED_URL, HttpMethod.GET, jwtEntity,
							String.class);
					if (demoResponse.getStatusCode().equals(HttpStatus.OK)) {
						response = demoResponse.getBody();
						System.out.println(demoResponse.getBody() + "response from the protected method");
					}
				}
			
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return response;
	}


	private RegisterRequest getRegisterRequest() {
		RegisterRequest RegisterRequest = new RegisterRequest();
		RegisterRequest.setUsername("daniel");
		RegisterRequest.setPassword("123456");
		RegisterRequest.getMyauthorities().add(new Authority((Integer)1));
		return RegisterRequest;
	}

	private AuthenticationRequest getAuthenticationRequest() {
		AuthenticationRequest authenRequest = new AuthenticationRequest();
		authenRequest.setUsername("thomas");
		authenRequest.setPassword("123456");
		return authenRequest;
	}

	private org.springframework.http.HttpHeaders getHeaders() {
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

	private String getBody(final AuthenticationRequest authenticationRequest) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(authenticationRequest);
	}
	
	private String getBody(final RegisterRequest registerRequest) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(registerRequest);
	}
	
}
