package me.seojinoh.learning.springSecurityJwt.property;

import java.beans.ConstructorProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("jwt")
public final class JwtProperty {

	public final String		GRANT_TYPE;
	public final String		SECRET_KEY;
	public final long		ACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS;
	public final long		REFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS;
	public final long		REFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS;

	@ConstructorProperties({"grantType", "secretKey", "accessTokenExpirationTimeInMilliseconds", "refreshTokenExpirationTimeInMilliseconds", "refreshTokenReissueTimeInMilliseconds"})
	public JwtProperty(String grantType, String secretKey, long accessTokenExpirationTimeInMilliseconds, long refreshTokenExpirationTimeInMilliseconds, long refreshTokenReissueTimeInMilliseconds) {
		this.GRANT_TYPE										= grantType + " ";
		this.SECRET_KEY										= secretKey;
		this.ACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS	= accessTokenExpirationTimeInMilliseconds;
		this.REFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS	= refreshTokenExpirationTimeInMilliseconds;
		this.REFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS		= refreshTokenReissueTimeInMilliseconds;
	}

	public String getGRANT_TYPE() {
		return GRANT_TYPE;
	}

	public String getSECRET_KEY() {
		return SECRET_KEY;
	}

	public long getACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS() {
		return ACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS;
	}

	public long getREFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS() {
		return REFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS;
	}

	public long getREFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS() {
		return REFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS;
	}

}
