package com.example.dodam.repository.schedule;

import com.example.dodam.domain.schedule.Schedule;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository {
    Integer save(Schedule schedule);    // 일정 등록


}
