package me.seojinoh.learning.springSecurityJwt.util;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import me.seojinoh.learning.springSecurityJwt.dto.CustomResponse;

@Component
public class ResponseUtil {

	@SuppressWarnings("deprecation")
	public void setResponse(HttpServletResponse response, CustomResponse customResponse, int status, String message, Object data) {
		response.setStatus(status, message);
		customResponse.setStatus(status);
		customResponse.setMessage(message);
		customResponse.setData(data);
	}

}
