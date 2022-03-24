package me.seojinoh.learning.springSecurityJwt.util;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.seojinoh.learning.springSecurityJwt.dto.CustomResponse;

@Component
public class ResponseUtil {

	@Autowired private ObjectMapper objectMapper;

	public void setResponse(HttpServletResponse response, int status, String message, Object data) {
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(status);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		try {
			response.getWriter().write(objectMapper.writeValueAsString(new CustomResponse(status, message, data)));
		} catch(Exception e) {
		}
	}

}
