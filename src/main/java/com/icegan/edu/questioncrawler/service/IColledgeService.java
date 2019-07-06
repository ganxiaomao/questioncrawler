package com.icegan.edu.questioncrawler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icegan.edu.questioncrawler.model.Colledge;

import java.util.List;

public interface IColledgeService extends IService<Colledge> {

    public List<Colledge> crawlColledgeByUrl(String url);
}
