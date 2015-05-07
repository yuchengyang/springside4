/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.entity.xmlnode;

import javax.xml.bind.annotation.XmlRootElement;

import org.springside.examples.oadata.entity.ProjectRuleView;
import org.springside.examples.quickstart.entity.xmlnode.BodyXml;
import org.springside.examples.quickstart.entity.xmlnode.HeadXml;

@XmlRootElement(name="project")
public class ProjectRuleXml {
	
	private HeadXml header;
	
	private BodyXml<ProjectRuleView> body;

	public HeadXml getHeader() {
		return header;
	}

	public void setHeader(HeadXml header) {
		this.header = header;
	}

	public BodyXml<ProjectRuleView> getBody() {
		return body;
	}

	public void setBody(BodyXml<ProjectRuleView> body) {
		this.body = body;
	}
	
	
}
