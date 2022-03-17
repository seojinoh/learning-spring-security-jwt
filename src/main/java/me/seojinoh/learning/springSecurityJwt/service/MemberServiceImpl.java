package me.seojinoh.learning.springSecurityJwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.seojinoh.learning.springSecurityJwt.dao.MemberDao;
import me.seojinoh.learning.springSecurityJwt.dto.MemberDetails;
import me.seojinoh.learning.springSecurityJwt.dto.MemberJoinRequest;
import me.seojinoh.learning.springSecurityJwt.entity.Member;
import me.seojinoh.learning.springSecurityJwt.exception.NotFoundException;
import me.seojinoh.learning.springSecurityJwt.util.DateUtil;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired private MemberDao	memberDao;
	@Autowired private DateUtil		dateUtil;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberDao.findByEmail(email);

		if(member == null) {
			throw new UsernameNotFoundException(email);
		}

		return member.toMemberDetails();
	}

	@Override
	@Transactional
	public Integer createMember(MemberJoinRequest memberJoinRequest) {
		Member member = memberJoinRequest.toEntityIncludingRegDt(dateUtil.getNowDt());

		return memberDao.save(member).getId();
	}

	@Override
	@Transactional
	public MemberDetails readMember(String email) throws NotFoundException {
		Member member = memberDao.findByEmail(email);

		if(member == null) {
			throw new NotFoundException();
		}

		return member.toMemberDetails();
	}

	@Override
	@Transactional
	public void deleteMember(String email) {
		memberDao.deleteByEmail(email);
	}

}
