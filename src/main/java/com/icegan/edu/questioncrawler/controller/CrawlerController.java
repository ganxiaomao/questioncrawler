package com.icegan.edu.questioncrawler.controller;

import com.icegan.edu.questioncrawler.job.CoocoCrawlJob;
import com.icegan.edu.questioncrawler.service.ICommonDsService;
import com.icegan.edu.questioncrawler.service.ICourseSectionService;
import com.icegan.edu.questioncrawler.service.ICrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CrawlerController {

    @Autowired
    private ICrawlerService iCrawlerService;

    @Autowired
    private CoocoCrawlJob coocoCrawlJob;

    @Autowired
    private ICommonDsService iCommonDsService;

    @Autowired
    private ICourseSectionService iCourseSectionService;

    @RequestMapping("/hello")
    public String index(@RequestParam(name = "subject",required = false) String subject){
        String html = "OK";
        coocoCrawlJob.crawlQuestion();
        return html;
    }

    @RequestMapping("/knowlege")
    public String knowlege(@RequestParam(name = "grade") String grade,@RequestParam(name = "subject") String subject){
        List<Map<String,Object>> datas = iCommonDsService.getDatasBySubjectAndGradeFromCourseAndSection(subject, grade, 1);
        List<String> ids = new ArrayList<>();
        for(Map<String,Object> data : datas){
            String id = data.get("id").toString();
            String courseId = data.get("course_id").toString();
            String knowledgeId = data.get("chapter_id").toString();
            String coocoId = data.get("cooco_id").toString();

            String res = iCrawlerService.coocoCrawlPage(grade,subject,knowledgeId,coocoId, courseId);
            if(!res.equals("error")){
                ids.add(id);
            }
        }
        //批量更新状态
        if(!ids.isEmpty()){
            iCourseSectionService.updateStatusByIds(0, ids);
        }

        return "";
    }

    @RequestMapping("/crawlknowledge")
    public String crawlKnowledge(@RequestParam(name = "jsName",required = true) String jsName, @RequestParam(name = "courseId",required = true) String courseId){
        String res = iCrawlerService.coocoKnowledgeCrawl(jsName, courseId);
        return res;
    }
}
