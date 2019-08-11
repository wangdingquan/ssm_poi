package com.debug.steadyjack.enums;

public enum WorkBookVersion {

	WorkBook2003("2003","2003版本workBook"),
	WorkBook2007("2007","2007版本workBook"),
	
	
	WorkBook2003Xls("xls","xls后缀名结尾-2003版本WorkBook"),
	WorkBook2007Xlsx("xlsx","xlsx后缀名结尾-2007版本workbook");
	
	
	private String code;
	
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private WorkBookVersion(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	
}
