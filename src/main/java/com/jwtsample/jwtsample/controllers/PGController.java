package com.jwtsample.jwtsample.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/pg/")
public class PGController {
	@Value("${pghost.url}")
	private String pgHostUrl;
	
	
	@PostMapping("/request")
	public String hitPGAPI(@RequestBody String request) {
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.postForObject(pgHostUrl, request, String.class);
		return result;
		
	}
}
