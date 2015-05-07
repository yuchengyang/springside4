/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.entity.xmlnode;

import javax.xml.bind.annotation.XmlRootElement;

import org.springside.examples.quickstart.entity.ProjectData;

@XmlRootElement(name="project")
public class ProjectXml {
	
	private HeadXml header;
	
	private BodyXml<ProjectData> body;

	public BodyXml<ProjectData> getBody() {
		return body;
	}
	public void setBody(BodyXml<ProjectData> body) {
		this.body = body;
	}
	public HeadXml getHeader() {
		return header;
	}
	public void setHeader(HeadXml header) {
		this.header = header;
	}
	
	
}
