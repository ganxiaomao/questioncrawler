package com.icegan.edu.questioncrawler.job;

import com.icegan.edu.questioncrawler.model.CrawlUrl;
import com.icegan.edu.questioncrawler.service.ICrawlUrlService;
import com.icegan.edu.questioncrawler.service.ICrawlerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CoocoCrawlJob {
    private static final Logger logger = LogManager.getLogger(CoocoCrawlJob.class);

    @Autowired
    private ICrawlUrlService iCrawlUrlService;
    @Autowired
    private ICrawlerService iCrawlerService;

    /**
     * 定时抓取cooco网站的题目。
     * 该函数执行完成后5s后，再次执行该任务
     */
    @Scheduled(fixedDelay = 5000)
    public void crawlQuestion(){
        try{
            logger.info("====开始执行Cooco网站题目抓取任务====");
            CrawlUrl crawlUrl = iCrawlUrlService.selectOneByStatus(0,-1);//获取一条未处理或者失败的连接记录
        } catch(Exception e){
            logger.info("===执行Cooco网站题目抓取任务出错====\n"+e);
        }
    }
}
