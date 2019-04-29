package com.icegan.edu.questioncrawler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;
import com.icegan.edu.questioncrawler.model.EduQuestionStem;

import java.util.List;

public interface IEduQuestionStemService extends IService<EduQuestionStem> {

    boolean batchInsert(List<EduQuestionBankBase> bases);
}
