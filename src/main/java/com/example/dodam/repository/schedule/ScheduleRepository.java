package com.example.dodam.repository.schedule;

import com.example.dodam.domain.schedule.Schedule;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ScheduleRepository {
    Integer save(Schedule schedule);    // 일정 등록
    Integer update(Schedule schedule);    // 일정 수정
    Optional<Schedule> deleteById(Integer scheduleId);    // 일정 삭제

}
