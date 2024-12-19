package de.othr.securityproject.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import de.othr.securityproject.model.Authority;

public class RegisterRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String email;
	
	private ArrayList<Authority> myauthorities = new ArrayList<>() ;

	@JsonCreator
	public RegisterRequest() {}
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<Authority> getMyauthorities() {
		return myauthorities;
	}

	public void setMyauthorities(ArrayList<Authority> myauthorities) {
		this.myauthorities = myauthorities;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
