package com.example.dodam.repository.user;

import com.example.dodam.domain.user.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    /**
     * email -> loginId로 변경
     */
    Optional<User> findByEmail(String email);
    void deleteById(Long userId);
    User Update(Long userId, User user);
    Optional<User> findByNickName(String nickname);
    Optional<User> findById(Long id);
}
