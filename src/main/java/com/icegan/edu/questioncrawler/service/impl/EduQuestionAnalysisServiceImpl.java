package com.icegan.edu.questioncrawler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icegan.edu.questioncrawler.dao.EduQuestionAnalysisMapper;
import com.icegan.edu.questioncrawler.model.EduQuestionAnalysis;
import com.icegan.edu.questioncrawler.service.IEduQuestionAnalysisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EduQuestionAnalysisServiceImpl extends ServiceImpl<EduQuestionAnalysisMapper, EduQuestionAnalysis> implements IEduQuestionAnalysisService {
    private static final Logger logger = LogManager.getLogger(EduQuestionAnalysisServiceImpl.class);

    @Override
    public EduQuestionAnalysis selectOneByQuestionBaseId(String questionBaseId) {
        EduQuestionAnalysis eduQuestionAnalysis = null;
        if(questionBaseId != null && !questionBaseId.isEmpty()){
            QueryWrapper<EduQuestionAnalysis> queryWrapper = new QueryWrapper<>();
            eduQuestionAnalysis = this.baseMapper.selectOne(queryWrapper.eq("question_base_id", questionBaseId));
        }
        return eduQuestionAnalysis;
    }
}
