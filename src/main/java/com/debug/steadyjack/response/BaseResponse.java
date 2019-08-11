package com.debug.steadyjack.response;

import com.debug.steadyjack.enums.StatusCode;

public class BaseResponse<T> {
  
	private Integer code;
	
	private String msg;
	
	private T data;

	public BaseResponse() {
		super();
	}
	
	public BaseResponse(StatusCode statusCode) {
		this.code=statusCode.getCode();
		this.msg=statusCode.getMessage();
	}
	
	public BaseResponse(StatusCode statusCode,T data) {
		this.code=statusCode.getCode();
		this.msg=statusCode.getMessage();
		this.data=data;
	}

	public BaseResponse(Integer code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}	
}
