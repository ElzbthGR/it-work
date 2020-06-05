package ru.kpfu.itis.security;

import ru.kpfu.itis.models.User;
import ru.kpfu.itis.services.UsersService;
import ru.kpfu.itis.services.auth.TokensService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HeaderAuthenticationFilter extends HttpFilter {

	private final TokensService tokensService;
	private final UsersService usersService;

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		SecurityContext securityContext = loadSecurityContext(request);
		SecurityContextHolder.setContext(securityContext);

		chain.doFilter(request, response);
	}

	private SecurityContext loadSecurityContext(HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		if (StringUtils.isNotBlank(authToken)) {
			try {
				authToken = authToken.substring("Bearer ".length());
			} catch (IndexOutOfBoundsException e) {
				log.info("Not valid token");
			}
		}
		Optional<Long> optionalUserId = tokensService.getUserId(authToken);
		UserDetails userDetails;
		if (optionalUserId.isPresent()) {
			Optional<User> optionalUser = usersService.find(optionalUserId.get());
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(user.getRole().name());
				userDetails = CurrentUser.getBuilder()
						.id(user.getId())
						.username(user.getId().toString())
						.enabled(true)
						.accountNonExpired(true)
						.accountNonLocked(true)
						.credentialsNonExpired(true)
						.authorities(authorityList)
						.build();
				return new CustomSecurityContext(userDetails);
			} else {
				log.info("User with id {} not found", optionalUserId);
			}
		}
		userDetails = CurrentUser.getBuilder()
				.username(CurrentUser.ANONYMOUS_AUTHORITY)
				.enabled(true)
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.authorities(CurrentUser.ANONYMOUS_AUTHORITIES)
				.build();
		return new CustomSecurityContext(userDetails);
	}
}
