package com.icegan.edu.questioncrawler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.icegan.edu.questioncrawler.dao")
public class QuestioncrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestioncrawlerApplication.class, args);
    }

}
