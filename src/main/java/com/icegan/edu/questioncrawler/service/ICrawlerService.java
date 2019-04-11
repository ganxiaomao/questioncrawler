package com.icegan.edu.questioncrawler.service;

import java.io.IOException;

public interface ICrawlerService {
    String coocoCrawler();

    /**
     * 抓取cooco网的知识点各科
     * @return
     */
    String coocoCrawlSubject(String subject);

    String coocoCrawlPage(String grade,String subject);
}
