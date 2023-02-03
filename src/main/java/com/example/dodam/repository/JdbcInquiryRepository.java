package com.example.dodam.repository;

import com.example.dodam.model.Inquiry;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
//        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
//        jdbcInsert.withTableName("inquiry").usingGeneratedKeyColumns("id");
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("userId", inquiry.getUserId());
//        parameters.put("title", inquiry.getTitle());
//        parameters.put("content", inquiry.getContent());
//        parameters.put("answer", inquiry.getAnswer());
//        parameters.put("status", inquiry.getStatus());
//        parameters.put("category", inquiry.getCategory());
//        parameters.put("createAt", inquiry.getCreateAt());
//        parameters.put("updateAt", inquiry.getUpdateAt());
//        parameters.put("imgPath", inquiry.getImgPath());
//
//        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
//        inquiry.setId(key.longValue());
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
    public List<Inquiry> findAll() {
        return jdbcTemplate.query("select * from inquiry", inquiryRowmapper());
    }

    private RowMapper<Inquiry> inquiryRowmapper(){
        return (rs, rowNum) -> {
            Inquiry inquiry = new Inquiry();
            inquiry.setId(rs.getLong("id"));
            inquiry.setUserId((int) rs.getLong("userId"));
            inquiry.setTitle(rs.getString("title"));
            inquiry.setContent(rs.getString("content"));
            inquiry.setAnswer(rs.getString("answer"));
            inquiry.setStatus(rs.getString("status"));
            inquiry.setCategory(rs.getString("category"));
            inquiry.setCreateAt(rs.getTimestamp("createAt"));
            inquiry.setUpdateAt(rs.getTimestamp("updateAt"));
            inquiry.setImgPath(rs.getString("imgPath"));
            return inquiry;
        };
    }
}
