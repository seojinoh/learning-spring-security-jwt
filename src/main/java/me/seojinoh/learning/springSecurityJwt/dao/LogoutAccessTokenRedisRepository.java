package me.seojinoh.learning.springSecurityJwt.dao;

import org.springframework.data.repository.CrudRepository;

import me.seojinoh.learning.springSecurityJwt.entity.LogoutAccessToken;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
}
