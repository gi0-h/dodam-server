package com.example.dodam;

import com.example.dodam.repository.DiaryRepository;
import com.example.dodam.repository.JdbcDiaryRepository;
import com.example.dodam.service.DiaryService;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {
    private final DataSource dataSource;

    public Configuration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public DiaryService diaryService(){
        return new DiaryService(diaryRepository());
    }
    @Bean
    public DiaryRepository diaryRepository(){
        return new JdbcDiaryRepository(dataSource);
    }

}
