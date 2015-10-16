/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.entity.xmlnode;

import javax.xml.bind.annotation.XmlRootElement;

import org.springside.examples.oadata.entity.BuyerView;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 * @author calvin
 */
// JPA 基类的标识
@XmlRootElement(name="buyer")
public class BuyerXml {
	
	private BodyXml<BuyerView> body;

	public BodyXml<BuyerView> getBody() {
		return body;
	}
	public void setBody(BodyXml<BuyerView> body) {
		this.body = body;
	}
}
