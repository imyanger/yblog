package com.yanger.blog.vo;

import com.yanger.blog.po.DateSum;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yanger
 * @description 统计vo
 * @date 2019/11/21
 */
@Data
public class SummaryVo implements Serializable {

    private List<ArticleKindVo> articleKindVos = new ArrayList <>(0);

    private  List<DateSum> dateSums = new ArrayList <>();

    private List<ArticleKindVo> moduleSums = new ArrayList <>(0);

}
