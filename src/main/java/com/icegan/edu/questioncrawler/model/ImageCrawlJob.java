package com.icegan.edu.questioncrawler.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

@TableName("biz_image_crawl_job")
public class ImageCrawlJob extends Model<ImageCrawlJob> {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("md5")
    private String md5;
    /**
     * 可圈可点网站图片地址
     */
    @TableField("org_img_url")
    private String orgImgUrl;
    /**
     * 下载到自己服务器后的图片地址
     */
    @TableField("rel_img_url")
    private String relImgUrl;
    /**
     * 状态：0，正常；1，图片待下载；
     */
    @TableField("status")
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getOrgImgUrl() {
        return orgImgUrl;
    }

    public void setOrgImgUrl(String orgImgUrl) {
        this.orgImgUrl = orgImgUrl;
    }

    public String getRelImgUrl() {
        return relImgUrl;
    }

    public void setRelImgUrl(String relImgUrl) {
        this.relImgUrl = relImgUrl;
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
        return "ImageCrawlJob{" +
                "id='" + id + '\'' +
                ", md5='" + md5 + '\'' +
                ", orgImgUrl='" + orgImgUrl + '\'' +
                ", relImgUrl='" + relImgUrl + '\'' +
                ", status=" + status +
                '}';
    }
}
