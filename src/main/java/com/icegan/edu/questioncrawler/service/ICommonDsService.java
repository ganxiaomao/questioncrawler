package com.icegan.edu.questioncrawler.service;

import com.icegan.edu.questioncrawler.vo.CourseSectionVo;

import java.util.Map;

public interface ICommonDsService {
    public Map<String, CourseSectionVo> getAllCourseSection();
}
