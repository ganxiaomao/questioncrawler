package com.icegan.edu.questioncrawler.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HttpUtils {
    private static final Logger logger = LogManager.getLogger(HttpUtils.class);

    public static String httpPost(String url, Map<String,String> params) throws IOException {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "text/html; charset=utf-8");
        List<NameValuePair> nvps = new ArrayList<>();
        if(params != null){
            Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,String> next = it.next();
                nvps.add(new BasicNameValuePair(next.getKey(),next.getValue()));
            }
        }
        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            response = httpClient.execute(httpPost);
            logger.info(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            result= EntityUtils.toString(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
        return result;
    }

    public static String httpGet(String url) throws IOException {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result= EntityUtils.toString(entity,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
        return result;
    }

    public static void main(String[] args){
        String url = "http://czwl.cooco.net.cn/testpage/1/";
        Map<String,String> params = new HashMap<>();
        params.put("lessonid","223");
        params.put("difficult","0");
        params.put("type","0");
        params.put("orderby","1");
        try {
            String html = httpPost(url,params);
            System.out.println(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
