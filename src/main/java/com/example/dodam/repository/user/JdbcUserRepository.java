package com.example.dodam.repository.user;

import com.example.dodam.domain.user.UpdateUserDto;
import com.example.dodam.domain.user.User;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
public class JdbcUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate template;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("USER")
            .usingGeneratedKeyColumns("id")
            .usingColumns("email", "password", "phone", "nickname", "status", "imgPath", "role",
                "birthdate", "createAt", "updateAt", "startDate");
    }

    @Override
    @Transactional
    public User save(User user) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(user);
        log.debug("user={}", user);
        Number key = jdbcInsert.executeAndReturnKey(param);
        user.setId(key.longValue());
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "select * from user where email = :email";
        try {
            Map<String, Object> param = Map.of("email", email);
            User user = template.queryForObject(sql, param, userRowMapper());
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void delete(Long userid) {
        String sql = "delete from user where id = :id";
        Map<String, Object> param = Map.of("id", userid);
        template.update(sql, param);
        log.debug("delete user = {}", userid);
    }

    @Override
    @Transactional
    public User Update(String userid, UpdateUserDto userDto) {
        return userDto.toEntity();
    }

    @Override
    public Optional<User> findByNickName(String nickname) {
        String sql = "select * from user where nickname = :nickname";
        try {
            Map<String, Object> param = Map.of("nickname", nickname);
            User user = template.queryForObject(sql, param, userRowMapper());
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "select * from user where id = :id";
        try {
            Map<String, Object> param = Map.of("id", id);
            User user = template.queryForObject(sql, param, userRowMapper());
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<User> userRowMapper() {
        return BeanPropertyRowMapper.newInstance(User.class);
    }

}
