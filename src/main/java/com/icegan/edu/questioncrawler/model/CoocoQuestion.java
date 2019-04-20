package com.icegan.edu.questioncrawler.model;

import lombok.Data;

import java.io.Serializable;


public class CoocoQuestion implements Serializable {
    private String question;//题目
    private String answer;//解答
    private String from;//来源
    private String difficult;//难度
    private String answerId;//答案的id
    private String type;//题目类型
    private String knowlege;//知识点
    private String originFrom;//来源网站

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKnowlege() {
        return knowlege;
    }

    public void setKnowlege(String knowlege) {
        this.knowlege = knowlege;
    }

    public String getOriginFrom() {
        return originFrom;
    }

    public void setOriginFrom(String originFrom) {
        this.originFrom = originFrom;
    }

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
                ", originFrom='" + originFrom + '\'' + "\n" +
                '}';
    }
}
