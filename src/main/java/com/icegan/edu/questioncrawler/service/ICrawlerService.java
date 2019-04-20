package com.icegan.edu.questioncrawler.service;

import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;

import java.io.IOException;
import java.util.List;

public interface ICrawlerService {
    List<EduQuestionBankBase> coocoCrawler(String url, String grade, String subject, String courseId, String courseSectionId, String originFrom);

    /**
     * 抓取cooco网的知识点各科
     * @return
     */
    String coocoCrawlSubject(String subject);

    String coocoCrawlPage(String grade,String subject, String knowlegeId, String coocoId, String courseId);

    /**
     * 从cooco网站获取答案解析
     * @param originFrom
     * @param answerId
     * @return
     */
    String coocoAnalysisCrawl(String originFrom, String answerId);
}
