package me.seojinoh.learning.springSecurityJwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

	@GetMapping("/user/test")
	public String testUser() {
		return "success";
	}

	@GetMapping("/admin/test")
	public String testAdmin() {
		return "success";
	}

}
