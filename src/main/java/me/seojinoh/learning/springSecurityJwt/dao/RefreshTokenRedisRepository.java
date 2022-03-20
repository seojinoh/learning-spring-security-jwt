package me.seojinoh.learning.springSecurityJwt.dao;

import org.springframework.data.repository.CrudRepository;

import me.seojinoh.learning.springSecurityJwt.entity.RefreshToken;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
