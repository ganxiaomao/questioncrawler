package com.icegan.edu.questioncrawler.controller;

import com.icegan.edu.questioncrawler.component.AsyncTask;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/async")
public class AsyncController {
    private static final Logger logger = LogManager.getLogger(AsyncController.class);

    @Autowired
    private AsyncTask asyncTask;

    @RequestMapping("/shardTrans")
    public String shardTrans(@RequestParam("subject") String subject){
        //
        asyncTask.shardTransTask(subject);
        return "任务已调用成功，在后台运行";
    }

    @RequestMapping("/downLoadImg")
    public String downLoadImg(@Param("type") int type){
        //
        return "图片下载任务已调用成功，在后台运行";
    }
}
