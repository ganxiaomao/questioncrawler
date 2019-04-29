package com.icegan.edu.questioncrawler.job;

import com.icegan.edu.questioncrawler.constant.Constants;
import com.icegan.edu.questioncrawler.model.CrawlUrl;
import com.icegan.edu.questioncrawler.model.EduQuestionAnalysis;
import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;
import com.icegan.edu.questioncrawler.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    @Autowired
    private IEduQuestionAnalysisService iEduQuestionAnalysisService;
    @Autowired
    private IEduQuestionStemService iEduQuestionStemService;

    /**
     * 定时抓取cooco网站的题目。
     * 该函数执行完成后5s后，再次执行该任务
     * initialDelay一定要有这个延迟，因为初始加载数据的时候，会晚于这个任务执行，导致运行时Constants里的数据都为空，延迟的时间自己估算，尽量等于或大于系统完全启动的时间
     */
    @Scheduled(initialDelay = 30000,fixedDelay = 5000)
    public void crawlQuestion(){
        try{
            CrawlUrl crawlUrl = iCrawlUrlService.selectOneByStatus(0,-1);//获取一条未处理或者失败的连接记录
            logger.info("====开始执行Cooco网站题目抓取任务,crawlurlId="+crawlUrl.getId()+"====");
            if(crawlUrl == null){
                logger.info("===没有更多待抓取的任务===");
                return;
            }
            List<EduQuestionBankBase> eduQuestionBankBases = iCrawlerService.coocoCrawler(crawlUrl.getUrl(), crawlUrl.getGrade(), crawlUrl.getSubject(), crawlUrl.getCourseId(), crawlUrl.getKnowledgeId(), crawlUrl.getOriginFrom());
            if(eduQuestionBankBases != null){
                //保存抓取到的题目
                iEduQuestionBaseBankService.saveBatch(eduQuestionBankBases);
                //保存题干
                iEduQuestionStemService.batchInsert(eduQuestionBankBases);
                //更新记录状态为抓取完毕
                iCrawlUrlService.updateStatusById(1,crawlUrl.getId());
            }else{
                iCrawlUrlService.updateStatusById(-1,crawlUrl.getId());
            }
        } catch(Exception e){
            logger.info("===执行Cooco网站题目抓取任务出错====\n",e.getCause());
        }
    }

    //@Scheduled(initialDelay = 30000,fixedDelay = 5000)
    public void crawlQuestionAnalysis(){
        try{
            List<String> successIds = new ArrayList<>();
            List<String> failIds = new ArrayList<>();
            List<EduQuestionAnalysis> eduQuestionAnalyses = new ArrayList<>();
            //从questionbankbase表中查找已经完成题干抓取的数据
            List<EduQuestionBankBase> eduQuestionBankBases = iEduQuestionBaseBankService.selectDatasByStatusesAndLimit(
                    10,
                    Constants.cooco_crawl_question_status_stem_ok,
                    Constants.cooco_crawl_question_status_answer_fail);
            for(EduQuestionBankBase eqb : eduQuestionBankBases){
                String originFrom = eqb.getOriginFrom();
                String coocoId = eqb.getAnswerId();
                String html = iCrawlerService.coocoAnalysisCrawl(originFrom, coocoId);
                if(html == null){
                    //失败
                    failIds.add(eqb.getId());
                }else{
                    //成功
                    successIds.add(eqb.getId());
                    EduQuestionAnalysis eduQuestionAnalysis = new EduQuestionAnalysis();
                    eduQuestionAnalysis.setAnalysis(html);
                    eduQuestionAnalysis.setQuestionBaseId(eqb.getId());
                    eduQuestionAnalysis.setStatus(0);
                    eduQuestionAnalyses.add(eduQuestionAnalysis);
                }
            }
            if(!successIds.isEmpty())
                iEduQuestionBaseBankService.updateStatusByIds(Constants.cooco_crawl_question_status_answer_ok,successIds);
            if(!failIds.isEmpty())
                iEduQuestionBaseBankService.updateStatusByIds(Constants.cooco_crawl_question_status_answer_fail,null);
            if(!eduQuestionAnalyses.isEmpty()){
                //批量保存解析
                iEduQuestionAnalysisService.saveBatch(eduQuestionAnalyses);
            }
        }catch(Exception e){
            logger.info("error:",e.getCause());
        }
    }
}
