package me.seojinoh.learning.springSecurityJwt.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.seojinoh.learning.springSecurityJwt.dto.MemberDetails;
import me.seojinoh.learning.springSecurityJwt.dto.MemberJoinRequest;
import me.seojinoh.learning.springSecurityJwt.exception.NotFoundException;
import me.seojinoh.learning.springSecurityJwt.service.MemberService;
import me.seojinoh.learning.springSecurityJwt.util.ResponseUtil;

@RestController
@RequestMapping(value = "/member")
public class MemberJoinRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private MemberService	memberService;
	@Autowired private ResponseUtil		responseUtil;

	@PostMapping("")
	public void postMember(HttpServletRequest request, HttpServletResponse response, @RequestBody MemberJoinRequest memberJoinRequest) {
		try {
			memberService.createMember(memberJoinRequest);

			responseUtil.setResponse(response, HttpServletResponse.SC_OK, "OK", memberJoinRequest.toMemberJoinResponse());
		} catch(Exception e) {
			responseUtil.setResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to register member", null);
		}
	}

	@GetMapping("")
	public void getMember(HttpServletRequest request, HttpServletResponse response, @RequestParam Optional<String> email) {
		if(email.isPresent() && StringUtils.hasText(email.get())) {
			try {
				MemberDetails memberDetails = memberService.readMember(email.get());

				responseUtil.setResponse(response, HttpServletResponse.SC_OK, "OK", memberDetails);
			} catch(NotFoundException e) {
				responseUtil.setResponse(response, HttpServletResponse.SC_NOT_FOUND, String.format("Member(%s) not found", email.get()), null);
			} catch(Exception e) {
				responseUtil.setResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to get member details", null);
			}
		} else {
			responseUtil.setResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Parameter(email) not found", null);
		}
	}

	@DeleteMapping("")
	public void deleteMember(HttpServletRequest request, HttpServletResponse response, @RequestParam Optional<String> email) {
		if(email.isPresent() && StringUtils.hasText(email.get())) {
			try {
				memberService.deleteMember(email.get());

				responseUtil.setResponse(response, HttpServletResponse.SC_OK, "OK", null);
			} catch(Exception e) {
				responseUtil.setResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete member", null);
			}
		} else {
			responseUtil.setResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Parameter(email) not found", null);
		}
	}

}
