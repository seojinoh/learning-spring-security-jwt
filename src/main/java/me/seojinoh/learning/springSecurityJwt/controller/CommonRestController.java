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

@RestController
@RequestMapping(value = "/api")
public class CommonRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private MemberService memberService;

	@PostMapping("/member")
	public Object postMember(HttpServletRequest request, HttpServletResponse response, @RequestBody MemberJoinRequest memberJoinRequest) {
		Object result = null;

		try {
			memberService.createMember(memberJoinRequest);

			response.setStatus(HttpServletResponse.SC_OK);
			result = memberJoinRequest.toMemberJoinResponse();
		} catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			result = "Failed to register member";
		}

		return result;
	}

	@GetMapping("/member")
	public Object getMember(HttpServletRequest request, HttpServletResponse response, @RequestParam Optional<String> email) {
		Object result = null;

		if(email.isPresent() && StringUtils.hasText(email.get())) {
			try {
				MemberDetails memberDetails = memberService.readMember(email.get());

				response.setStatus(HttpServletResponse.SC_OK);
				result = memberDetails;
			} catch(NotFoundException e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				result = String.format("Member(%s) not found", email.get());
			} catch(Exception e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				result = "Failed to get member details";
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result = "Parameter(email) not found";
		}

		return result;
	}

	@DeleteMapping("/member")
	public Object deleteMember(HttpServletRequest request, HttpServletResponse response, @RequestParam Optional<String> email) {
		Object result = null;

		if(email.isPresent() && StringUtils.hasText(email.get())) {
			try {
				memberService.deleteMember(email.get());

				response.setStatus(HttpServletResponse.SC_OK);
			} catch(Exception e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				result = "Failed to delete member";
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result = "Parameter(email) not found";
		}

		return result;
	}

}
