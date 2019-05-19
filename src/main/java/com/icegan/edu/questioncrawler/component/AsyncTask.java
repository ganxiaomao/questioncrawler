package com.icegan.edu.questioncrawler.component;

import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;
import com.icegan.edu.questioncrawler.service.IEduQuestionBaseBankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AsyncTask {
    private static final Logger logger = LogManager.getLogger(AsyncTask.class);
    @Autowired
    private IEduQuestionBaseBankService iEduQuestionBaseBankService;

    @Async
    public void shardTransTask(String subject){
        logger.info("本次分表数据开始，学科为："+subject);
        int offset = 0;
        int limit = 1000;
        while(true){
            //从数据库读取
            List<EduQuestionBankBase> bbs = iEduQuestionBaseBankService.selectDatasBySubjectAndLimit(subject,offset,limit);

            if(bbs == null || bbs.isEmpty()){
                logger.info("本次分表数据结束，学科为："+subject);
                break;
            }

            logger.info("本次"+subject+"分表数据"+bbs.size()+"条");
            iEduQuestionBaseBankService.shardInsert(bbs,subject);
            offset+= limit;
        }
    }
}
