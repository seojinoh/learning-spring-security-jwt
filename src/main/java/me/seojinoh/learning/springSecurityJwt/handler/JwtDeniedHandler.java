package me.seojinoh.learning.springSecurityJwt.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import me.seojinoh.learning.springSecurityJwt.util.ResponseUtil;

@Component
public class JwtDeniedHandler implements AccessDeniedHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private ResponseUtil responseUtil;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		responseUtil.setResponse(response, HttpServletResponse.SC_FORBIDDEN, "Forbidden", null);
	}

}
