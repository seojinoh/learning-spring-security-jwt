package me.seojinoh.learning.springSecurityJwt.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import me.seojinoh.learning.springSecurityJwt.entity.Member;

public class MemberJoinRequest {

	private String email;
	private String password;
	private String name;
	private String phoneNumber;
	private String roles;
	private String method;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Member toEntity() {
		return new Member.Builder(email, generateEncodedPassword(), name, phoneNumber, roles, method)
				.build();
	}

	public Member toEntityIncludingRegDt(String regDt) {
		return new Member.Builder(email, generateEncodedPassword(), name, phoneNumber, roles, method)
				.setRegDt(regDt)
				.build();
	}

	public MemberJoinResponse toMemberJoinResponse() {
		MemberJoinResponse memberJoinResponse = new MemberJoinResponse();
		memberJoinResponse.setEmail(this.email);
		memberJoinResponse.setName(this.name);
		memberJoinResponse.setPhoneNumber(this.phoneNumber);
		memberJoinResponse.setRoles(this.roles);
		memberJoinResponse.setMethod(this.method);

		return memberJoinResponse;
	}

	private String generateEncodedPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		return passwordEncoder.encode(password);
	}

}
