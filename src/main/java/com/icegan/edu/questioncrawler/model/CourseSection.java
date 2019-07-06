package com.icegan.edu.questioncrawler.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

@TableName("biz_edu_course_section")
public class CourseSection extends Model<CourseSection> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 父章节id
     */
    @TableField("parent_id")
    private String parentId;
    /**
     * 课程id
     */
    @TableField("course_id")
    private String courseId;
    /**
     * 层级
     */
    private Integer ilevel;
    /**
     * 排序
     */
    @TableField("sort_str")
    private String sortStr;
    /**
     * 章节名称
     */
    private String name;
    /**
     * 章节内容
     */
    private String contect;
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
     * 创建者
     */
    private String creator;
    /**
     * 更新人
     */
    private String updater;

    @TableField("cooco_id")
    private String coocoId;

    private Integer status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getIlevel() {
        return ilevel;
    }

    public void setIlevel(Integer ilevel) {
        this.ilevel = ilevel;
    }

    public String getSortStr() {
        return sortStr;
    }

    public void setSortStr(String sortStr) {
        this.sortStr = sortStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContect() {
        return contect;
    }

    public void setContect(String contect) {
        this.contect = contect;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getCoocoId() {
        return coocoId;
    }

    public void setCoocoId(String coocoId) {
        this.coocoId = coocoId;
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
        return "CourseSection{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", courseId=" + courseId +
                ", ilevel=" + ilevel +
                ", sortStr=" + sortStr +
                ", name=" + name +
                ", contect=" + contect +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", creator=" + creator +
                ", updater=" + updater +
                ", coocoId=" + coocoId +
                ", status=" + status +
                "}";
    }
}
