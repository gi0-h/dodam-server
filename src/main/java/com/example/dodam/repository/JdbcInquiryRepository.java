package com.example.dodam.repository;

import com.example.dodam.domain.Inquiry;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class JdbcInquiryRepository implements InquiryRepository{

    private static final String TABLE = "inquiry";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcInquiryRepository(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id")
                .usingColumns("userId", "title", "content", "answer", "status", "category",
                        "createAt", "updateAt", "imgPath");
    }

    @Override
    public Inquiry save(Inquiry inquiry) {

        inquiry.setCreateAt(LocalDateTime.now());
        inquiry.setUpdateAt(LocalDateTime.now());
        SqlParameterSource param = new BeanPropertySqlParameterSource(inquiry);
        Number key = jdbcInsert.executeAndReturnKey(param);
        inquiry.setId(key.longValue());
        return inquiry;
    }

    @Override
    public Optional<Inquiry> findById(Long id) {
        List<Inquiry> result = jdbcTemplate.query("select * from inquiry where id = ?",inquiryRowmapper(),id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Inquiry> deleteById(Long id) {
        jdbcTemplate.update("delete from inquiry where id = ?",id);
        return Optional.empty();
    }

    @Override
    public List<Inquiry> findAll(Long userId) {
        return jdbcTemplate.query("select * from inquiry where userId = ?", inquiryRowmapper(), userId); //userId에 해당하는 문의사항만 출력 select * from inquiry where userId = ?
    }

    @Override
    @Transactional
    public Inquiry update(Long id, Inquiry inquiry) {

        inquiry.setUpdateAt(LocalDateTime.now());
        jdbcTemplate.update("update inquiry set title = ?, content = ?, imgPath = ?,category = ? , answer = ? where id = ?",inquiry.getTitle(),inquiry.getContent(),inquiry.getImgPath(),inquiry.getCategory(),inquiry.getAnswer(),id);
        return inquiry;
    }


    private RowMapper<Inquiry> inquiryRowmapper() {
        return BeanPropertyRowMapper.newInstance(Inquiry.class);
    }
}

