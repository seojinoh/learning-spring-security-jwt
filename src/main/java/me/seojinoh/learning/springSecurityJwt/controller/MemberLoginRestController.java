package me.seojinoh.learning.springSecurityJwt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.seojinoh.learning.springSecurityJwt.dto.CustomResponse;
import me.seojinoh.learning.springSecurityJwt.dto.MemberLoginRequest;
import me.seojinoh.learning.springSecurityJwt.dto.TokenDto;
import me.seojinoh.learning.springSecurityJwt.service.MemberService;
import me.seojinoh.learning.springSecurityJwt.util.JwtTokenUtil;
import me.seojinoh.learning.springSecurityJwt.util.ResponseUtil;

@RestController
@RequestMapping(value = "/member")
public class MemberLoginRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private MemberService	memberService;
	@Autowired private ResponseUtil		responseUtil;
	@Autowired private JwtTokenUtil		jwtTokenUtil;

	@PostMapping("/login")
	public void postLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody MemberLoginRequest memberLoginRequest) {
		try {
			TokenDto tokenDto = memberService.loginMember(memberLoginRequest);

			responseUtil.setResponse(response, HttpServletResponse.SC_OK, "OK", tokenDto);
		} catch(Exception e) {
			responseUtil.setResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Login failure", null);
		}
	}

	@PostMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response, @RequestHeader("Authorization") String accessToken, @RequestHeader("RefreshToken") String refreshToken) {
		try {
			memberService.logoutMember(new TokenDto(accessToken, refreshToken), jwtTokenUtil.getUsername(jwtTokenUtil.getToken(accessToken)));

			responseUtil.setResponse(response, HttpServletResponse.SC_OK, "OK", null);
		} catch(Exception e) {
			responseUtil.setResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Logout failure", null);
		}
	}

	@PostMapping("/reissue")
	public void reissue(HttpServletRequest request, HttpServletResponse response, @RequestHeader("RefreshToken") String refreshToken) {
		try {
			TokenDto tokenDto = memberService.reissue(refreshToken);

			responseUtil.setResponse(response, HttpServletResponse.SC_OK, "OK", tokenDto);
		} catch(Exception e) {
			responseUtil.setResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to reissue token", null);
		}
	}

}
