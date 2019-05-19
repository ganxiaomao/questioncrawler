package com.icegan.edu.questioncrawler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;
import com.icegan.edu.questioncrawler.model.EduQuestionStem;

import java.util.List;

public interface IEduQuestionStemService extends IService<EduQuestionStem> {

    boolean batchInsert(List<EduQuestionBankBase> bases);

    List<EduQuestionStem> selectByStatusAndLimitAndOffset(int status, int limit, int offset);

    boolean batchUpdateStatusByIds(int status, List<String> ids);

    EduQuestionStem selectOneByStatus(int status);

    boolean updateStemAndStatusById(int status, String stem, String id);
}
