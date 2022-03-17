package me.seojinoh.learning.springSecurityJwt.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MemberDetails implements UserDetails {

	private static final long serialVersionUID = 1110387436621534654L;

	private String email;
	private String password;
	private String name;
	private String phoneNumber;
	private String roles;
	private String method;
	private String regDt;
	private String lastLoginDt;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		List<String> roles = Arrays.asList(this.roles.split(","));

		for(String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
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

	public String getRegDt() {
		return regDt;
	}

	public String getLastLoginDt() {
		return lastLoginDt;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public void setLastLoginDt(String lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}

}
