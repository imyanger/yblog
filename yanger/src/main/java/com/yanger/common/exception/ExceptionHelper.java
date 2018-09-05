package com.yanger.common.exception;

import com.yanger.common.vo.ApiResponse;

public interface ExceptionHelper {
	
	public ApiResponse<Object> processException(Exception ex);
	
}
