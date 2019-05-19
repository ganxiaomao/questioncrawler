package com.icegan.edu.questioncrawler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icegan.edu.questioncrawler.dao.ImageCrawlJobMapper;
import com.icegan.edu.questioncrawler.model.ImageCrawlJob;
import com.icegan.edu.questioncrawler.service.IImageCrawlJobService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ImageCrawlJobServiceImpl extends ServiceImpl<ImageCrawlJobMapper, ImageCrawlJob> implements IImageCrawlJobService {
    private static final Logger logger = LogManager.getLogger(ImageCrawlJobServiceImpl.class);

    @Override
    public boolean batchSaveOrgImgUrls(List<String> orgImgUrls) {
        List<ImageCrawlJob> jobs = new ArrayList<>();
        for(String url : orgImgUrls){
            ImageCrawlJob job = new ImageCrawlJob();
            job.setMd5(DigestUtils.md5Hex(url));
            job.setOrgImgUrl(url);
            job.setStatus(1);//图片待下载
            jobs.add(job);
        }
        if(!jobs.isEmpty())
            this.saveBatch(jobs);
        return true;
    }

    @Override
    public List<ImageCrawlJob> selectDatasByStatusAndLimitAndOffset(int status, int limit, int offset) {
        QueryWrapper<ImageCrawlJob> queryWrapper = new QueryWrapper<>();
        List<ImageCrawlJob> jobs = this.baseMapper.selectList(queryWrapper.eq("status",status).last("limit "+offset+","+limit));
        return jobs;
    }

    @Override
    public boolean batchUpdateStatusByIds(int status, List<String> ids) {
        UpdateWrapper<ImageCrawlJob> updateWrapper = new UpdateWrapper<>();
        this.baseMapper.update(null, updateWrapper.set("status",status).in("id",ids));
        return true;
    }
}
