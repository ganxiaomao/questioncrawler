package com.icegan.edu.questioncrawler.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icegan.edu.questioncrawler.model.CourseSection;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CourseSectionMapper extends BaseMapper<CourseSection> {

    @Select("select bcs.id,bc.id as course_id,bcs.id as chapter_id,bcs.cooco_id from biz_course_section bcs left join biz_course bc on (bc.id=bcs.course_id) where bc.subject=#{subject} and bc.grade=#{grade} and bcs.status=#{status}")
    List<Map<String,Object>> selectMapBySubjectAndGrade(@Param("subject") String subject, @Param("grade") String grade,@Param("status") Integer status);
}
