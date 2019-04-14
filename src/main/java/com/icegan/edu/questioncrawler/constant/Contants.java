package com.icegan.edu.questioncrawler.constant;

import com.icegan.edu.questioncrawler.vo.CourseSectionVo;

import java.util.HashMap;
import java.util.Map;

/**
 * 一些常量
 */
public class Contants {
    //应用初始化的时候，用于保存课程信息，key为知识点名称，value为课程信息结构数据
    public static Map<String, CourseSectionVo> csvMap = new HashMap<>();

    /**
     * biz_edu_question_bank_base表status的值，只针对cooco网的情况
     */
    public static final int cooco_crawl_question_status__ok = 0;//题干和解析都抓取成功
    public static final int cooco_crawl_question_status_stem_ok = 3;//题干抓取成功
    public static final int cooco_crawl_question_status_answer_ok = 4;//解析抓取成功
    public static final int cooco_crawl_question_status_answer_fail = 5;//解析抓取失败

}
