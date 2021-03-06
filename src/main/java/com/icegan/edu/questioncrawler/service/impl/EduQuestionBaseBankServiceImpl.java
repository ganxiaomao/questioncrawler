package com.icegan.edu.questioncrawler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icegan.edu.questioncrawler.dao.EduQuestionBankBaseMapper;
import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;
import com.icegan.edu.questioncrawler.service.IEduQuestionBaseBankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EduQuestionBaseBankServiceImpl extends ServiceImpl<EduQuestionBankBaseMapper, EduQuestionBankBase> implements IEduQuestionBaseBankService {
    private static final Logger logger = LogManager.getLogger(EduQuestionBaseBankServiceImpl.class);

    @Override
    public List<EduQuestionBankBase> selectDatasByStatusesAndLimit(int limit,Integer... status) {
        QueryWrapper<EduQuestionBankBase> queryWrapper  = new QueryWrapper<>();
        List<EduQuestionBankBase> datas = this.baseMapper.selectList(queryWrapper.in("status",status).last("limit "+limit));
        return datas;
    }

    @Override
    public boolean updateStatusByIds(Integer status, List<String> ids) {
        UpdateWrapper<EduQuestionBankBase> updateWrapper = new UpdateWrapper<>();
        this.baseMapper.update(null,updateWrapper.set("status",status).in("id",ids));
        return true;
    }

    @Override
    public List<EduQuestionBankBase> selectDatasByOffsetAndLimit(int offset, int limit) {
        QueryWrapper<EduQuestionBankBase> queryWrapper  = new QueryWrapper<>();
        List<EduQuestionBankBase> datas = this.baseMapper.selectList(queryWrapper.last("limit "+offset+","+limit));
        return datas;
    }

    @Override
    public boolean clearStemFiled(List<String> ids) {
        return false;
    }

    @Override
    public List<EduQuestionBankBase> selectDatasBySubjectAndLimit(String subject,int offset, int limit) {
        QueryWrapper<EduQuestionBankBase> queryWrapper  = new QueryWrapper<>();
        List<EduQuestionBankBase> datas = this.baseMapper.selectList(queryWrapper.eq("subject",subject).last("limit "+offset+","+limit));
        return datas;
    }

    @Override
    public int shardInsert(List<EduQuestionBankBase> questions, String subject) {
        //List<EduQuestionBankBase> temp = this.baseMapper.selectDatasBySubject(subject);
        Map<String,Object> params = new HashMap<>();
        params.put("subject",subject);
        params.put("questions",questions);
        this.baseMapper.insertBatch(subject,questions);
        return 0;
    }
}
