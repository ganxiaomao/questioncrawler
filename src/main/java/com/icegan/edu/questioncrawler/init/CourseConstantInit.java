package com.icegan.edu.questioncrawler.init;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icegan.edu.questioncrawler.constant.Constants;
import com.icegan.edu.questioncrawler.service.ICommonDsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import sun.misc.IOUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CourseConstantInit implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(CourseConstantInit.class);

    @Autowired
    private ICommonDsService iCommonDsService;

    @Value("classpath:data/difficult.json")
    Resource difficultRes;
    @Value("classpath:data/grade.json")
    Resource gradeRes;
    @Value("classpath:data/questiontype.json")
    Resource questiontypeRes;
    @Value("classpath:data/subject.json")
    Resource subjectRes;
    @Value("classpath:data/subject2cooco.json")
    Resource subject2coocoRes;
    @Value("classpath:data/grade2cooco.json")
    Resource grade2coocoRes;

    @Override
    public void run(String... args) throws Exception {
        logger.info("开始初始化知识点");
        Constants.csvMap.putAll(iCommonDsService.getAllCourseSection());
        int size = Constants.csvMap.size();
        logger.info("知识点信息初始化，总共条数为："+size);

        Constants.difficultMap.putAll(parseJson2Dict(loandJsonFile(difficultRes)));
        Constants.gradeMap.putAll(parseJson2Dict(loandJsonFile(gradeRes)));
        Constants.questiontypeMap.putAll(parseJson2Dict(loandJsonFile(questiontypeRes)));
        Constants.subjectMap.putAll(parseJson2Dict(loandJsonFile(subjectRes)));
        Constants.subject2CoocoMap.putAll(loadSubject2CoocoJsonFile(loandJsonFile(subject2coocoRes)));
        Constants.grade2CoocoMap.putAll(loadSubject2CoocoJsonFile(loandJsonFile(grade2coocoRes)));
    }

    private JSONArray loandJsonFile(Resource resource){
        logger.info("开始加载"+resource.getFilename()+"文件");
        JSONArray jsonArray = null;;
        try {
            String jsonStr = new String(IOUtils.readFully(resource.getInputStream(), -1, true));
            jsonArray = JSON.parseArray(jsonStr);
        }catch (Exception e) {
            logger.info(e);
        }
        logger.info("结束加载"+resource.getFilename()+"文件");
        return jsonArray;
    }

    private Map<String,String> parseJson2Dict(JSONArray jsonArray){
        Map<String,String> res = new HashMap<>();
        int size = jsonArray.size();
        for(int i=0; i<size; i++){
            JSONObject jo = jsonArray.getJSONObject(i);
            String code = jo.getString("code");
            String name = jo.getString("name");

            res.put(name, code);
        }
        return res;
    }

    private Map<String,String> loadSubject2CoocoJsonFile(JSONArray jsonArray){
        Map<String,String> res = new HashMap<>();
        int size = jsonArray.size();
        for(int i=0; i<size; i++){
            JSONObject jo = jsonArray.getJSONObject(i);
            String code = jo.getString("code");
            String cooco = jo.getString("cooco");

            res.put(code, cooco);
        }
        return res;
    }
}
