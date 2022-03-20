package me.seojinoh.learning.springSecurityJwt.property;

import java.beans.ConstructorProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("cache")
public final class CacheProperty {

	private final int		EXPIRATION_TIME_IN_SECONDS;
	private final String	MEMBER_CACHE_NAME;

	@ConstructorProperties({"expirationTimeInSeconds", "memberCacheName"})
	public CacheProperty(int expirationTimeInSeconds, String memberCacheName) {
		this.EXPIRATION_TIME_IN_SECONDS	= expirationTimeInSeconds;
		this.MEMBER_CACHE_NAME			= memberCacheName;
	}

	public int getEXPIRATION_TIME_IN_SECONDS() {
		return EXPIRATION_TIME_IN_SECONDS;
	}

	public String getMEMBER_CACHE_NAME() {
		return MEMBER_CACHE_NAME;
	}

}
