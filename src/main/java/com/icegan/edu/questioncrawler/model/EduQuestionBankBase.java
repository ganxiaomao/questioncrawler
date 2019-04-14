package com.icegan.edu.questioncrawler.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 题库基本信息表
 * </p>
 *
 * @author icegan
 * @since 2018-09-16
 */
@TableName("biz_edu_question_bank_base")
public class EduQuestionBankBase extends Model<EduQuestionBankBase> {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;
    /**
     * 题干
     */
    private String stem;
    /**
     * 题目类型
     */
    private String type;
    /**
     * 学科
     */
    private String subject;
    /**
     * 年级
     */
    private String grade;
    /**
     * 难易程度
     */
    private String difficult;
    /**
     * 课程id
     */
    @TableField("course_id")
    private String courseId;
    /**
     * 章节id
     */
    @TableField("chapter_id")
    private String chapterId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 创建人id
     */
    @TableField("create_by")
    private Integer createBy;
    /**
     * 题目状态
     */
    private Integer status;
    /**
     * 解析
     */
    private String analysis;
    /**
     * cooco网题目的解析id，抓取用
     */
    @TableField("answer_id")
    private String answerId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EduQuestionBankBase{" +
                "id=" + id +
                ", stem=" + stem +
                ", type=" + type +
                ", subject=" + subject +
                ", grade=" + grade +
                ", difficult=" + difficult +
                ", courseId=" + courseId +
                ", chapterId=" + chapterId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy=" + createBy +
                ", status=" + status +
                ", analysis=" + analysis +
                ",answerId=" + answerId +
                "}";
    }
}
