package com.example.dodam.config;

import com.example.dodam.repository.InquiryRepository;
import com.example.dodam.repository.JdbcInquiryRepository;
import com.example.dodam.service.InquiryService;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Config {
    private final DataSource dataSource;

    public Config(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public InquiryService inquiryService(){
        return new InquiryService(inquiryRepository());
    }
    @Bean
    public InquiryRepository inquiryRepository(){
        return new JdbcInquiryRepository(dataSource);
    }

}
