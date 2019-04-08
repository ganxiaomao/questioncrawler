package com.icegan.edu.questioncrawler.service.impl;

import com.icegan.edu.questioncrawler.model.CoocoQuestion;
import com.icegan.edu.questioncrawler.service.ICrawlerService;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CrawlerServiceImpl implements ICrawlerService {
    private static final Logger logger = LogManager.getLogger(CrawlerServiceImpl.class);
    @Override
    public String coocoCrawler() throws IOException {
        //
        String html = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://czsx.cooco.net.cn/testpage/1/?lessonid=111&difficult=0&type=0&orderby=1");
        httpPost.setHeader("Content-type", "text/html; charset=utf-8");
        List<NameValuePair> nvps = new ArrayList<>();
//        nvps.add(new BasicNameValuePair("lessonid","111"));
//        nvps.add(new BasicNameValuePair("difficult","0"));
//        nvps.add(new BasicNameValuePair("type","0"));
//        nvps.add(new BasicNameValuePair("orderby","1"));

        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            response = httpClient.execute(httpPost);
            logger.info(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            html=EntityUtils.toString(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(response != null)
                response.close();
        }
        html = parseHtml(html);
        return html;
    }

    String parseHtml(String html){
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

            Element stem = ps.get(0);//获取题干
            coocoQuestion.setQuestion(stem.html());
            if(ps.size()>1){
                Element choice = ps.get(1);//获取选项
                coocoQuestion.setQuestion(stem.html()+choice.html());
            }


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
        }
        logger.info("题目数量="+elements.size());
        return result;
    }
}
