package com.yanger.blog.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yanger
 * @description 数量统计
 * @date 2019/11/21
 */
@Data
public class DateSum implements Serializable {

    private String artDate;

    private int sum;

}
