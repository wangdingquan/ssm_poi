package com.debug.steadyjack.request;

import java.io.Serializable;

public class ProductRequest implements Serializable {
	
	private static final long serialVersionUID=6235211737642357039L;
	
	private Integer id;
	
	private String name;
	
	private String unit;
	
	private Double price;
	
	private Double stock;
	
	private String remark;
	
	private String purchaseDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
