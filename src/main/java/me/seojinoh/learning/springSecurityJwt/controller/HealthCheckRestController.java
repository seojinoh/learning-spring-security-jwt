package me.seojinoh.learning.springSecurityJwt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.seojinoh.learning.springSecurityJwt.util.ResponseUtil;

@RestController
public class HealthCheckRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private ResponseUtil responseUtil;

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

	@GetMapping("/user/test")
	public void testUser(HttpServletRequest request, HttpServletResponse response) {
		responseUtil.setResponse(response, HttpServletResponse.SC_OK, "OK", null);
	}

	@GetMapping("/admin/test")
	public void testAdmin(HttpServletRequest request, HttpServletResponse response) {
		responseUtil.setResponse(response, HttpServletResponse.SC_OK, "OK", null);
	}

}
