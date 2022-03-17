package me.seojinoh.learning.springSecurityJwt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import me.seojinoh.learning.springSecurityJwt.entity.Member;

public interface MemberDao extends JpaRepository<Member, Integer> {

	Member	findByEmail(String email);
	void	deleteByEmail(String email);

}
