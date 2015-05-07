/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.entity.xmlnode;

import javax.xml.bind.annotation.XmlRootElement;

import org.springside.examples.quickstart.entity.BulletinData;

@XmlRootElement(name="bulletin")
public class BulletinXml {
	
	private HeadXml header;
	
	private BodyXml<BulletinData> body;

	public HeadXml getHeader() {
		return header;
	}
	public void setHeader(HeadXml header) {
		this.header = header;
	}
	public BodyXml<BulletinData> getBody() {
		return body;
	}
	public void setBody(BodyXml<BulletinData> body) {
		this.body = body;
	}
}
