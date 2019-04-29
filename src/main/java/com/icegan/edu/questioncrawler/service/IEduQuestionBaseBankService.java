package com.icegan.edu.questioncrawler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icegan.edu.questioncrawler.model.EduQuestionBankBase;

import java.util.List;

public interface IEduQuestionBaseBankService extends IService<EduQuestionBankBase> {

    /**
     * 根据状态查找指定数量的数据
     * @param limit
     * @param status
     * @return
     */
    public List<EduQuestionBankBase> selectDatasByStatusesAndLimit(int limit,Integer... status);

    /**
     * 根据id集合批量更新状态为指定值
     * @param status
     * @param ids
     * @return
     */
    public boolean updateStatusByIds(Integer status, List<String> ids);

    public List<EduQuestionBankBase> selectDatasByOffsetAndLimit(int offset, int limit);

    /**
     * 清空stem字段
     * @param ids
     * @return
     */
    public boolean clearStemFiled(List<String> ids);
}
