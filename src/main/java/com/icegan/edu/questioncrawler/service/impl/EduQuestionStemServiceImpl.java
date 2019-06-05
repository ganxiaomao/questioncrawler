package com.icegan.edu.questioncrawler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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

    @Override
    public List<EduQuestionStem> selectByStatusAndLimitAndOffset(int status, int limit, int offset) {
        QueryWrapper<EduQuestionStem> queryWrapper = new QueryWrapper<>();
        List<EduQuestionStem> datas = this.baseMapper.selectList(queryWrapper.eq("status",status).last("limit "+offset+","+limit));
        return datas;
    }

    @Override
    public boolean batchUpdateStatusByIds(int status, List<String> ids) {
        UpdateWrapper<EduQuestionStem> updateWrapper = new UpdateWrapper<>();
        this.baseMapper.update(null, updateWrapper.set("status", status).in("id", ids));
        return true;
    }

    @Override
    public EduQuestionStem selectOneByStatus(int status) {
        EduQuestionStem stem = null;
        QueryWrapper<EduQuestionStem> queryWrapper = new QueryWrapper<>();
        List<EduQuestionStem> stems = this.baseMapper.selectList(queryWrapper.eq("status",status).last("limit 1"));
        if(stems != null && !stems.isEmpty())
            stem = stems.get(0);
        return stem;
    }

    @Override
    public boolean updateStemAndStatusById(int status, String stem, String id) {
        UpdateWrapper<EduQuestionStem> updateWrapper = new UpdateWrapper<>();
        this.baseMapper.update(null, updateWrapper.set("status",status).set("stem",stem).eq("id",id));
        return true;
    }

    @Override
    public boolean updateStatusById(int status, String id) {
        UpdateWrapper<EduQuestionStem> updateWrapper = new UpdateWrapper<>();
        this.baseMapper.update(null, updateWrapper.set("status",status).eq("id",id));
        return true;
    }
}
