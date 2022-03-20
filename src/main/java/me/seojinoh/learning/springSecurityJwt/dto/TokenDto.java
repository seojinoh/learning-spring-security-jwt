package me.seojinoh.learning.springSecurityJwt.dto;

import me.seojinoh.learning.springSecurityJwt.config.JwtConfig;

public class TokenDto {

	private String grantType;
	private String accessToken;
	private String refreshToken;

	public TokenDto(String accessToken, String refreshToken) {
		this.grantType		= JwtConfig.getGRANT_TYPE();
		this.accessToken	= accessToken;
		this.refreshToken	= refreshToken;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
