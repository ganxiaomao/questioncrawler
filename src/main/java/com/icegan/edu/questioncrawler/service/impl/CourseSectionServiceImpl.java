package com.icegan.edu.questioncrawler.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icegan.edu.questioncrawler.dao.CourseSectionMapper;
import com.icegan.edu.questioncrawler.model.CourseSection;
import com.icegan.edu.questioncrawler.service.ICourseSectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseSectionServiceImpl extends ServiceImpl<CourseSectionMapper, CourseSection> implements ICourseSectionService {
    private static final Logger logger = LogManager.getLogger(CourseSectionServiceImpl.class);

    @Override
    public boolean updateStatusByIds(Integer status, List<String> ids) {
        UpdateWrapper<CourseSection> updateWrapper = new UpdateWrapper<>();
        this.baseMapper.update(null, updateWrapper.set("status", status).in("id", ids));
        return true;
    }
}
