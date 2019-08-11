package com.debug.steadyjack.enums;

public enum StatusCode {
	
	Success(0,"成功"),
	Fail(-1,"失败"),
	Invalid_Param(1001,"无效的参数"),
	System_Error(1002,"系统错误"),
	Entity_Not_Exist(1003,"对象实体不存在"),
	
	WorkBook_Version_Invalid(1003,"excel版本号不合法");
	
	private Integer code;
	
	private String message;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private StatusCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	
	
	

}
