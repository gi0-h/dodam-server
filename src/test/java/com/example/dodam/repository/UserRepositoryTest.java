package com.example.dodam.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.dodam.domain.user.User;
import com.example.dodam.repository.user.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser = User.builder()
        .email("test@naver.com")
        .nickname("test")
        .role("ROLE_USER")
        .phone("01000000000")
        .password("123")
        .status("A")
        .build();

    @Test
    void save() {
        User user = userRepository.save(testUser);
        userRepository.findById(testUser.getId());
        assertThat(user.getCreateAt()).isEqualTo(testUser.getCreateAt());
    }

    @Test
    void findByEmail() {
        userRepository.save(testUser);
        User user = userRepository.findByEmail("test@naver.com").get();
        assertThat(user.getId()).isEqualTo(testUser.getId());
    }

    @Test
    void delete() {
        userRepository.save(testUser);
        userRepository.delete(testUser.getId());
        Optional<User> userOptional = userRepository.findById(testUser.getId());
        assertThatThrownBy(userOptional::get)
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void update() {
    }

    @Test
    void findByNickName() {
        userRepository.save(testUser);
        User user = userRepository.findByNickName("test").get();
        assertThat(user.getId()).isEqualTo(testUser.getId());
    }

    @Test
    void findById() {
        userRepository.save(testUser);
        User user = userRepository.findById(testUser.getId()).get();
        assertThat(user.getCreateAt()).isEqualTo(testUser.getCreateAt());
    }
}
