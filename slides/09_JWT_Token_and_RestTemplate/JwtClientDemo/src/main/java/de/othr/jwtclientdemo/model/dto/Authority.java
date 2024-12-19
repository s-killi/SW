package de.othr.jwtclientdemo.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



public class Authority implements Serializable{

	
	private Long id;
	
	private String description;


	public void Authority () {}
	
	
	public  Authority (Integer id) {
		this.id=(long) id;
		this.description="";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
