/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
@XmlRootElement(name="projectPkgs")
@Entity
@DiscriminatorValue("s")
public class ProjectPkgData extends ProjectData {
	
	private ProjectData parentProject;
	
	@XmlTransient
	//@XmlElement(name="ProjectPkgData")
	@ManyToOne
	@JoinColumn(name = "PROJECT_PARENT_ID")
//	@XmlElementRef( type = ProjectData.class )
	public ProjectData getParentProject() {
		return parentProject;
	}

	public void setParentProject(ProjectData parentProject) {
		this.parentProject = parentProject;
	}
}
