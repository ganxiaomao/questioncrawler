package com.icegan.edu.questioncrawler.constant;

import com.icegan.edu.questioncrawler.vo.CourseSectionVo;

import java.util.HashMap;
import java.util.Map;

/**
 * 一些常量
 */
public class Constants {
    //应用初始化的时候，用于保存课程信息，key为知识点名称，value为课程信息结构数据
    public static Map<String, CourseSectionVo> csvMap = new HashMap<>();

    public static Map<String,String> difficultMap = new HashMap<>();//用于保存难度信息
    public static Map<String,String> gradeMap = new HashMap<>();//用于保存年级信息
    public static Map<String,String> questiontypeMap = new HashMap<>();//用于保存题目类型信息
    public static Map<String,String> subjectMap = new HashMap<>();//用于保存学科信息

    /**
     * biz_edu_question_bank_base表status的值，只针对cooco网的情况
     */
    public static final int cooco_crawl_question_status__ok = 0;//题干和解析都抓取成功
    public static final int cooco_crawl_question_status_stem_ok = 3;//题干抓取成功
    public static final int cooco_crawl_question_status_answer_ok = 4;//解析抓取成功
    public static final int cooco_crawl_question_status_answer_fail = 5;//解析抓取失败

}
