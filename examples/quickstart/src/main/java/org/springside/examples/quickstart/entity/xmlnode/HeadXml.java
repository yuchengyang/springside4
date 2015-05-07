/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.entity.xmlnode;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="header")
public class HeadXml {

	private String pmOrgId;
	private String pmUserId;
	
	public HeadXml(){
		
	}
	
	public HeadXml(String pmOrgId, String pmUserId) {
		super();
		this.pmOrgId = pmOrgId;
		this.pmUserId = pmUserId;
	}

	public String getPmOrgId() {
		return pmOrgId;
	}
	public void setPmOrgId(String pmOrgId) {
		this.pmOrgId = pmOrgId;
	}
	public String getPmUserId() {
		return pmUserId;
	}
	public void setPmUserId(String pmUserId) {
		this.pmUserId = pmUserId;
	}

	
	
}
