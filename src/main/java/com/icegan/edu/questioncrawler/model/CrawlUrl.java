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
    private int status;//0,未处理；1，抓取完成；-1，抓取失败。

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CrawlUrl{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", subject='" + subject + '\'' +
                ", grade='" + grade + '\'' +
                ", status=" + status +
                '}';
    }
}
