package com.example.dodam.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.dodam.domain.model.Diary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcDiaryRepository implements DiaryRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcDiaryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public Diary save(Diary diary) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("diary").usingGeneratedKeyColumns("id");
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("userId",diary.getUserId());
        parameters.put("date",diary.getDate());
        parameters.put("title",diary.getTitle());
        parameters.put("imgPath",diary.getImgPath());
        parameters.put("oneWord",diary.getOneWord());
        parameters.put("feel",diary.getFeel());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        diary.setId(key.intValue());
        return diary;
    }

    @Override
    public String updateDiary(Diary diary) {
        Integer diaryId = diary.getId();
        jdbcTemplate.update("update diary set title = ?,imgPath = ?,oneWord = ? , feel = ? where id = ?",diary.getTitle(),diary.getImgPath(),diary.getOneWord(),diary.getFeel(),diary.getId());
        return "ok";
    }


    @Override
    public Optional<Diary>  findByDate(String date){
        List<Diary> result = jdbcTemplate.query("select * from diary where date = ?",diaryRowmapper(),date);
        return result.stream().findAny();
    }

    @Override
    public List<Diary> findAll() {
        return jdbcTemplate.query("select * from diary", diaryRowmapper());
    }

    private RowMapper<Diary> diaryRowmapper(){
        return (rs, rowNum) -> {
            Diary diary = new Diary();
            diary.setId((int) rs.getLong("id"));
            diary.setUserId((int) rs.getLong("userId"));
            diary.setDate(rs.getDate("date"));
            diary.setTitle(rs.getString("title"));
            diary.setFeel(rs.getString("feel"));
            diary.setOneWord(rs.getString("oneWord"));
            diary.setImgPath(rs.getString("imgPath"));
            return diary;
        };
    }
}
