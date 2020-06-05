package ru.kpfu.itis.configs;

import ru.kpfu.itis.security.HeaderAuthenticationFilter;
import ru.kpfu.itis.security.provider.UserPasswordAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final HeaderAuthenticationFilter authenticationFilter;
	private final UserPasswordAuthProvider userPasswordAuthProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.requestCache().disable()
				.rememberMe().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterAfter(authenticationFilter, BasicAuthenticationFilter.class)
				.authorizeRequests()
				.and()
				.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler())
				.and()
				.logout().permitAll();
	}

	@Autowired
	public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(userPasswordAuthProvider);
	}

	public static class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
		}
	}
}
