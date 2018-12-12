package com.yanger.common.vo;

import java.io.Serializable;

import com.yanger.blog.vo.BlogUserVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description token信息VO
 * @author 杨号
 * @date 2018年9月14日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenMsg implements Serializable {

	private static final long serialVersionUID = 1L;

	// 用户id
	private Integer id;

	// 用户头像路径
	private String path;

	// 用户账号
	private String code;

	// 用户名
	private String name;

	public TokenMsg setInfo(BlogUserVo user) {
		this.id = user.getUserId();
		this.path = user.getUserImgPath();
		this.code = user.getUserCode();
		this.name = user.getUserNickName();
		return this;
	}

}
