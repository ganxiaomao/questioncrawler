package com.icegan.edu.questioncrawler.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

@TableName("biz_crawlurl")
public class CrawlUrl extends Model<CrawlUrl> {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("url")
    private String url;

    @TableField("subject")
    private String subject;

    @TableField("grade")
    private String grade;

    @TableField("status")
    private Integer status;//0,未处理；1，抓取完成；-1，抓取失败。

    @TableField("knowledge_id")
    private String knowledgeId;

    @TableField("course_id")
    private String courseId;

    @TableField("cooco_id")
    private String coocoId;

    @TableField("origin_from")
    private String originFrom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCoocoId() {
        return coocoId;
    }

    public void setCoocoId(String coocoId) {
        this.coocoId = coocoId;
    }

    public String getOriginFrom() {
        return originFrom;
    }

    public void setOriginFrom(String originFrom) {
        this.originFrom = originFrom;
    }

    @Override
    public String toString() {
        return "CrawlUrl{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", subject='" + subject + '\'' +
                ", grade='" + grade + '\'' +
                ", status=" + status +
                ", knowledgeId=" + knowledgeId +
                ", courseId=" + courseId +
                ", coocoId=" + coocoId +
                ", originFrom=" + originFrom +
                '}';
    }
}
