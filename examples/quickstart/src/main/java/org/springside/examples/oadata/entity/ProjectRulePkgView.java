/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.entity;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="pack")
public class ProjectRulePkgView {
	
	private String packCode;//project_code `项目id`,
	private String packName;//project_code `项目id`,

    private BigDecimal filePrice;// BID_DOC_PRICE-- 招标文件价格
    private BigDecimal bailPrice; //BID_DEPOSIT-- `投标保证金`,
	public String getPackCode() {
		return packCode;
	}
	public void setPackCode(String packCode) {
		this.packCode = packCode;
	}
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public BigDecimal getFilePrice() {
		return filePrice;
	}
	public void setFilePrice(BigDecimal filePrice) {
		this.filePrice = filePrice;
	}
	public BigDecimal getBailPrice() {
		return bailPrice;
	}
	public void setBailPrice(BigDecimal bailPrice) {
		this.bailPrice = bailPrice;
	}
}
