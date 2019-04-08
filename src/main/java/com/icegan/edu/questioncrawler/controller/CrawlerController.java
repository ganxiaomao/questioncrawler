package com.icegan.edu.questioncrawler.controller;

import com.icegan.edu.questioncrawler.service.ICrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CrawlerController {

    @Autowired
    private ICrawlerService iCrawlerService;

    @RequestMapping("/hello")
    public String index(){
        String html = "";
        try {
            html = iCrawlerService.coocoCrawler();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }
}
