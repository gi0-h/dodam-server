package com.example.dodam.repository.medicalRecord;

import com.example.dodam.domain.medicalRecord.MedicalRecord;
import com.example.dodam.domain.medicalRecord.MedicalRecordList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMedicalRecordRepository implements MedicalRecordRepository {

    String updateQuery = "update medicalrecord set date = ?, detail = ? where id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JdbcMedicalRecordRepository(DataSource source) {
        jdbcTemplate = new JdbcTemplate(source);
        jdbcInsert = new SimpleJdbcInsert(source)       // 데이터소스로 DB에 접근
                .withTableName("medicalRecord")         // "medicalRecord" 테이블에 삽입
                .usingGeneratedKeyColumns("id")             // "id" 컬럼의 값을 key로 반환
                .usingColumns("userId", "date", "detail");  // SimpleJdbcInsert시 사용할 컬럼을 지정
    }

    // 진료기록 등록
    @Override
    public Integer save(MedicalRecord medicalRecord) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(medicalRecord);
        Number key = jdbcInsert.executeAndReturnKey(param);
        medicalRecord.setId(key.intValue());
        return medicalRecord.getId();
    }

    // 진료기록 수정
    @Override
    @Transactional
    public Integer update(MedicalRecord medicalRecord) {
        Integer id = medicalRecord.getId();
        jdbcTemplate.update(updateQuery, medicalRecord.getDate(), medicalRecord.getDetail(), id);
        return id;
    }


}
