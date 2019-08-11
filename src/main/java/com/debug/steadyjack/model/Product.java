package com.debug.steadyjack.model;
 
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
 
public class Product {
    private Integer id;
 
    private String name;
 
    private String unit;
 
    private Double price;
 
    private Double stock;
 
    private String remark;
 
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date purchaseDate;
    
    private Integer isDelete;
    
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
        this.name = name == null ? null : name.trim();
    }
 
    public String getUnit() {
        return unit;
    }
 
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }
 
    public Double getPrice() {
        return price;
    }
 
    public void setPrice(Double price) {
        this.price = price;
    }
 
    public String getRemark() {
        return remark;
    }
 
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
 
	public Date getPurchaseDate() {
		return purchaseDate;
	}
 
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
 
	public Double getStock() {
		return stock;
	}
 
	public void setStock(Double stock) {
		this.stock = stock;
	}
	
 
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", unit=" + unit
				+ ", price=" + price + ", stock=" + stock + ", remark="
				+ remark + ", purchaseDate=" + purchaseDate + "]";
	}
	
}