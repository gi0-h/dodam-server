package com.example.dodam.repository.user;

import com.example.dodam.domain.user.User;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class JdbcUserRepository implements UserRepository {
    private static final String TABLE = "USER";

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcUserRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id")
                .usingColumns("email", "password", "phone", "nickname", "status", "imgPath", "role",
                        "birthdate", "updateAt", "startDate");
    }

    @Override
    public Optional<User> findByUsername(String email) {
        String sql = "select * from user where email = :email";
        try {
            Map<String, Object> param = Map.of("email", email);
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