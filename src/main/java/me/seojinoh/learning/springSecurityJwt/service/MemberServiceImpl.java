package me.seojinoh.learning.springSecurityJwt.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.seojinoh.learning.springSecurityJwt.config.CacheConfig;
import me.seojinoh.learning.springSecurityJwt.config.JwtConfig;
import me.seojinoh.learning.springSecurityJwt.dao.LogoutAccessTokenRedisRepository;
import me.seojinoh.learning.springSecurityJwt.dao.MemberDao;
import me.seojinoh.learning.springSecurityJwt.dao.RefreshTokenRedisRepository;
import me.seojinoh.learning.springSecurityJwt.dto.MemberDetails;
import me.seojinoh.learning.springSecurityJwt.dto.MemberJoinRequest;
import me.seojinoh.learning.springSecurityJwt.dto.MemberLoginRequest;
import me.seojinoh.learning.springSecurityJwt.dto.TokenDto;
import me.seojinoh.learning.springSecurityJwt.entity.LogoutAccessToken;
import me.seojinoh.learning.springSecurityJwt.entity.Member;
import me.seojinoh.learning.springSecurityJwt.entity.RefreshToken;
import me.seojinoh.learning.springSecurityJwt.exception.NotFoundException;
import me.seojinoh.learning.springSecurityJwt.util.DateUtil;
import me.seojinoh.learning.springSecurityJwt.util.JwtTokenUtil;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired private MemberDao						memberDao;
	@Autowired private DateUtil							dateUtil;
	@Autowired private JwtTokenUtil						jwtTokenUtil;
	@Autowired private PasswordEncoder					passwordEncoder;
	@Autowired private RefreshTokenRedisRepository		refreshTokenRedisRepository;
	@Autowired private LogoutAccessTokenRedisRepository	logoutAccessTokenRedisRepository;

	@Override
	@Cacheable(value = CacheConfig.MEMBER_CACHE_NAME, key = "#email", unless = "#result == null")
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberDao.findByEmail(email);

		if(member == null) {
			throw new UsernameNotFoundException(String.format("Member(%s) not found", email));
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
	@Cacheable(value = CacheConfig.MEMBER_CACHE_NAME, key = "#email", unless = "#result == null")
	public MemberDetails readMember(String email) throws NotFoundException {
		Member member = memberDao.findByEmail(email);

		if(member == null) {
			throw new NotFoundException(String.format("Member(%s) not found", email));
		}

		return member.toMemberDetails();
	}

	@Override
	@Transactional
	public void deleteMember(String email) {
		memberDao.deleteByEmail(email);
	}

	@Override
	public TokenDto loginMember(MemberLoginRequest memberLoginRequest) throws UsernameNotFoundException, IllegalArgumentException {
		Member member = memberDao.findByEmail(memberLoginRequest.getEmail());

		if(member == null) {
			throw new UsernameNotFoundException(String.format("Member(%s) not found", memberLoginRequest.getEmail()));
		}

		if(!checkPassword(memberLoginRequest.getPassword(), member.getPassword())) {
			throw new IllegalArgumentException("Password do not match");
		}

		String accessToken = jwtTokenUtil.generateAccessToken(memberLoginRequest.getEmail());
		RefreshToken refreshToken = saveRefreshToken(memberLoginRequest.getEmail());

		return new TokenDto(accessToken, refreshToken.getRefreshToken());
	}

	private boolean checkPassword(String rawPassword, String memberPassword) {
		return passwordEncoder.matches(rawPassword, memberPassword) ? true : false;
	}

	private RefreshToken saveRefreshToken(String email) {
		return refreshTokenRedisRepository.save(new RefreshToken(email, jwtTokenUtil.generateRefreshToken(email), JwtConfig.getREFRESH_TOKEN_EXPIRATION_TIME_IN_MILLISECONDS()));
	}

	@CacheEvict(value = CacheConfig.MEMBER_CACHE_NAME, key = "#email")
	public void logoutMember(TokenDto tokenDto, String email) {
		refreshTokenRedisRepository.deleteById(email);

		String token = jwtTokenUtil.getToken(tokenDto.getAccessToken());

		logoutAccessTokenRedisRepository.save(new LogoutAccessToken(token, email, jwtTokenUtil.getRemainingTimeInMilliseconds(token)));
	}

	public TokenDto reissue(String refreshToken) {
		refreshToken = jwtTokenUtil.getToken(refreshToken);

		String username = getCurrentUsername();
		RefreshToken refreshTokenInRedis = refreshTokenRedisRepository.findById(username).orElseThrow(NoSuchElementException::new);

		if(refreshToken.equals(refreshTokenInRedis.getRefreshToken())) {
			return reissueRefreshToken(refreshToken, username);
		}

		throw new IllegalArgumentException("Invalid token");
	}

	private String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) authentication.getPrincipal();

		return principal.getUsername();
	}

	private TokenDto reissueRefreshToken(String refreshToken, String username) {
		if(lessThanReissueExpirationTimesLeft(refreshToken)) {
			String accessToken = jwtTokenUtil.generateAccessToken(username);

			return new TokenDto(accessToken, saveRefreshToken(username).getRefreshToken());
		}

		return new TokenDto(jwtTokenUtil.generateAccessToken(username), refreshToken);
	}

	private boolean lessThanReissueExpirationTimesLeft(String refreshToken) {
		return jwtTokenUtil.getRemainingTimeInMilliseconds(refreshToken) < JwtConfig.getREFRESH_TOKEN_REISSUE_TIME_IN_MILLISECONDS();
	}

}
