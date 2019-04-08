package com.icegan.edu.questioncrawler.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoocoQuestion implements Serializable {
    private String question;//题目
    private String answer;//解答
    private String from;//来源
    private String difficult;//难度
    private String answerId;//答案的id
    private String type;//题目类型
    private String knowlege;//知识点

    @Override
    public String toString() {
        return "CoocoQuestion{" + "\n" +
                "question='" + question + '\'' + "\n" +
                ", answer='" + answer + '\'' + "\n" +
                ", from='" + from + '\'' + "\n" +
                ", difficult='" + difficult + '\'' + "\n" +
                ", answerId='" + answerId + '\'' + "\n" +
                ", type='" + type + '\'' + "\n" +
                ", knowlege='" + knowlege + '\'' + "\n" +
                '}';
    }
}
