/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 * @author calvin
 */
// JPA 基类的标识
@Entity
@DiscriminatorValue("s")
public class ProjectPkgView extends ProjectView {
	
	private ProjectView parentProject;

	@Cascade(CascadeType.REFRESH)
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "PROJECT_PARENT_ID")
	public ProjectView getParentProject() {
		return parentProject;
	}

	public void setParentProject(ProjectView parentProject) {
		this.parentProject = parentProject;
	}
}
