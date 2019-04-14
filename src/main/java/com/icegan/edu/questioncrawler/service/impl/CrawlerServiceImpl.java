package com.icegan.edu.questioncrawler.service.impl;

import com.icegan.edu.questioncrawler.model.CoocoQuestion;
import com.icegan.edu.questioncrawler.model.CrawlUrl;
import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;
import com.icegan.edu.questioncrawler.service.ICrawlUrlService;
import com.icegan.edu.questioncrawler.service.ICrawlerService;
import com.icegan.edu.questioncrawler.util.HttpUtils;
import com.icegan.edu.questioncrawler.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CrawlerServiceImpl implements ICrawlerService {
    private static final Logger logger = LogManager.getLogger(CrawlerServiceImpl.class);

    @Autowired
    private ICrawlUrlService iCrawlUrlService;

    @Override
    public int coocoCrawler(String url) {
        //
        int res = 1;//成功
        String html = "";
        try {
            html = HttpUtils.httpPost(url,null);
            List<CoocoQuestion> coocoQuestions = parseHtml(html);
        } catch (Exception e) {
            res = -1;//失败
            logger.info(e);
        }
        return res;
    }

    @Override
    public String coocoCrawlSubject(String subject) {
        String url = "http://"+subject+".cooco.net.cn/test/";
        String html = "";
        try {
            html = HttpUtils.httpGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    @Override
    public String coocoCrawlPage(String grade,String subject) {
        String url = "http://"+grade+subject+".cooco.net.cn/test/";
        String html = "";
        try {
            html = HttpUtils.httpGet(url);
            Document doc = Jsoup.parse(html);
            Elements pages = doc.select("p.pagenav a.page-numbers");
            int pageNum = -1;
            int size = pages.size();
            if(size > 0){
                Element lastPage = pages.last();
                String text = lastPage.text();
                pageNum = StringUtils.convertString2Int(text);
            }
            List<CrawlUrl> pageUrls = new ArrayList<>();
            if(pageNum > 0){
                for(int i=0; i<pageNum; i++){
                    CrawlUrl crawlUrl = new CrawlUrl();
                    crawlUrl.setGrade(grade);
                    crawlUrl.setStatus(0);
                    crawlUrl.setSubject(subject);
                    crawlUrl.setUrl("http://"+grade+subject+".cooco.net.cn/testpage/"+i+"/");
                    pageUrls.add(crawlUrl);
                }
            }
            iCrawlUrlService.saveBatch(pageUrls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    List<CoocoQuestion> parseHtml(String html){
        List<CoocoQuestion> coocoQuestions = new ArrayList<>();
        String result = "";
        //
        Document doc = Jsoup.parse(html);

        Elements allImgs = doc.select("img");
        Iterator<Element> allit = allImgs.iterator();
        while(allit.hasNext()){
            Element img = allit.next();
            String src = img.attr("src");
            if(!src.contains("http"))
                img.attr("src","http://czsx.cooco.net.cn"+src);
        }
        result = doc.html();

        Elements elements = doc.select("ul[id=test]").select("li");
        Iterator<Element> it = elements.iterator();
        while(it.hasNext()){
            CoocoQuestion coocoQuestion = new CoocoQuestion();
            Element element = it.next();

            Elements txts = element.select("div[class=txt]");
            Elements titles = txts.select("div[class=title]");
            Elements ps = txts.select("p");
            Elements spans = txts.select("div[class=bottom]").select("span");


            Element divImg = titles.get(0).select("div").get(0);//取难易度
            Element from = titles.get(1).select("span").get(0);//获取题目来源
            coocoQuestion.setFrom(from.text());

            String stem = extractStem(txts.first());//获取题干
            coocoQuestion.setQuestion(stem);


            Element answer = txts.select("div[class=daan]").get(0);//获取答案id
            String[] splits = answer.attr("id").split("-");
            coocoQuestion.setAnswerId(splits[1]);

            Element questionType = spans.get(1);//获取题目类型
            coocoQuestion.setType(questionType.text().replaceAll("&nbsp;",""));

            Element knowlege = spans.get(2);//获取知识点
            coocoQuestion.setKnowlege(knowlege.text());

            Elements imgs = divImg.select("img");
            Iterator<Element> imgit = imgs.iterator();

            int totalHard = 0;
            while(imgit.hasNext()){
                Element img = imgit.next();
                String src = img.attr("src");
                if(src.contains("nsts")){
                    totalHard+=1;
                }
            }
            //基础、容易，中等、偏难、很难
            String difficult = "";
            switch (totalHard){
                case 1:
                    difficult="基础";
                    break;
                case 2:
                    difficult="容易";
                    break;
                case 3:
                    difficult="中等";
                    break;
                case 4:
                    difficult="偏难";
                    break;
                case 5:
                    difficult="很难";
                    break;
            }
            coocoQuestion.setDifficult(difficult);
            logger.info("题目信息="+coocoQuestion.toString());
            coocoQuestions.add(coocoQuestion);
        }
        logger.info("题目数量="+coocoQuestions.size());
        return coocoQuestions;
    }

    public String extractStem(Element el){
        StringBuffer stems = new StringBuffer();//用来保存题干
        Elements children = el.children();
        Iterator<Element> it = children.iterator();
        while(it.hasNext()){
            Element e = it.next();
            String tagName = e.tagName();
            if((tagName.equals("div")&&!e.hasAttr("class"))||(!tagName.equals("div"))){
                //Pattern pattern = Pattern.compile(".*[A-Z]+.*[A-Z]+.*");
                //Matcher m = pattern.matcher(text);
                stems.append(e.html());
            }
        }
        return stems.toString();
    }

    public List<EduQuestionBankBase> convertCoocoQuestion2Edu(List<CoocoQuestion> coocoQuestions){
        List<EduQuestionBankBase> eduQuestionBankBases = new ArrayList<>();
        for(CoocoQuestion cq : coocoQuestions){
            EduQuestionBankBase eq = new EduQuestionBankBase();
            eduQuestionBankBases.add(eq);
        }
        return eduQuestionBankBases;
    }
}
