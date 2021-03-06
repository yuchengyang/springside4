/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.oadata.entity.ProjectStepTypeView;


public interface ProjectStepTypeViewDao extends PagingAndSortingRepository<ProjectStepTypeView, Long>, JpaSpecificationExecutor<ProjectStepTypeView> {
	
}
