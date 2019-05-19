package com.icegan.edu.questioncrawler.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ImageUtils {

    private static final String cosSecretId = "AKIDseBWckUNo1rU1IDVM43qNjxe6IEubTij";
    private static final String cosSecretKey = "XUIqIdvxk27yZNbeRcBYi4FCRbZHkwZ1";
    private static final String cosBucketName = "edu1958-1257912152";
    private static final String cosRegion = "ap-chengdu";

    public static void getSmallImageBytes(String url){
        //
        try {
            int suffix = url.lastIndexOf(".");
            int length = url.length();
            String imageSuffix = url.substring(suffix,length);
            byte[] bytes = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).execute().bodyAsBytes();
            //IOUtils.write(bytes, new FileOutputStream("C:\\Users\\icega\\Documents\\workspace\\11"+imageSuffix));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getLargeImageStream(String url){
        try {
            BufferedInputStream bufferedInputStream = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).maxBodySize(3000000).execute().bodyStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从html中提取出img标签的src图片地址，转换成md5--url的形式返回
     * 将html中的img的src替换成md5
     * @param html
     * @return 下载并替换src后的html
     */
    public String extractImgByMd5(String html){
        String res = "";
        if(html != null){
            Map<String,String> urlMd5 = new HashMap<>();
            Document doc = Jsoup.parse(html);
            Elements eles = doc.select("img");
            Iterator<Element> it = eles.iterator();
            while(it.hasNext()){
                Element ele = it.next();
                String src = ele.attr("src");
                String md5 = DigestUtils.md5Hex(src);
                //将md5替换src的值
                ele.attr("src",md5);
                urlMd5.put(md5,src);
            }
            res = doc.html();
        }
        return res;
    }

    public String cosUpload(byte[] fileByte, String fileName, String suffix, String contentType){
        COSCredentials cred = new BasicCOSCredentials(cosSecretId,cosSecretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(cosRegion));
        COSClient cosClient = new COSClient(cred,clientConfig);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileByte.length);
        objectMetadata.setContentType(contentType);
        String key = "question/"+createCosKey(fileName,suffix);
        InputStream input = new ByteArrayInputStream(fileByte);
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosBucketName,key,input,objectMetadata);
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        String etag = putObjectResult.getETag();
        String url = "https://edu1958-1257912152.cos.ap-chengdu.myqcloud.com/"+key;
        cosClient.shutdown();
        return url;
    }

    public String createCosKey(String fileName, String suffix){
        String md5 = DigestUtils.md5Hex(fileName);
        return md5+"."+suffix;
    }

    public static void main(String[] args){
        String url = "http://czsx.cooco.net.cn/files/down/test/2019/04/28/02/2019042802455141464225.files/image019.gif";
        String md5 = DigestUtils.md5Hex(url);
        String md51 = DigestUtils.md5Hex(url);
        System.out.println(md5+","+md51+",length="+url.length());
    }
}
