package com.icegan.edu.questioncrawler.util;

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
}
