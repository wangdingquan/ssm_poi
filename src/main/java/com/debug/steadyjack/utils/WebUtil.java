package com.debug.steadyjack.utils;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * web通用工具
 * @author wangdingquan
 *
 */
public class WebUtil {

	/**
	 * 下载Excel
	 * @param response
	 * @param wb
	 * @param fileName
	 * @throws Exception
	 */
	public static void downloadExcel(HttpServletResponse response,Workbook wb,String fileName) throws Exception {
		response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"iso-8859-1"));
		response.setContentType("application/ynd.ms-excel;charset=UTF-8");
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
	}
}
