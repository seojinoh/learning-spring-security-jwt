package me.seojinoh.learning.springSecurityJwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import me.seojinoh.learning.springSecurityJwt.dao.LogoutAccessTokenRedisRepository;
import me.seojinoh.learning.springSecurityJwt.service.MemberService;
import me.seojinoh.learning.springSecurityJwt.util.JwtTokenUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private JwtTokenUtil						jwtTokenUtil;
	@Autowired private MemberService					memberService;
	@Autowired private LogoutAccessTokenRedisRepository	logoutAccessTokenRedisRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		String accessToken = jwtTokenUtil.getToken(request);

		if(accessToken != null) {
			checkLogout(accessToken);

			String username = jwtTokenUtil.getUsername(accessToken);

			if(username != null) {
				UserDetails userDetails = memberService.loadUserByUsername(username);

				validateAccessToken(accessToken, userDetails);
				processSecurity(request, userDetails);
			}
		}

		filterChain.doFilter(request, response);
	}

	private void checkLogout(String accessToken) {
		if(logoutAccessTokenRedisRepository.existsById(accessToken)) {
			throw new IllegalArgumentException("Already logged out member");
		}
	}

	private void validateAccessToken(String accessToken, UserDetails userDetails) {
		if(!jwtTokenUtil.validateToken(accessToken, userDetails)) {
			throw new IllegalArgumentException("Token validation failed");
		}
	}

	private void processSecurity(HttpServletRequest request, UserDetails userDetails) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}

}
