package me.seojinoh.learning.springSecurityJwt.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import me.seojinoh.learning.springSecurityJwt.property.JwtProperty;

@Configuration
@EnableCaching
@EnableConfigurationProperties(value = {JwtProperty.class})
public class JwtConfig {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired JwtProperty jwtProperty;

	private static String	GRANT_TYPE;
	private static String	SECRET_KEY;
	private static long		ACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS;
	private static long		REFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS;
	private static long		REFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS;

	@PostConstruct
	private void init() {
		setGRANT_TYPE(jwtProperty.getGRANT_TYPE());
		setSECRET_KEY(jwtProperty.getSECRET_KEY());
		setACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS(jwtProperty.getACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS());
		setREFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS(jwtProperty.getREFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS());
		setREFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS(jwtProperty.getREFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS());
	}

	public static String getGRANT_TYPE() {
		return GRANT_TYPE;
	}

	private static void setGRANT_TYPE(String grantType) {
		GRANT_TYPE = grantType;
	}

	public static String getSECRET_KEY() {
		return SECRET_KEY;
	}

	private static void setSECRET_KEY(String secretKey) {
		SECRET_KEY = secretKey;
	}

	public static long getACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS() {
		return ACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS;
	}

	private static void setACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS(long accessTokenExpirationTimeInMilliseconds) {
		ACCESS_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS = accessTokenExpirationTimeInMilliseconds;
	}

	public static long getREFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS() {
		return REFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS;
	}

	private static void setREFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS(long refreshTokenExpirationTimeInMilliseconds) {
		REFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS = refreshTokenExpirationTimeInMilliseconds;
	}

	public static long getREFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS() {
		return REFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS;
	}

	private static void setREFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS(long refreshTokenReissueTimeInMilliseconds) {
		REFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS = refreshTokenReissueTimeInMilliseconds;
	}

}
