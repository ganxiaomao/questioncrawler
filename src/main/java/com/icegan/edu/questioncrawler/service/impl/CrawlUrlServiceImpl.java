package com.icegan.edu.questioncrawler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icegan.edu.questioncrawler.dao.CrawlUrlMapper;
import com.icegan.edu.questioncrawler.model.CrawlUrl;
import com.icegan.edu.questioncrawler.service.ICrawlUrlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CrawlUrlServiceImpl extends ServiceImpl<CrawlUrlMapper, CrawlUrl> implements ICrawlUrlService {
}
