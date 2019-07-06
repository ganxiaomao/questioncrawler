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
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;
import java.util.*;
import java.util.List;

public class ImageUtils {

    private static final String cosSecretId = "AKIDseBWckUNo1rU1IDVM43qNjxe6IEubTij";
    private static final String cosSecretKey = "XUIqIdvxk27yZNbeRcBYi4FCRbZHkwZ1";
    private static final String cosBucketName = "edu1958-1257912152";
    private static final String cosRegion = "ap-chengdu";
    private static final COSCredentials cred = new BasicCOSCredentials(cosSecretId,cosSecretKey);
    private static final ClientConfig clientConfig = new ClientConfig(new Region(cosRegion));
    private static final COSClient cosClient = new COSClient(cred,clientConfig);
    public static final int IMAGEMAXSIZE = 1024*1024;

    public static String getSmallImageBytes(String url,String keyPre){
        String res = "";
        try {
            int suffix = url.lastIndexOf(".");
            int length = url.length();
            String imageSuffix = url.substring(suffix+1,length);
            String contentType = getcontentType(imageSuffix);
            byte[] bytes = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).execute().bodyAsBytes();
            res = cosUpload(bytes,url,imageSuffix,contentType,keyPre);
            //IOUtils.write(bytes, new FileOutputStream("C:\\Users\\icega\\Documents\\workspace\\11"+imageSuffix));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getLargeImageStream(String url){
        String res = "";
        try {
            BufferedInputStream bufferedInputStream = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).maxBodySize(3000000).execute().bodyStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 从html中提取出img标签的src图片地址，转换成md5--url的形式返回
     * 将html中的img的src替换成md5
     * @param html
     * @return 下载并替换src后的html
     */
    public static String extractImgByMd5(String html,String keyPre){
        String res = "";
        if(html != null){
            Map<String,String> urlMd5 = new HashMap<>();
            Document doc = Jsoup.parse(html);
            Elements eles = doc.select("img");
            Iterator<Element> it = eles.iterator();
            while(it.hasNext()){
                Element ele = it.next();
                String src = ele.attr("src");
                String url = "";
                //有些src可能是base64的
                if(src.contains("data:image")){
                    int index = src.lastIndexOf("data:image");
                    url = src.substring(index);
                }else{
                    url = getSmallImageBytes(src,keyPre);
                }
                //String md5 = DigestUtils.md5Hex(src);
                //将md5替换src的值
                if(url != null && !url.isEmpty())
                    ele.attr("src",url);
                //urlMd5.put(md5,src);
            }
            res = doc.body().html();//直接doc.html会自动添加一些其他标签
        }
        return res;
    }

    /**
     * 将文件上传到腾讯云的cos对象存储
     * @param fileByte
     * @param fileName
     * @param suffix
     * @param contentType
     * @return
     */
    public static String cosUpload(byte[] fileByte, String fileName, String suffix, String contentType, String keyPre){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileByte.length);
        objectMetadata.setContentType(contentType);
        String key = keyPre+createCosKey(fileName,suffix);
        InputStream input = new ByteArrayInputStream(fileByte);
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosBucketName,key,input,objectMetadata);
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        String etag = putObjectResult.getETag();
        String url = "https://edu1958-1257912152.cos.ap-chengdu.myqcloud.com/"+key;
        return url;
    }

    /**
     * 生成coskey，其实就用来做cos上的文件名
     * @param fileName
     * @param suffix
     * @return
     */
    public static String createCosKey(String fileName, String suffix){
        String md5 = DigestUtils.md5Hex(fileName);
        return md5+"."+suffix;
    }

    public static boolean hasImage(String html){
        Document doc = Jsoup.parse(html);
        Elements eles = doc.select("img");
        if(eles != null && eles.size()>0)
            return true;
        return false;
    }

    public static void closeCos(){
        cosClient.shutdown();
    }

    /**
     * Description: 判断Cos服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
                || filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    public static void batchConvert2Sizes(String srcFilePath, String type,String descFilePath) {
        try{
            File tempFile = new File(srcFilePath);
            if(tempFile.length()>IMAGEMAXSIZE){
                System.out.println("sss");
                BufferedImage src = ImageIO.read(tempFile); // 读入文件
                int width = src.getWidth(); // 得到源图宽
                int height = src.getHeight(); // 得到源图长

                double sc = (double)1700/(double)width;


                width = 480;//(int)((double)width * sc);
                height = 800;//(int)((double)height * sc);

                Image image = src.getScaledInstance(width, height,
                        Image.SCALE_DEFAULT);
                BufferedImage tag = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(image, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                FileOutputStream out = new FileOutputStream(descFilePath);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);
                out.close();
                //ImageIO.write(tag, type, new File(descFilePath));// 输出到文件流
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String checkImageFileType(String extName) {

        if ("jpg".equalsIgnoreCase(extName))
            return "jpg";
        if ("gif".equalsIgnoreCase(extName))
            return "gif";
        if ("bmp".equalsIgnoreCase(extName))
            return "bmp";
        if ("jpeg".equalsIgnoreCase(extName))
            return "jpeg";
        if ("png".equalsIgnoreCase(extName))
            return "jpeg";
        return null;
    }

    public static void main(String[] args){
        String fileName = "C:/Users/icega/Documents/workspace/套包/应用/安卓-魔方/1.jpg";
        String desc = "C:/Users/icega/Documents/workspace/套包/应用/安卓-魔方";
        String fileExtName = fileName.substring(
                        (fileName.lastIndexOf(".") + 1), fileName.length());
        String type = checkImageFileType(fileExtName);
        batchConvert2Sizes(fileName, type,desc);
    }
}
