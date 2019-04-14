package com.icegan.edu.questioncrawler.init;

import com.icegan.edu.questioncrawler.constant.Contants;
import com.icegan.edu.questioncrawler.service.ICommonDsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseConstantInit implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(CourseConstantInit.class);

    @Autowired
    private ICommonDsService iCommonDsService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("开始初始化知识点");
        Contants.csvMap.putAll(iCommonDsService.getAllCourseSection());
        int size = Contants.csvMap.size();
        logger.info("知识点信息初始化，总共条数为："+size);
    }
}
