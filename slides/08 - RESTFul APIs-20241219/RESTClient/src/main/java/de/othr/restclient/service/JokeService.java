package de.othr.restclient.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import de.othr.restclient.model.Joke;
import reactor.core.publisher.Mono;

@Service
public class JokeService {
    
	private final WebClient webClient;
	
	public JokeService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://official-joke-api.appspot.com/").build();
	}

	public Mono<Joke> someRestCallforJoke() {
		
		return this.webClient.get().uri("random_joke")
						.retrieve().bodyToMono(Joke.class);
	}
	



}
