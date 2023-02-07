package com.example.dodam;
import java.sql.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * SpringBoot의 가장 기본적인 설정 선언.
 * @Controller, @Service, @Repository 등의 Annotation 스캔 및 Bean 등록
 * 사전의 정의한 라이브러리들을 Bean 등록
 *
 * **Bean 간단 설명: 스프링 컨테이너가 관리하는 자바 객체
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DodamApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(DodamApplication.class, args);  //스프링 부트 프로젝트의 시작

	}
}


