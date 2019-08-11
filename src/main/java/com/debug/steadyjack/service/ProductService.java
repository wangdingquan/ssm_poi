package com.debug.steadyjack.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.debug.steadyjack.mapper.ProductMapper;
import com.debug.steadyjack.model.Product;

@Service
public class ProductService {

	private final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private ProductMapper productMapper;
	
	
	public void saveProduct(Product p) {
		if(p!=null) {
			logger.debug("待插入的数据: {}",p);
			
			productMapper.insertSelective(p);
		}
	}
}
