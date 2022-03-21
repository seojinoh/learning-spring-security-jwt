package me.seojinoh.learning.springSecurityJwt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import me.seojinoh.learning.springSecurityJwt.entryPoint.JwtEntryPoint;
import me.seojinoh.learning.springSecurityJwt.filter.JwtAuthenticationFilter;
import me.seojinoh.learning.springSecurityJwt.handler.JwtDeniedHandler;
import me.seojinoh.learning.springSecurityJwt.service.MemberService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private JwtEntryPoint			jwtEntryPoint;
	@Autowired private JwtDeniedHandler			jwtDeniedHandler;
	@Autowired private JwtAuthenticationFilter	jwtAuthenticationFilter;
	@Autowired private MemberService			memberService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers("/lib/**", "/js/**", "/css/**", "/img/**");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable();

		httpSecurity.authorizeRequests()
			.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
			.antMatchers("/admin/**").hasAnyRole("ADMIN")
			.antMatchers("/**").permitAll();

		httpSecurity.exceptionHandling()
			.authenticationEntryPoint(jwtEntryPoint)
			.accessDeniedHandler(jwtDeniedHandler);

		httpSecurity.logout().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(memberService).passwordEncoder(passwordEncoder());
	}

}
