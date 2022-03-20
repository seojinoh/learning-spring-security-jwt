package me.seojinoh.learning.springSecurityJwt.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import me.seojinoh.learning.springSecurityJwt.dto.MemberDetails;
import me.seojinoh.learning.springSecurityJwt.dto.MemberJoinRequest;
import me.seojinoh.learning.springSecurityJwt.dto.MemberLoginRequest;
import me.seojinoh.learning.springSecurityJwt.dto.TokenDto;
import me.seojinoh.learning.springSecurityJwt.exception.NotFoundException;

public interface MemberService extends UserDetailsService {

	Integer			createMember(MemberJoinRequest memberJoinRequest);
	MemberDetails	readMember(String email) throws NotFoundException;
	void			deleteMember(String email);

	TokenDto		loginMember(MemberLoginRequest memberLoginRequest);
	void			logoutMember(TokenDto tokenDto, String email);
	TokenDto		reissue(String refreshToken);

}
