package com.icegan.edu.questioncrawler.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

@TableName("biz_edu_question_stem")
public class EduQuestionStem extends Model<EduQuestionStem> {
    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("question_base_id")
    private String questionBaseId;
    @TableField("stem")
    private String stem;
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

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
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
        return "EduQuestionStem{" +
                "id='" + id + '\'' +
                ", questionBaseId='" + questionBaseId + '\'' +
                ", stem='" + stem + '\'' +
                ", status=" + status +
                '}';
    }
}
