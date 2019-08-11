package com.debug.steadyjack.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.debug.steadyjack.model.Product;

public interface ProductMapper {
	
	int deleteByPrimaryKey(Integer id);
	
	int insert(Product record);
	
    int insertSelective(Product record);
    
    Product selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(Product record);
    
    int updateByPrimaryKey(Product record);
   
	List<Product> selectAll(@Param("name")String name);
	
	void insertBatch(@Param("dataList") List<Product> dataList);
	
 
}
