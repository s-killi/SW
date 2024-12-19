package de.othr.restclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.othr.restclient.model.Joke;
import de.othr.restclient.service.JokeService;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JokeController {

	
	@Autowired
	JokeService jokeService;
	
	
	@GetMapping("/joke")
	public Joke getJoke() {
		return jokeService.someRestCallforJoke().block();
	}
	
	
	
	
}
