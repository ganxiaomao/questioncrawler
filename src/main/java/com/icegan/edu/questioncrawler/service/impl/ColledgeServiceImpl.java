package com.icegan.edu.questioncrawler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.icegan.edu.questioncrawler.dao.ColledgeMapper;
import com.icegan.edu.questioncrawler.model.Colledge;
import com.icegan.edu.questioncrawler.service.IColledgeService;
import com.icegan.edu.questioncrawler.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ColledgeServiceImpl extends ServiceImpl<ColledgeMapper, Colledge> implements IColledgeService {
    private static final Logger logger = LogManager.getLogger(ColledgeServiceImpl.class);

    @Override
    public List<Colledge> crawlColledgeByUrl(String url) {
        List<Colledge> colledges = Lists.newArrayList();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("tbody[id=lists] tr");
            for(Element element : elements){
                Elements tds = element.select("td");
                int size = tds.size();
                if(size == 8){
                    Colledge colledge = arrangeColledgeFromElements(tds);
                    if(colledge != null)
                        colledges.add(colledge);
                }
            }
        } catch (IOException e) {
            logger.info("error:", e.getCause());
        }
        return colledges;
    }

    /**
     * 根据表格的td元素，整理出每个高校的基本信息
     * @param elements
     * @return
     */
    private Colledge arrangeColledgeFromElements(Elements elements){
        Colledge colledge = new Colledge();
        Element school_badge_td = elements.get(0);//校徽td
        String schoolBadge = getSchoolBadge(school_badge_td,true);

        Element school_name_td = elements.get(1);//校名td
        Map<String,String> map = getSchoolName(school_name_td);
        String schoolName = map.get("name");
        String schoolDetailUrl = map.get("schoolDetailUrl");

        Element school_province_td = elements.get(2);//院校省份td
        String schoolProvince = getElementText(school_province_td);

        Element school_natrue_td = elements.get(3);//学校性质td
        String schoolNature = getElementText(school_natrue_td);

        Element school_type_td = elements.get(4);//学校类型td
        String schoolType = getElementText(school_type_td);

        Element school_level_td = elements.get(5);//学历层次td
        String schoolLevel = getElementText(school_level_td);

        Element school_attribute_td = elements.get(6);//学校属性td
        String schoolAttribute = getSchoolAttributes(school_attribute_td);

        colledge.setSchoolBadge(schoolBadge);
        colledge.setSchoolName(schoolName);
        colledge.setSchoolDetailUrl(schoolDetailUrl);
        colledge.setSchoolProvince(schoolProvince);
        colledge.setSchoolNature(schoolNature);
        colledge.setSchoolType(schoolType);
        colledge.setSchoolLevel(schoolLevel);
        colledge.setSchoolAttribute(schoolAttribute);

        return colledge;
    }

    /**
     * 从校徽元素中获取校徽图片的url地址
     * @param badgeEle
     * @param download 是否下载
     * @return
     */
    private String getSchoolBadge(Element badgeEle,boolean download){
        String badge = "http://gaokao.xdf.cn/college/images/china/defaultSchool.png";//默认值
        if(badgeEle != null){
            Element imgEle = badgeEle.selectFirst("img");
            if(imgEle != null){
                String src = imgEle.attr("src");
                if(!Strings.isNullOrEmpty(src)){
                    badge = src;
                    if(download){
                        badge = ImageUtils.getSmallImageBytes(src, "colledge/");
                    }
                }
            }
        }
        return badge;
    }

    /**
     * 从学校名称元素中获取校名和学校详情地址
     * @param nameEle
     * @return schoolDetailUrl，学校详情地址；name，学校名字
     */
    private Map<String,String> getSchoolName(Element nameEle){
        Map<String,String> map = Maps.newHashMap();
        if(nameEle != null){
            Element aEle = nameEle.selectFirst("a");
            if(aEle != null){
                String schoolDetailUrl = aEle.attr("href");
                String name = aEle.text();
                map.put("schoolDetailUrl", schoolDetailUrl);
                map.put("name", name);
            }
        }
        return map;
    }

    /**
     * 用于只需要获取元素文本的元素
     * @param ele
     * @return
     */
    private String getElementText(Element ele){
        String text = "";
        if(ele != null){
            text = ele.text().trim();
        }
        return text;
    }

    /**
     * 从学校属性元素中，获取学校的属性。
     * @param typeEle
     * @return 学校属性可能有多个，属性见以","英文逗号分隔
     */
    private String getSchoolAttributes(Element typeEle){
        String type = "";
        if(typeEle != null){
            Elements spanEles = typeEle.select("span");
            if(spanEles != null){
                for(Element span : spanEles){
                    String text = getElementText(span);
                    if(type.isEmpty())
                        type += text;
                    else
                        type += ","+text;
                }
            }
        }
        return type;
    }
}
