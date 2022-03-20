package me.seojinoh.learning.springSecurityJwt.entity;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("refreshToken")
public class RefreshToken {

	@Id
	private String	id;
	private String	refreshToken;
	@TimeToLive
	private Long	expiration;

	@SuppressWarnings("unused")
	private RefreshToken() {
	}

	public RefreshToken(String email, String refreshToken, Long remainingTimeInMilliseconds) {
		this.id				= email;
		this.refreshToken	= refreshToken;
		this.expiration		= remainingTimeInMilliseconds / 1000;
	}

	public String getId() {
		return id;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public Long getExpiration() {
		return expiration;
	}

}
