package com.jwtsample.jwtsample.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/pg/")
public class PGController {

	@PostMapping("/request")
	public String hitPGAPI(@RequestBody String request) {
		String uri = "http://localhost:8084/safaripay_app_main/gsm";
	    RestTemplate restTemplate = new RestTemplate();
	    //restTemplate.postForObject(url, request, responseType)
	    
	    String result = restTemplate.postForObject(uri, request, String.class);
		return result;
		
	}
}
