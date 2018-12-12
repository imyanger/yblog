package com.yanger.common.vo;

import java.io.Serializable;

/**
 * @description 通用请求响应VO
 * @author 杨号
 * @date 2018年9月14日
 */
public class ApiResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SUCCESS = 0;

	public static final int ERROR = 1;

	public static final int ERROR_TOKEN = 2;

	public static final int BUSY = -100;

	public static final String SUCCESS_TEXT = "Success";

	public static final String BUSY_TEXT = "Service is busy now";

	/**
	 * 响应状态
	 */
	private long status;
	/**
	 * 响应返回信息
	 */
	private String msg;
	/**
	 * 响应数据
	 */
	private T data;
	/**
	 * 响应的令牌
	 */
	private String token;

	public ApiResponse() {
		status = SUCCESS;
		msg = SUCCESS_TEXT;
	}

	public ApiResponse(long status, String msg, T data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public ApiResponse(T data) {
		super();
		status = SUCCESS;
		msg = SUCCESS_TEXT;
		this.data = data;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
		if (status == SUCCESS) {
			this.msg = SUCCESS_TEXT;
		} else if (status == BUSY) {
			this.msg = BUSY_TEXT;
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @description 错误信息
	 * @param msg
	 */
	public void error(String msg) {
		this.msg = msg;
		this.status = ERROR;
	}

	public void errorToken() {
		this.msg = "token不合法";
		this.status = ERROR_TOKEN;
	}

}
