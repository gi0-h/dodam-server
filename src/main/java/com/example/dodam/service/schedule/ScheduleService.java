package com.example.dodam.service.schedule;

import com.example.dodam.domain.schedule.Schedule;
import com.example.dodam.repository.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    // 일정 등록
    public Integer save(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    // 일정 수정
    public Integer update(Schedule schedule){
        return scheduleRepository.update(schedule);
    }
}
