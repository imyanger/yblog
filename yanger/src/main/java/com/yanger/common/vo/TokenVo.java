package com.yanger.common.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
* <p>Description: token信息VO </p>  
* @author 杨号  
* @date 2018年9月14日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVo implements Serializable {

	private static final long serialVersionUID = 1L;

	//用户账号
	private String code;
	
	//用户名
	private String name;

}
