package ru.kpfu.itis.controllers.client;

import ru.kpfu.itis.dto.Response;
import ru.kpfu.itis.dto.TokenDto;
import ru.kpfu.itis.dto.forms.AuthorizationForm;
import ru.kpfu.itis.dto.forms.UserRegistrationForm;
import ru.kpfu.itis.security.annotations.PermitAll;
import ru.kpfu.itis.services.auth.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Slf4j
@RequiredArgsConstructor
@Api(tags = {"Client.Auth"})
@RestController
@RequestMapping(AuthClientController.ROOT_URL)
public class AuthClientController {

	public static final String ROOT_URL = "/v1/client";
	private static final String LOGIN_URL = "/login";
	private static final String REGISTER_URL = "/register";

	private final AuthService authService;

	@PermitAll
	@ApiOperation(value = "Login")
	@ApiResponses({
			@ApiResponse(
					code = SC_BAD_REQUEST,
					message = "Account or password not specified: \"empty-param\"; " +
							"account param has invalid email: \"invalid-email\""),
			@ApiResponse(
					code = SC_UNAUTHORIZED,
					message = "Authentication failed"
			)
	})
	@PostMapping(LOGIN_URL)
	public Response<TokenDto> login(@RequestBody final AuthorizationForm form) {
		return Response.ok(authService.authenticate(form.getLogin(), form.getPassword()));
	}

	@PermitAll
	@ApiOperation(value = "Registration")
	@ApiResponses({
			@ApiResponse(
					code = SC_BAD_REQUEST,
					message = "Account or password not specified: \"empty-param\"; " +
							"account param has invalid email: \"invalid-email\""),
			@ApiResponse(
					code = SC_UNAUTHORIZED,
					message = "Authentication failed"
			)
	})
	@PostMapping(REGISTER_URL)
	public Response<TokenDto> register(@RequestBody @Valid final UserRegistrationForm form) {
		return Response.ok(authService.register(form));
	}
}
