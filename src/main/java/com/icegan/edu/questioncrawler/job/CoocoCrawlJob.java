package com.icegan.edu.questioncrawler.job;

import com.icegan.edu.questioncrawler.constant.Constants;
import com.icegan.edu.questioncrawler.model.CrawlUrl;
import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;
import com.icegan.edu.questioncrawler.service.ICrawlUrlService;
import com.icegan.edu.questioncrawler.service.ICrawlerService;
import com.icegan.edu.questioncrawler.service.IEduQuestionBaseBankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoocoCrawlJob {
    private static final Logger logger = LogManager.getLogger(CoocoCrawlJob.class);

    @Autowired
    private ICrawlUrlService iCrawlUrlService;
    @Autowired
    private ICrawlerService iCrawlerService;
    @Autowired
    private IEduQuestionBaseBankService iEduQuestionBaseBankService;

    /**
     * 定时抓取cooco网站的题目。
     * 该函数执行完成后5s后，再次执行该任务
     * initialDelay一定要有这个延迟，因为初始加载数据的时候，会晚于这个任务执行，导致运行时Constants里的数据都为空，延迟的时间自己估算，尽量等于或大于系统完全启动的时间
     */
    @Scheduled(initialDelay = 30000,fixedDelay = 5000)
    public void crawlQuestion(){
        try{
            logger.info("====开始执行Cooco网站题目抓取任务====");
            CrawlUrl crawlUrl = iCrawlUrlService.selectOneByStatus(0,-1);//获取一条未处理或者失败的连接记录
            List<EduQuestionBankBase> eduQuestionBankBases = iCrawlerService.coocoCrawler(crawlUrl.getUrl(), crawlUrl.getGrade(), crawlUrl.getSubject());
            if(eduQuestionBankBases != null){
                //保存抓取到的题目
                iEduQuestionBaseBankService.saveBatch(eduQuestionBankBases);
                //更新记录状态为抓取完毕
                iCrawlUrlService.updateStatusById(1,crawlUrl.getId());
            }else{
                iCrawlUrlService.updateStatusById(-1,crawlUrl.getId());
            }
        } catch(Exception e){
            logger.info("===执行Cooco网站题目抓取任务出错====\n"+e);
        }
    }
}
