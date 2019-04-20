package com.icegan.edu.questioncrawler.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 题目解析表结构体
 */
@TableName("biz_edu_question_bank_analysis")
public class EduQuestionAnalysis extends Model<EduQuestionAnalysis> {
    private static final long serialVersionUID = 1L;

    private String id;

    @TableField("question_base_id")
    private String questionBaseId;

    @TableField("analysis")
    private String analysis;
    /**
     * 状态：0，正常。
     */
    @TableField("status")
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionBaseId() {
        return questionBaseId;
    }

    public void setQuestionBaseId(String questionBaseId) {
        this.questionBaseId = questionBaseId;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EduQuestionAnalysis{" +
                "id='" + id + '\'' +
                ", questionBaseId='" + questionBaseId + '\'' +
                ", analysis='" + analysis + '\'' +
                ", status=" + status +
                '}';
    }
}
