package com.icegan.edu.questioncrawler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icegan.edu.questioncrawler.model.ImageCrawlJob;

import java.util.List;

public interface IImageCrawlJobService extends IService<ImageCrawlJob> {

    public boolean batchSaveOrgImgUrls(List<String> orgImgUrls);

    public List<ImageCrawlJob> selectDatasByStatusAndLimitAndOffset(int status, int limit, int offset);

    public boolean batchUpdateStatusByIds(int status, List<String> ids);
}
