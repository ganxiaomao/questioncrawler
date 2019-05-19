package com.icegan.edu.questioncrawler.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public interface EduQuestionBankBaseMapper extends BaseMapper<EduQuestionBankBase> {
    @InsertProvider(type = Provider.class, method = "insertBatch")
    int insertBatch(@Param("subject") String subject,@Param("questions") List<EduQuestionBankBase> questions);

    @Select("select * from biz_edu_question_bank_base_${subject}")
    List<EduQuestionBankBase> selectDatasBySubject(@Param("subject") String subject);

    class Provider {
        public String insertBatch(Map map){
            List<EduQuestionBankBase> bbs = (List<EduQuestionBankBase>) map.get("questions");
            String subject = map.get("subject").toString();
            StringBuilder sb = new StringBuilder();
            sb.append("insert into biz_edu_question_bank_base_"+subject+" (id, type, subject, grade, difficult, course_id, chapter_id, create_time, update_time, create_by, status, answer_id, origin_from) values");
            MessageFormat mf = new MessageFormat(
                    "(#'{'questions[{0}].id}, #'{'questions[{0}].type}, #'{'questions[{0}].subject}, #'{'questions[{0}].grade}, #'{'questions[{0}].difficult}, #'{'questions[{0}].courseId}, #'{'questions[{0}].chapterId}, #'{'questions[{0}].createTime}, #'{'questions[{0}].updateTime}, #'{'questions[{0}].createBy}, #'{'questions[{0}].status}, #'{'questions[{0}].answerId}, #'{'questions[{0}].originFrom})"
            );
            int size = bbs.size();
            for(int i=0; i < size; i++){
                sb.append(mf.format(new Object[] {i}));
                if(i < (size - 1))
                    sb.append(",");
            }
            String res = sb.toString();
            System.out.println(res);
            return res;
        }
    }
}
