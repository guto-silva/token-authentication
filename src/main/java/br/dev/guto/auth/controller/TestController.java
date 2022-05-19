package br.dev.guto.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello Security World";
	}
	
	@GetMapping("/hellosecurity")
	public String helloSecurity() {
		return "Hello Security Authorized";
	}
	
}
