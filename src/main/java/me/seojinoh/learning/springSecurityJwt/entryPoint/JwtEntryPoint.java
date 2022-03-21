package me.seojinoh.learning.springSecurityJwt.entryPoint;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import me.seojinoh.learning.springSecurityJwt.util.ResponseUtil;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private ResponseUtil responseUtil;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
		responseUtil.setResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized", null);
	}

}
