/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.entity.xmlnode;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.springside.examples.oadata.entity.ProjectRuleView;
import org.springside.examples.oadata.entity.TenderView;
import org.springside.examples.quickstart.entity.BulletinData;
import org.springside.examples.quickstart.entity.BuyerData;
import org.springside.examples.quickstart.entity.ProjectData;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 * @author calvin
 */
// JPA 基类的标识
@XmlRootElement(name="body" )
@XmlSeeAlso({ProjectData.class, BulletinData.class , ProjectRuleView.class , TenderView.class })
public class BodyXml<T> {
	
	private BuyerData buyerInfo;
	
	private T projectInfo;
	
	public BuyerData getBuyerInfo() {
		return buyerInfo;
	}

	public void setBuyerInfo(BuyerData buyerInfo) {
		this.buyerInfo = buyerInfo;
	}

	public T getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(T projectInfo) {
		this.projectInfo = projectInfo;
	}
}
