package com.icegan.edu.questioncrawler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icegan.edu.questioncrawler.model.CrawlUrl;

import java.util.List;

public interface ICrawlUrlService extends IService<CrawlUrl> {

    public List<CrawlUrl> selectByPageAndStatus(int pageNum, int pageSize, List<Integer> statuss);

    public CrawlUrl selectOneByStatus(Integer... status);

    public boolean updateStatusById(Integer status, String id);
}
