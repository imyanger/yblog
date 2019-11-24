package com.yanger.blog.service;

import com.yanger.blog.dao.ArticleKindDao;
import com.yanger.blog.po.ArticleKind;
import com.yanger.blog.po.DateSum;
import com.yanger.blog.util.ConstUtils;
import com.yanger.blog.vo.ArticleKindVo;
import com.yanger.blog.vo.SummaryVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author yanger
 * @description 统计service
 * @date 2019/11/21
 */
@Service
public class KindService {

    @Autowired
    private ArticleKindDao articleKindDao;

    @Autowired
    private ConstUtils constUtils;

    /**
     * @description 进行统计（文章更改后）
     * @author yanger
     * @date 2019/11/21
     * @param
     * @return void
     */
    public void exeDealSummary(){
        articleKindDao.deleteAll();
        articleKindDao.dealSummaryData();
        new Thread(() -> {
        }).start();
    }

    /**
     * @description 获取统计数据
     * @author yanger
     * @date 2019/11/21
     * @param
     * @return void
     */
    public SummaryVo getSummaryData() throws Exception {
        SummaryVo summaryVo = new SummaryVo();
        // 按文章类型的map
        Map<String, Integer> moduleSumMap = new HashMap<>();
        // 分类的统计
        List <ArticleKind> articleKinds = articleKindDao.selectSummary();
        List<ArticleKindVo> articleKindVos = new ArrayList<>(0);
        articleKinds.forEach(articleKind -> {
            try {
                ArticleKindVo articleKindVo = new ArticleKindVo();
                BeanUtils.copyProperties(articleKindVo, articleKind);
                constUtils.setVal(articleKindVo);
                articleKindVos.add(articleKindVo);
                // 根据模块计数
                String moduleVal = articleKindVo.getModuleVal();
                if(moduleSumMap.containsKey(moduleVal)) {
                    Integer sum = moduleSumMap.get(moduleVal);
                    moduleSumMap.put(moduleVal, sum + articleKindVo.getSum());
                } else {
                    moduleSumMap.put(moduleVal, articleKindVo.getSum());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        summaryVo.setArticleKindVos(articleKindVos);
        // 按年月统计
        List<DateSum> dateSums = articleKindDao.dateSum(null);
        summaryVo.setDateSums(dateSums);
        List<ArticleKindVo> moduleSums = new ArrayList <>(0);
        for (Map.Entry<String, Integer> entry : moduleSumMap.entrySet()) {
            ArticleKindVo moduleSum = new ArticleKindVo();
            moduleSum.setModule(entry.getKey());
            moduleSum.setSum(entry.getValue());
            moduleSums.add(moduleSum);
        }
        summaryVo.setModuleSums(moduleSums);
        return summaryVo;
    }
}
