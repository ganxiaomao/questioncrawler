package com.icegan.edu.questioncrawler.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringUtils {
    private static final Logger logger = LogManager.getLogger(StringUtils.class);

    public static int convertString2Int(String str){
        int result = -1;
        if(str != null && !str.isEmpty()){
            //
            result = Integer.parseInt(str);
        }
        return result;
    }

    public static void main(String[] args){
        String str = "myobj = { label: \"第一节 圆 \", id:\"187\" } ; ";
        String str2 = "ahmedDocs = new YAHOO.widget.TextNode(\"第二十四章　圆\", ahmedDocsRootOne, true);";
        if(str.contains("myobj =")){
            int left = str.indexOf("{");
            int right = str.indexOf("}");
            String res = str.substring(left,right+1);
            //res = res.replaceAll(" ","");
            JSONObject jo = JSON.parseObject(res);
            System.out.println(res+"\n label="+jo.getString("label")+"\n id="+jo.getString("id"));
        }

        if(str2.contains("ahmedDocs =")){
            int left = str2.indexOf("\"");
            int right = str2.indexOf(",");
            String res = str2.substring(left+1, right-1);
            System.out.println(res);
        }
    }
}
