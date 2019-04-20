package com.icegan.edu.questioncrawler.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icegan.edu.questioncrawler.model.CourseSection;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CourseSectionMapper extends BaseMapper<CourseSection> {

    @Select("select bc.id as course_id,bc.subject, bc.grade,bcs.id as chapter_id,bcs.cooco_id,bcs.name from biz_course_section bcs left join biz_course bc on (bc.id=bcs.course_id) where bc.subject=#{subject} and bc.grade=#{grade} and bcs.cooco_id is not null")
    List<Map<String,Object>> selectMapBySubjectAndGrade(String subject, String grade);
}
