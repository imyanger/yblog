package com.yanger.blog.util;

import com.yanger.blog.cache.CacheRunner;
import com.yanger.blog.po.Const;
import com.yanger.blog.vo.ArticleKindVo;
import com.yanger.blog.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yanger
 * @description 常量操作的工具类
 * @date 2019/11/20
 */
@Component
public class ConstUtils {

    @Autowired
    private CacheRunner cacheRunner;

    /**
     * @description 常量转换，code获取val
     * @author yanger
     * @date 2019/11/20
     * @param code
     * @return java.lang.String
     */
    public String getValByCode(String code){
        Map <String, Const> constCache = cacheRunner.getConstCache();
        if(constCache != null){
            Const aConst = constCache.get(code);
            if(aConst != null){
                return aConst.getVal();
            }
        }
        return null;
    }

    /**
     * 给ArticleVo类型字段Val赋值
     * @param articleVo
     */
    public void setVal( ArticleVo articleVo) {
        articleVo.setModuleVal(getValByCode(articleVo.getModule()));
        articleVo.setTypeVal(getValByCode(articleVo.getType()));
        articleVo.setClassifyVal(getValByCode(articleVo.getClassify()));
    }

    /**
     * 给ArticleVo类型字段Val赋值
     * @param articleVo
     */
    public void setVal(ArticleKindVo articleVo) {
        articleVo.setModuleVal(getValByCode(articleVo.getModule()));
        articleVo.setTypeVal(getValByCode(articleVo.getType()));
        articleVo.setClassifyVal(getValByCode(articleVo.getClassify()));
    }

}
