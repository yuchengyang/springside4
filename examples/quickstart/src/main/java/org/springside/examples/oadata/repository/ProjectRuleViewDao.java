/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.oadata.entity.ProjectRuleView;


public interface ProjectRuleViewDao extends PagingAndSortingRepository<ProjectRuleView, String>, JpaSpecificationExecutor<ProjectRuleView> {
	
	@Query("FROM ProjectRuleView where projectId = ?1")
	List<ProjectRuleView> queryProjectRuleView(String projectId );

}
