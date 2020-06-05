package ru.kpfu.itis.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.forms.UserRegistrationForm;
import ru.kpfu.itis.exceptions.NotFoundException;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.UsersRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {

	private final UsersRepository usersRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public User register(final UserRegistrationForm form) {
		log.info("Registering new user...");
		User user = new User();
		fill(user, form);
		return usersRepository.save(user);
	}

	public void fill(final User user, final UserRegistrationForm form) {
		log.info("Filling user info...");
		user
				.setEmail(form.getEmail())
				.setFirsName(form.getFirstName())
				.setLastName(form.getLastName())
				.setPasswordHash(passwordEncoder.encode(form.getPassword()))
				.setRole(User.Role.CLIENT);
	}

	public User get(Long userId) {
		return find(userId)
				.orElseThrow(this::notFoundException);
	}

	public Optional<User> find(@NonNull Long userId) {
		return usersRepository.findByIdAndDeletedFalse(userId);
	}

	public boolean existsByEmail(@NonNull String email) {
		return usersRepository.existsByEmailIgnoreCaseAndDeletedFalse(email.trim().toLowerCase());
	}

	private NotFoundException notFoundException() {
		return new NotFoundException("User not found");
	}
}
