package com.icegan.edu.questioncrawler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icegan.edu.questioncrawler.model.CourseSection;

import java.util.List;

public interface ICourseSectionService extends IService<CourseSection> {

    public boolean updateStatusByIds(Integer status, List<String> ids);
}
