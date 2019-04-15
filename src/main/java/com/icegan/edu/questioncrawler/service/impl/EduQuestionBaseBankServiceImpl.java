package com.icegan.edu.questioncrawler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icegan.edu.questioncrawler.dao.EduQuestionBankBaseMapper;
import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;
import com.icegan.edu.questioncrawler.service.IEduQuestionBaseBankService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EduQuestionBaseBankServiceImpl extends ServiceImpl<EduQuestionBankBaseMapper, EduQuestionBankBase> implements IEduQuestionBaseBankService {
}
