package com.icegan.edu.questioncrawler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icegan.edu.questioncrawler.model.EduQuestionAnalysis;

public interface IEduQuestionAnalysisService extends IService<EduQuestionAnalysis> {
    /**
     * 根据题目基本信息表id查找答案解析
     * @param questionBaseId
     * @return
     */
    public EduQuestionAnalysis selectOneByQuestionBaseId(String questionBaseId);
}
