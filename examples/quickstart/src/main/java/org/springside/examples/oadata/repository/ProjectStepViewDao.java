/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.oadata.entity.ProjectStepView;


public interface ProjectStepViewDao extends PagingAndSortingRepository<ProjectStepView, Long>, JpaSpecificationExecutor<ProjectStepView> {

	@Query(" select count(1) FROM ProjectStepView where projectId = ?1 and projectStepTypeId in (3,4,5) ")
	Long prequalificationCount(long projectId );
}
