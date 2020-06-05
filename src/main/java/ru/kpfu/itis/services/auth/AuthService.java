package ru.kpfu.itis.services.auth;

import ru.kpfu.itis.dto.TokenDto;
import ru.kpfu.itis.dto.forms.UserRegistrationForm;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.security.token.ApplicationAuthToken;
import ru.kpfu.itis.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.services.UsersService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;

	private final TokensService tokensService;
	private final UsersService usersService;

	public TokenDto authenticate(String login, String password) {
		Long userId = null;
		String role = null;
		TokenDto tokenDto = new TokenDto();
		AbstractAuthenticationToken authenticationToken;
		authenticationToken = new ApplicationAuthToken(login, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		if (authentication == null || !authentication.isAuthenticated()) {
			log.info("Failed to authenticate user [{}]", login);
			throw new BadCredentialsException("bad-credentials");
		}
		Object userDetailsObject = authentication.getDetails();
		if (userDetailsObject instanceof CurrentUser) {
			final CurrentUser userDetails = (CurrentUser) userDetailsObject;
			userId = userDetails.getId();
			final GrantedAuthority authority = userDetails.getAuthorities().toArray(new GrantedAuthority[1])[0];
			role = authority == null ? null : authority.getAuthority();
		}
		log.info("Successfully signed in user [{}] with email [{}]", userId, login);
		tokenDto.setUserId(userId);
		tokenDto.setAuthToken(tokensService.generateToken(userId));
		tokenDto.setRole(role);
		return tokenDto;
	}

	public TokenDto register(final UserRegistrationForm form) {
		final User user = usersService.register(form);
		return authenticate(user.getEmail(), form.getPassword());
	}
}
