package com.icegan.edu.questioncrawler.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

@TableName("biz_colledge")
public class Colledge extends Model<Colledge> {
    private static final long serialVersionUID = 1L;

    @TableId(value="ID",type= IdType.UUID)
    private String id;

    /**
     * 校徽地址
     */
    @TableField("school_badge")
    private String schoolBadge;
    /**
     * 校名
     */
    @TableField("school_name")
    private String schoolName;
    /**
     * 所属省份
     */
    @TableField("school_province")
    private String schoolProvince;
    /**
     * 学校性质：公办、
     */
    @TableField("school_natrue")
    private String schoolNature;
    /**
     * 学校类型：综合、理工、农林等
     */
    @TableField("school_type")
    private String schoolType;
    /**
     * 学历层次：普通本科、独立学院、高职高专等
     */
    @TableField("school_level")
    private String schoolLevel;
    /**
     * 学校属性：985工程、211工程、一流大学建设高校等
     */
    @TableField("school_attribute")
    private String schoolAttribute;
    /**
     * 学校详情地址，暂时不抓，留待以后
     */
    @TableField("school_detail_url")
    private String schoolDetailUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolBadge() {
        return schoolBadge;
    }

    public void setSchoolBadge(String schoolBadge) {
        this.schoolBadge = schoolBadge;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolProvince() {
        return schoolProvince;
    }

    public void setSchoolProvince(String schoolProvince) {
        this.schoolProvince = schoolProvince;
    }

    public String getSchoolNature() {
        return schoolNature;
    }

    public void setSchoolNature(String schoolNature) {
        this.schoolNature = schoolNature;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public String getSchoolAttribute() {
        return schoolAttribute;
    }

    public void setSchoolAttribute(String schoolAttribute) {
        this.schoolAttribute = schoolAttribute;
    }

    public String getSchoolDetailUrl() {
        return schoolDetailUrl;
    }

    public void setSchoolDetailUrl(String schoolDetailUrl) {
        this.schoolDetailUrl = schoolDetailUrl;
    }
}
