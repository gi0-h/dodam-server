package com.example.dodam.repository.user;

import com.example.dodam.domain.user.UpdateUserDto;
import com.example.dodam.domain.user.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    void delete(Long userId);
    User Update(String userId, UpdateUserDto userDto);
    Optional<User> findByNickName(String nickname);
    Optional<User> findById(Long id);
}
