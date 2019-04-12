package com.icegan.edu.questioncrawler.controller;

import com.icegan.edu.questioncrawler.service.ICrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CrawlerController {

    @Autowired
    private ICrawlerService iCrawlerService;

    @RequestMapping("/hello")
    public String index(@RequestParam(name = "subject") String subject){
        String html = iCrawlerService.coocoCrawler();
        return html;
    }

    @RequestMapping("/knowlege")
    public String knowlege(@RequestParam(name = "grade") String grade,@RequestParam(name = "subject") String subject){
        return iCrawlerService.coocoCrawlPage(grade,subject);
    }
}
