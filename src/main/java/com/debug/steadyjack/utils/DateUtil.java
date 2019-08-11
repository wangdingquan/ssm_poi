package com.debug.steadyjack.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期工具类
 * @author wangdingquan
 *
 */
public class DateUtil {
	
	public static String dateToString(Date date,String dataFormat) throws Exception {
		SimpleDateFormat format=new SimpleDateFormat(dataFormat);
		return format.format(date);
	}
	
	public static Date strToDate(String str,String dateFormat) throws Exception {
		SimpleDateFormat format=new SimpleDateFormat(dateFormat);
		return format.parse(str);
	}

}
