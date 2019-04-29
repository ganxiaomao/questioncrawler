package com.icegan.edu.questioncrawler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icegan.edu.questioncrawler.dao.EduQuestionStemMapper;
import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;
import com.icegan.edu.questioncrawler.model.EduQuestionStem;
import com.icegan.edu.questioncrawler.service.IEduQuestionStemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EduQuestionStemServiceImpl extends ServiceImpl<EduQuestionStemMapper, EduQuestionStem> implements IEduQuestionStemService {
    private static final Logger logger = LogManager.getLogger(EduQuestionStemServiceImpl.class);

    @Override
    public boolean batchInsert(List<EduQuestionBankBase> bases) {
        List<EduQuestionStem> stems = new ArrayList<>();
        for(EduQuestionBankBase base : bases){
            EduQuestionStem stem = new EduQuestionStem();
            stem.setStem(base.getStem());
            stem.setStatus(1);//尚未替换图片地址的状态
            stem.setQuestionBaseId(base.getId());
            stems.add(stem);
        }
        //批量保存
        this.saveBatch(stems);
        return true;
    }
}
