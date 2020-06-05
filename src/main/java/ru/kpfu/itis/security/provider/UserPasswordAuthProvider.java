package ru.kpfu.itis.security.provider;

import ru.kpfu.itis.models.User;
import ru.kpfu.itis.security.token.ApplicationAuthToken;
import ru.kpfu.itis.repositories.UsersRepository;
import ru.kpfu.itis.security.AuthenticationImpl;
import ru.kpfu.itis.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.utils.HttpResponseStatus;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserPasswordAuthProvider implements AuthenticationProvider {

	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!authentication.getClass().isAssignableFrom(ApplicationAuthToken.class))
			throw new IllegalArgumentException();

		String password = (String) authentication.getCredentials();
		String email = authentication.getName();
		if (StringUtils.isAnyBlank(password, email)) {
			throw new BadCredentialsException(HttpResponseStatus.EMPTY_PARAM);
		}

		User user = usersRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new BadCredentialsException(HttpResponseStatus.BAD_CREDENTIALS));

		if (!passwordEncoder.matches(password, user.getPasswordHash())) {
			throw new BadCredentialsException(HttpResponseStatus.BAD_CREDENTIALS);
		}

		final String userAuthority = user.getRole().name();
		List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(userAuthority);

		return new AuthenticationImpl(CurrentUser.getBuilder()
				.id(user.getId())
				.username(user.getId().toString())
				.enabled(true)
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.authorities(authorityList)
				.build());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(ApplicationAuthToken.class);
	}
}
