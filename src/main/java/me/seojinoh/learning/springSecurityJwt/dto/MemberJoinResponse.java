package me.seojinoh.learning.springSecurityJwt.dto;

public class MemberJoinResponse {

	private String email;
	private String name;
	private String phoneNumber;
	private String roles;
	private String method;

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getRoles() {
		return roles;
	}

	public String getMethod() {
		return method;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
