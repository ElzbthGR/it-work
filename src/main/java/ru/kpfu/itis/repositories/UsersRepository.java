package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.User;

import java.util.Optional;

public interface UsersRepository extends JpaDeletableRepository<User, Long> {
	Optional<User> findByEmailIgnoreCase(String email);
	boolean existsByEmailIgnoreCaseAndDeletedFalse(String email);
}
