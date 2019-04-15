package com.icegan.edu.questioncrawler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icegan.edu.questioncrawler.dao.CourseSectionMapper;
import com.icegan.edu.questioncrawler.model.CourseSection;
import com.icegan.edu.questioncrawler.service.ICommonDsService;
import com.icegan.edu.questioncrawler.vo.CourseSectionVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommonDsServiceImpl implements ICommonDsService {
    private static final Logger logger = LogManager.getLogger(CommonDsServiceImpl.class);

    @Resource
    private CourseSectionMapper courseSectionMapper;

    @Override
    public Map<String, CourseSectionVo> getAllCourseSection() {
        Map<String,CourseSectionVo> map = new HashMap<>();
        QueryWrapper<CourseSection> queryWrapper = new QueryWrapper<>();
        List<CourseSection> courseSectionList = courseSectionMapper.selectList(queryWrapper.eq("1",1));
        for(CourseSection cs : courseSectionList){
            String id = cs.getId();
            String name = cs.getName();
            String parentId = cs.getCourseId();
            CourseSectionVo vo = new CourseSectionVo();
            vo.setId(id);
            vo.setName(name);
            vo.setParentId(parentId);
            map.put(name,vo);
        }
        return map;
    }
}
