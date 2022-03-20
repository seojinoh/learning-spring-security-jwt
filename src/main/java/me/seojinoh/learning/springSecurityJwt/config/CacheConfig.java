package me.seojinoh.learning.springSecurityJwt.config;

import java.time.Duration;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import me.seojinoh.learning.springSecurityJwt.property.CacheProperty;

@Configuration
@EnableCaching
@EnableConfigurationProperties(value = {CacheProperty.class})
public class CacheConfig {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired CacheProperty cacheProperty;

	public	static final String		MEMBER_CACHE_NAME = "member";
	private	static int				EXPIRATION_TIME_IN_SECONDS;

	@PostConstruct
	private void init() {
		setEXPIRATION_TIME_IN_SECONDS(cacheProperty.getEXPIRATION_TIME_IN_SECONDS());
	}

	@Bean
	public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.disableCachingNullValues()
				.entryTtl(Duration.ofSeconds(getEXPIRATION_TIME_IN_SECONDS()))
				.computePrefixWith(CacheKeyPrefix.simple())
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

		return RedisCacheManager.RedisCacheManagerBuilder
				.fromConnectionFactory(redisConnectionFactory)
				.cacheDefaults(redisCacheConfiguration)
				.build();
	}

	public static int getEXPIRATION_TIME_IN_SECONDS() {
		return EXPIRATION_TIME_IN_SECONDS;
	}

	private static void setEXPIRATION_TIME_IN_SECONDS(int expirationTimeInSeconds) {
		EXPIRATION_TIME_IN_SECONDS = expirationTimeInSeconds;
	}

}
