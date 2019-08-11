package com.debug.steadyjack.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.debug.steadyjack.enums.StatusCode;
import com.debug.steadyjack.enums.WorkBookVersion;
import com.debug.steadyjack.mapper.ProductMapper;
import com.debug.steadyjack.model.Product;
import com.debug.steadyjack.request.EntityRequest;
import com.debug.steadyjack.request.ProductRequest;
import com.debug.steadyjack.response.BaseResponse;
import com.debug.steadyjack.service.PoiService;
import com.debug.steadyjack.service.ProductService;
import com.debug.steadyjack.utils.DateUtil;
import com.debug.steadyjack.utils.ExcelUtil;
import com.debug.steadyjack.utils.WebUtil;

@Controller
public class ProductController {
	
	private final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	private static final String prefix="product";
	
	
	@Value("${poi.excel.sheet.name}")
	private String sheetProductName;
	
	@Value("${poi.excel.file.name}")
	private String excelProductName;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private PoiService poiService;
	
	@Autowired
	private ProductService productService;
	
	
	/**
	 * 获取所有产品列表
	 * @param name
	 * @return
	 */
	@RequestMapping(value=prefix+"/list",method=RequestMethod.GET)
	@ResponseBody
	public List<Product> list(String name){
		List<Product> products=new ArrayList<Product>();
		try {
			name=StringUtils.isNotEmpty(name)?URLDecoder.decode(name,"UTF-8"):null;
			
			products=productMapper.selectAll(name);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("获取产品列表发生异常:",e.fillInStackTrace());
		}
		return products;
	}
	
	/**
	 * 导出excel  --还可以用于下载
	 * @param response
	 * @param search
	 * @return
	 */
	@RequestMapping(value=prefix+"/excel/export",method=RequestMethod.GET)
	@ResponseBody
	public String exportExcel(HttpServletResponse response,String search) {
		try {
			search=StringUtils.isNotEmpty(search)?URLDecoder.decode(search,"UTF-8"):null;
			
			String[] headers=new String[] {"id编号","名称","单位","单价","库存量","采购日期","备注信息"};
			List<Product> products=productMapper.selectAll(search);
			
			
			//填充数据往每个头部
			List<Map<Integer,Object>> dataList=ExcelUtil.manageProductList(products);
			log.info("excel下载填充数据:{}",dataList);
			
			Workbook wb=new HSSFWorkbook();
			ExcelUtil.fillExcelSheetData(dataList,wb,headers,sheetProductName);
			WebUtil.downloadExcel(response,wb,excelProductName);
			return excelProductName;
		} catch (Exception e) {
			log.error("下载excel 发生异常:",e.fillInStackTrace());
		}
		return null;
	}
	
	/**
	 * 上传excel导入数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value=prefix+"/excel/upload",method=RequestMethod.POST,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public BaseResponse uploadExcel(MultipartHttpServletRequest request) {
		BaseResponse response=new BaseResponse(StatusCode.Success);
		try {
			MultipartFile file=request.getFile("productFile");
			if(file==null||file.getName()==null) {
				return new BaseResponse(StatusCode.Invalid_Param);
			}
			String fileName=file.getOriginalFilename();
			String suffix=StringUtils.substring(fileName, fileName.lastIndexOf(".")+1);
			if(WorkBookVersion.valueOf(suffix)==null) {
				return new BaseResponse(StatusCode.WorkBook_Version_Invalid);
			}
			log.info("文件名:{} 文件后缀名:{}",fileName,suffix);
			
			
			Workbook wb=poiService.getWorkbook(file,suffix);
			List<Product> products=poiService.readExcelData(wb);
			
			//批量插入-第一种方法
			//productService.insertBatch(products);
			
			//数量插入第一种方法
			//批量插入-第二种方法(注意jdbc链接mysql允许批量插入删除的配置)
			for (Product p : products) {
				productService.saveProduct(p);
			}
		} catch (Exception e) {
			log.error("上传excel导入数据 发生异常",e.fillInStackTrace());
			return new BaseResponse<>(StatusCode.System_Error);
		}
		return response;
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value=prefix+"/delete",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse delete(@Validated @RequestBody EntityRequest request) {
		BaseResponse response=new BaseResponse(StatusCode.Success);
		try {
			if(request.getId()==null||request.getId()<=0) {
				return new BaseResponse(StatusCode.Invalid_Param);
			}
			
			Product product=productMapper.selectByPrimaryKey(request.getId());
			if(product==null) {
				return new BaseResponse(StatusCode.Entity_Not_Exist);
			}
			product.setIsDelete(1);
			productMapper.updateByPrimaryKeySelective(product);
		} catch (Exception e) {
			// TODO: handle exception
			response=new BaseResponse(StatusCode.Fail);
			log.error("删除发生异常: id={}",request.getId(),e.fillInStackTrace());
		}
		return response;
	}
	
	
	@SuppressWarnings("rawTypes")
	@RequestMapping(value=prefix+"/insert/or/update",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse insert(@Validated @RequestBody ProductRequest request){
		BaseResponse response=new BaseResponse(StatusCode.Success);
		try {		
			if(StringUtils.isEmpty(request.getName())||StringUtils.isEmpty(request.getUnit())||request.getPrice()==null
					||StringUtils.isEmpty(request.getPurchaseDate())) {
				return new BaseResponse(StatusCode.Invalid_Param);
			}
			Date purchaseDate=DateUtil.strToDate(request.getPurchaseDate(),"yyyy-MM-dd");
			
			Product p;
			if(request.getId()!=null&&request.getId()>0) {
				p=productMapper.selectByPrimaryKey(request.getId());
			    if(p==null) {
			    	return new BaseResponse(StatusCode.Entity_Not_Exist);
			    }
			    BeanUtils.copyProperties(request, p);
			    p.setId(request.getId());
			    p.setPurchaseDate(purchaseDate);
			    productMapper.updateByPrimaryKeySelective(p);
			}else {
				p=new Product();
				BeanUtils.copyProperties(request,p);
				p.setPurchaseDate(purchaseDate);
				productMapper.insertSelective(p);
			}
		} catch (Exception e) {
			// TODO: handle exception
			response=new BaseResponse(StatusCode.Fail);
			log.error("删除 发生异常: id={}",request.getId(),e.fillInStackTrace());
		}
		return response;
	}
	
	

}
