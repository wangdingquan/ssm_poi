package com.debug.steadyjack.utils;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.debug.steadyjack.model.Product;

public class ExcelUtil {

	private static final Logger log=Logger.getLogger(ExcelUtil.class);
	private static final String dataFormat="yyyy-MM-dd";
	
	private static final SimpleDateFormat simpleDateFormat=new SimpleDateFormat(dataFormat);
	
	
	public static void fillExcelSheetData(List<Map<Integer, Object>> dataList,Workbook wb,String[] headers,String sheetName) {
		Sheet sheet=wb.createSheet(sheetName);
		
		//TODO:创建sheet的第一行数据即excel的头部信息
		Row headerRow=sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			
			headerRow.createCell(i).setCellValue(headers[i]);
		}
		
		//TODO: 从第二行开始塞入真正的数据列表
		int rowIndex=1;
		Row row;
		Object obj;
		for (Map<Integer, Object> rowMap : dataList) {
			try {
				row=sheet.createRow(rowIndex++);
				for (int i = 0; i < headers.length; i++) {
					obj=rowMap.get(i);
					if(obj == null) {
						row.createCell(i).setCellValue("");
					}else if (obj instanceof Date) {
						String tempDate=simpleDateFormat.format((Date)obj);
						row.createCell(i).setCellValue((tempDate==null)?"":tempDate);
					}else {
						row.createCell(i).setCellValue(String.valueOf(obj));
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.debug("excel sheet填充数据 发生异常:",e.fillInStackTrace());
			}
		}
		
	}
	
	/**
	 * 处理产品列表 塞入list-map 等待塞入excel的Workbook进行处理
	 * @param products
	 * @return
	 */
	public static List<Map<Integer, Object>> manageProductList(final List<Product> products){
		List<Map<Integer, Object>> dataList=new ArrayList<>();
		if(products!=null&&products.size()>0) {
			int length=products.size();
			
			Map<Integer, Object> dataMap;
			Product bean;
			for (int i = 0; i < length; i++) {
				bean=products.get(i);
				
				
				dataMap=new HashMap<>();
				dataMap.put(0, bean.getId());
				dataMap.put(1, bean.getName());
				dataMap.put(2, bean.getUnit());
				dataMap.put(3, bean.getPrice());
				dataMap.put(4, bean.getStock());
				dataMap.put(5, bean.getPurchaseDate());
				dataMap.put(6, bean.getRemark());
				
				dataList.add(dataMap);
			}
		}
		return dataList;
	}
	
	
	public static String manageCell(Cell cell,String dateFormat) throws Exception {
		
		DecimalFormat decimalFormatZero=new DecimalFormat("0");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  //日期格式化
		DecimalFormat dacimalFormatNumeric=new DecimalFormat("0.00");
		
		String value="";
		CellType cellType=cell.getCellTypeEnum();
		if(CellType.STRING.equals(cellType)) {
			value=cell.getStringCellValue();
		}else if (CellType.NUMERIC.equals(cellType)) {
			if("General".equals(cell.getCellStyle().getDataFormatString())) {
				value=decimalFormatZero.format(cell.getNumericCellValue());
			}else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
				value=sdf.format(cell.getDateCellValue());
			}else {
				value=dacimalFormatNumeric.format(cell.getNumericCellValue());
			}
		}else if(CellType.BOOLEAN.equals(cellType)) {
			value=String.valueOf(cell.getBooleanCellValue());
		}else if(CellType.BLANK.equals(cellType)) {
			value="";
		}
		return value;
		
	}
}
