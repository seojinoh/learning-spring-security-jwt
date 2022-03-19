package me.seojinoh.learning.springSecurityJwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import me.seojinoh.learning.springSecurityJwt.dto.MemberDetails;

@Entity
@Table(name = "tb_member", indexes = {
	@Index(name = "idx_email", columnList = "email", unique = true),
	@Index(name = "idx_phone_number", columnList = "phone_number", unique = true),
	@Index(name = "idx_method_and_email", columnList = "method, email", unique = true)
})
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 80, nullable = false)
	private String email;

	@Column(length = 200, nullable = false)
	private String password;

	@Column(length = 200, nullable = false)
	private String name;

	@Column(name = "phone_number", length = 200, nullable = false)
	private String phoneNumber;

	@Column(length = 80, nullable = false)
	private String roles;

	@Column(length = 80, nullable = false)
	private String method;

	@Column(name = "reg_dt")
	private String regDt;

	@Column(name = "last_login_dt")
	private String lastLoginDt;

	private Member() {
	}

	public static class Builder {
		private String email;
		private String password;
		private String name;
		private String phoneNumber;
		private String roles;
		private String method;
		private String regDt;
		private String lastLoginDt;

		@SuppressWarnings("unused")
		private Builder() {
		}

		public Builder(String email, String password, String name, String phoneNumber, String roles, String method) {
			this.email = email;
			this.password = password;
			this.name = name;
			this.phoneNumber = phoneNumber;
			this.roles = roles;
			this.method = method;
		}

		public Builder setRegDt(String regDt) {
			this.regDt = regDt;

			return this;
		}

		public Builder setLastLoginDt(String lastLoginDt) {
			this.lastLoginDt = lastLoginDt;

			return this;
		}

		public Member build() {
			Member member = new Member();
			member.email = email;
			member.password = password;
			member.name = name;
			member.phoneNumber = phoneNumber;
			member.roles = roles;
			member.method = method;
			member.regDt = regDt;
			member.lastLoginDt = lastLoginDt;

			return member;
		}
	}

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

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

	public MemberDetails toMemberDetails() {
		MemberDetails memberDetails = new MemberDetails();
		memberDetails.setEmail(getEmail());
		memberDetails.setPassword(getPassword());
		memberDetails.setName(getName());
		memberDetails.setPhoneNumber(getPhoneNumber());
		memberDetails.setRoles(getRoles());
		memberDetails.setMethod(getMethod());
		memberDetails.setRegDt(getRegDt());
		memberDetails.setLastLoginDt(getLastLoginDt());

		return memberDetails;
	}

}
