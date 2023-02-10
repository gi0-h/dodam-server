package com.example.dodam.repository.schedule;

import com.example.dodam.domain.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class JdbcScheduleRepository implements ScheduleRepository{

    String updateQuery = "update schedule set name = ?, repeatStatus = ?, selectDate = ?, selectDay = ?, startDate = ?, endDate = ?, startTime = ?, endTime = ?, color = ? where scheduleId = ?";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    @Autowired
    public JdbcScheduleRepository(DataSource source) {
        jdbcTemplate = new JdbcTemplate(source);
        jdbcInsert = new SimpleJdbcInsert(source)  // 데이터소스로 DB에 접근
                .withTableName("schedule")              // "schedule" 테이블에 삽입
                .usingGeneratedKeyColumns("scheduleId")                 // "scheduleId" 컬럼의 값을 key로 반환
                .usingColumns("userId", "name", "repeatStatus", "selectDate", "selectDay",
                        "startDate", "endDate", "startTime", "endTime", "color");    // SimpleJdbcInsert시 사용할 컬럼을 지정
    }


    // 일정 등록
    @Override
    public Integer save(Schedule schedule) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(schedule);
        Number key = jdbcInsert.executeAndReturnKey(param);
        schedule.setScheduleId(key.intValue());         // scheduleId를 schedule에 저장
        return schedule.getScheduleId();
    }

    // 일정 수정
    @Override
    @Transactional
    public Integer update(Schedule schedule) {
        Integer id = schedule.getScheduleId();
        jdbcTemplate.update(updateQuery, schedule.getName(), schedule.getRepeatStatus(), schedule.getSelectDate(), schedule.getSelectDay(),
                schedule.getStartDate(), schedule.getEndDate(), schedule.getStartTime(), schedule.getEndTime(), schedule.getColor(), id );
        return id;
    }
}
