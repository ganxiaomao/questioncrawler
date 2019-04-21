package com.icegan.edu.questioncrawler.service;

import com.icegan.edu.questioncrawler.vo.CourseSectionVo;

import java.util.List;
import java.util.Map;

public interface ICommonDsService {
    public Map<String, CourseSectionVo> getAllCourseSection();

    /**
     * 从course和course_section表中依据subject和grade获取数据
     * @param subject
     * @param grade
     * @param status
     * @return
     */
    public List<Map<String,Object>> getDatasBySubjectAndGradeFromCourseAndSection(String subject, String grade, Integer status);
}
