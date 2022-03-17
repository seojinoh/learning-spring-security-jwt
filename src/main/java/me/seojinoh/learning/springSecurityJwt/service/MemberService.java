package me.seojinoh.learning.springSecurityJwt.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import me.seojinoh.learning.springSecurityJwt.dto.MemberDetails;
import me.seojinoh.learning.springSecurityJwt.dto.MemberJoinRequest;

public interface MemberService extends UserDetailsService {

	Integer			createMember(MemberJoinRequest memberJoinRequest);
	MemberDetails	readMember(String email);
	void			deleteMember(String email);

}
