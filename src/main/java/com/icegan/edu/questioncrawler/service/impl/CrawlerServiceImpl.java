package com.icegan.edu.questioncrawler.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icegan.edu.questioncrawler.constant.Constants;
import com.icegan.edu.questioncrawler.model.CoocoQuestion;
import com.icegan.edu.questioncrawler.model.CourseSection;
import com.icegan.edu.questioncrawler.model.CrawlUrl;
import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;
import com.icegan.edu.questioncrawler.service.ICourseSectionService;
import com.icegan.edu.questioncrawler.service.ICrawlUrlService;
import com.icegan.edu.questioncrawler.service.ICrawlerService;
import com.icegan.edu.questioncrawler.util.HttpUtils;
import com.icegan.edu.questioncrawler.util.StringUtils;
import com.icegan.edu.questioncrawler.vo.CourseSectionVo;
import org.apache.http.ContentTooLongException;
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
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CrawlerServiceImpl implements ICrawlerService {
    private static final Logger logger = LogManager.getLogger(CrawlerServiceImpl.class);

    @Autowired
    private ICrawlUrlService iCrawlUrlService;
    @Autowired
    private ICourseSectionService iCourseSectionService;

    @Override
    public List<EduQuestionBankBase> coocoCrawler(String url, String grade, String subject, String courseId, String courseSectionId, String originFrom) {
        //
        List<EduQuestionBankBase> eduQuestionBankBases = new ArrayList<>();
        try {
            String html = HttpUtils.httpPost(url,null);
            List<CoocoQuestion> coocoQuestions = parseHtml(html, originFrom);
            eduQuestionBankBases = convertCoocoQuestion2Edu(coocoQuestions, grade, subject, courseId, courseSectionId, originFrom);
        } catch (Exception e) {
            eduQuestionBankBases = null;//失败
            e.printStackTrace();
        }
        return eduQuestionBankBases;
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
    public String coocoCrawlPage(String grade,String subject, String knowlegeId, String coocoId, String courseId) {
        String coocograde = Constants.grade2CoocoMap.get(grade);
        String coocosubject = Constants.subject2CoocoMap.get(subject);
        String originFrom = "http://"+coocograde+coocosubject+".cooco.net.cn";//最后不以/结尾，是为了图片地址完善方便
        String url = originFrom+"/testpage/1/";
        logger.info("===开始分析待抓取题库的页面地址："+url);
        Map<String,String> params = new HashMap<>();
        params.put("lessonid",coocoId);
        params.put("difficult","0");
        params.put("type","0");
        params.put("orderby","1");
        String html = "";
        try {
            html = HttpUtils.httpPost(url,params);
            int pageNum = paresePageNum(html);
            List<CrawlUrl> pageUrls = new ArrayList<>();
            if(pageNum > 0){
                for(int i=0; i<pageNum; i++){
                    CrawlUrl crawlUrl = new CrawlUrl();
                    crawlUrl.setGrade(grade);
                    crawlUrl.setStatus(0);
                    crawlUrl.setSubject(subject);
                    crawlUrl.setUrl("http://"+coocograde+coocosubject+".cooco.net.cn/testpage/"+i+"/");
                    crawlUrl.setKnowledgeId(knowlegeId);
                    crawlUrl.setCoocoId(coocoId);
                    crawlUrl.setCourseId(courseId);
                    crawlUrl.setOriginFrom(originFrom);
                    pageUrls.add(crawlUrl);
                }
            }
            if(pageUrls.size()>0){
                logger.info("===coocoId="+coocoId+",总共待抓取题库的地址有"+pageUrls.size()+"条");
                iCrawlUrlService.saveBatch(pageUrls);
            }else{
                logger.info("coocoId="+coocoId+"没有获取到数据");
            }
        } catch (Exception e) {
            html = "error";
            logger.info(e);
        }
        return html;
    }

    @Override
    public String coocoAnalysisCrawl(String originFrom, String answerId) {
        String res = "";
        try{
            String url = originFrom+"/answerdetail/"+answerId+"/";
            logger.info("===开始抓取url="+url+"的答案解析====");
            String html = HttpUtils.httpPost(url,null);
            if(!html.isEmpty()){
                res = parseAnalysis(originFrom, html);
            }
        }catch (Exception e){
            logger.info(e);
            res = null;
        }
        return res;
    }

    @Override
    public String coocoKnowledgeCrawl(String jsName, String courseId) {
        String url = "http://img.cooco.net.cn/site_media/"+jsName+".js";
        logger.info("===开始抓取知识点，地址为："+url);
        String html = "";
        try {
            html = HttpUtils.httpGet(url);
            InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(html.getBytes()),"utf-8");
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            String lastKey = "";
            Map<String, List<JSONObject>> knowledgeMap = new HashMap<>();
            while((line=br.readLine()) != null){
                if(line.contains("ahmedDocs =")){
                    int left = line.indexOf("\"");
                    int right = line.indexOf(",");
                    lastKey = line.substring(left+1, right-1);
                    knowledgeMap.put(lastKey, new ArrayList<>());
                }else if(line.contains("myobj =")){
                    int left = line.indexOf("{");
                    int right = line.indexOf("}");
                    String res = line.substring(left,right+1);
                    JSONObject jo = JSON.parseObject(res);
                    List<JSONObject> value = knowledgeMap.get(lastKey);
                    value.add(jo);
                }
            }
            br.close();
            reader.close();
            //知识点处理
            Iterator<Map.Entry<String, List<JSONObject>>> it = knowledgeMap.entrySet().iterator();
            parseKnowledge(it, courseId);
        } catch (IOException e) {
            logger.info("error:",e.getCause());
        }
        logger.info("===知识点抓取完毕===");
        return html;
    }

    int paresePageNum(String html){
        Document doc = Jsoup.parse(html);
        Elements pages = doc.select("p.pagenav a.page-numbers");
        int pageNum = -1;
        int size = pages.size();
        if(size > 0){
            Element lastPage = pages.last();
            String text = lastPage.text();
            pageNum = StringUtils.convertString2Int(text);
        }
        return pageNum;
    }

    List<CoocoQuestion> parseHtml(String html, String originFrom){
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
                img.attr("src",originFrom+src);
        }
        result = doc.html();

        Elements elements = doc.select("ul[id=test]").select("li");
        Iterator<Element> it = elements.iterator();
        while(it.hasNext()){
            try{
                CoocoQuestion coocoQuestion = new CoocoQuestion();
                Element element = it.next();

                Elements txts = element.select("div[class=txt]");
                Elements titles = txts.select("div[class=title]");
                Elements ps = txts.select("p");
                Elements spans = element.select("div[class=bottom]").select("span");


                Element divImg = titles.get(0).select("div").get(0);//取难易度
                Element from = titles.get(1).select("span").get(0);//获取题目来源
                coocoQuestion.setFrom(from.text());

                String stem = extractStem(txts.first());//获取题干
                coocoQuestion.setQuestion(stem);
                logger.info(txts.html());

                Element answer = element.select("div[class=daan]").get(0);//获取答案id
                String[] splits = answer.attr("id").split("-");
                coocoQuestion.setAnswerId(splits[1]);

                Element questionType = spans.get(1);//获取题目类型
                coocoQuestion.setType(questionType.text().replaceAll("&nbsp;",""));

                //Element knowlege = spans.get(2);//获取知识点
                //coocoQuestion.setKnowlege(knowlege.text());

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
                //logger.info("题目信息="+coocoQuestion.toString());
                coocoQuestions.add(coocoQuestion);
            }catch (Exception e){
                logger.info("error",e.getCause());
            }

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
            String classValue = e.attr("class");
            boolean attr = classValue.contains("title") || classValue.contains("bottom");
            if(!attr ){
                stems.append(e.toString());
            }
            //if((tagName.equals("div")&&!e.hasAttr("class"))||(!tagName.equals("div"))){
                //stems.append(e.html());
            //}
        }
        return stems.toString();
    }

    public List<EduQuestionBankBase> convertCoocoQuestion2Edu(List<CoocoQuestion> coocoQuestions, String gradeCode, String subjectCode, String courseId, String courseSectionId, String originFrom){
        Date now = new Date();
        List<EduQuestionBankBase> eduQuestionBankBases = new ArrayList<>();
        for(CoocoQuestion cq : coocoQuestions){
            EduQuestionBankBase eq = new EduQuestionBankBase();
            eq.setAnswerId(cq.getAnswerId());
            eq.setChapterId(courseSectionId);
            eq.setCourseId(courseId);

            eq.setCreateBy(1);//创建人：系统管理员
            eq.setCreateTime(now);
            eq.setUpdateTime(now);
            eq.setDifficult(Constants.difficultMap.get(cq.getDifficult()));
            eq.setGrade(gradeCode);
            eq.setStatus(Constants.cooco_crawl_question_status_stem_ok);//状态为stem抓取成功
            eq.setStem(cq.getQuestion());
            eq.setSubject(subjectCode);
            eq.setType(Constants.questiontypeMap.get(cq.getType()));
            eq.setOriginFrom(originFrom);

            eduQuestionBankBases.add(eq);
        }
        return eduQuestionBankBases;
    }

    public String parseAnalysis(String originFrom,String html){
        String res = "";
        Document doc = Jsoup.parse(html);
        Elements allImgs = doc.select("img");
        Iterator<Element> allit = allImgs.iterator();
        while(allit.hasNext()){
            Element img = allit.next();
            String src = img.attr("src");
            if(!src.contains("http"))
                img.attr("src",originFrom+src);
        }
        res = doc.html();
        return res;
    }

    public void parseKnowledge(Iterator<Map.Entry<String, List<JSONObject>>> it, String courseId){
        Date now = new Date();
        List<CourseSection> children = new ArrayList<>();
        while(it.hasNext()){
            Map.Entry<String, List<JSONObject>> entry = it.next();
            String key = entry.getKey();
            List<JSONObject> value = entry.getValue();
            //父节点一个个保存
            CourseSection parent = arrange2CourseSection(now, null, key, courseId,"");
            iCourseSectionService.save(parent);
            String parentId = parent.getId();
            //生成子节点
            for(JSONObject jo : value){
                CourseSection child = arrange2CourseSection(now, parentId, jo.getString("label"), courseId, jo.getString("id"));
                children.add(child);
            }

        }
        iCourseSectionService.saveBatch(children);
    }

    private CourseSection arrange2CourseSection(Date time, String parentId, String name, String courseId, String coocoId){
        CourseSection cs = new CourseSection();
        cs.setCourseId(courseId);
        cs.setCreateTime(time);
        cs.setCreator("admin");//

        cs.setName(name);
        cs.setSortStr("002");
        cs.setUpdater("admin");
        cs.setUpdateTime(time);
        if(parentId != null && !parentId.isEmpty()){
            cs.setIlevel(1);//第二级
            cs.setParentId(parentId);
            cs.setStatus(1);//第二级需要抓取
            cs.setCoocoId(coocoId);
        }else{
            cs.setIlevel(0);//第一级
            cs.setStatus(0);//第一级不需要抓取
        }
        return cs;
    }
}
