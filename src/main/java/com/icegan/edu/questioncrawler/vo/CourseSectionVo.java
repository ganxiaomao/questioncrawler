package com.icegan.edu.questioncrawler.vo;

import java.io.Serializable;

/**
 * 用来初始化加载课程信息的数据结构
 */
public class CourseSectionVo implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
