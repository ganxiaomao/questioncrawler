package com.icegan.edu.questioncrawler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icegan.edu.questioncrawler.dao.CrawlUrlMapper;
import com.icegan.edu.questioncrawler.model.CrawlUrl;
import com.icegan.edu.questioncrawler.service.ICrawlUrlService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CrawlUrlServiceImpl extends ServiceImpl<CrawlUrlMapper, CrawlUrl> implements ICrawlUrlService {
    private static final Logger logger = LogManager.getLogger(CrawlUrlServiceImpl.class);


    @Override
    public List<CrawlUrl> selectByPageAndStatus(int pageNum, int pageSize, List<Integer> statuss) {
        Page<CrawlUrl> page = new Page<>(pageNum, pageSize);
        QueryWrapper<CrawlUrl> queryWrapper = new QueryWrapper<>();
        this.baseMapper.selectPage(page,queryWrapper.in("status",statuss));
        return page.getRecords();
    }

    @Override
    public CrawlUrl selectOneByStatus(Integer... status) {
        QueryWrapper<CrawlUrl> queryWrapper = new QueryWrapper<>();
        List<CrawlUrl> datas = this.baseMapper.selectList(queryWrapper.in("status",status).last("limit 1"));
        CrawlUrl cu = null;
        if(datas != null && !datas.isEmpty())
            cu = datas.get(0);
        return cu;
    }
}
