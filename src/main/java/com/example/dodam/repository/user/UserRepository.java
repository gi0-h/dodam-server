
package com.example.dodam.repository.user;

import com.example.dodam.domain.user.User;
import java.util.Optional;

public interface UserRepository {
	/**
	 * email -> loginId로 변경
	 */
	Optional<User> findByUsername(String email);
}