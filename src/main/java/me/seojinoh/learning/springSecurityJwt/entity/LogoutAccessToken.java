package me.seojinoh.learning.springSecurityJwt.entity;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("logoutAccessToken")
public class LogoutAccessToken {

	@Id
	private String	id;
	private String	username;
	@TimeToLive
	private Long	expiration;

	@SuppressWarnings("unused")
	private LogoutAccessToken() {
	}

	public LogoutAccessToken(String accessToken, String email, Long remainingTimeInMilliseconds) {
		this.id				= accessToken;
		this.username		= email;
		this.expiration		= remainingTimeInMilliseconds / 1000;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Long getExpiration() {
		return expiration;
	}

}
