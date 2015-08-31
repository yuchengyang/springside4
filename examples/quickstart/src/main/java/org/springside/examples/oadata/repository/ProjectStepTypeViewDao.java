/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.oadata.entity.ProjectStepTypeView;


public interface ProjectStepTypeViewDao extends PagingAndSortingRepository<ProjectStepTypeView, Long>, JpaSpecificationExecutor<ProjectStepTypeView> {
	
	@Query(" select pst FROM ProjectStepTypeView pst,  ProjectStepView ps  where pst.projectStepTypeId = ps.projectStepTypeId and ps.projectId = ?1 and ps.latestFinished=1 ")
	java.util.List<ProjectStepTypeView> getLastedStep(long projectId );
	
	@Query(" select pst FROM ProjectStepTypeView pst,  ProjectStepView ps  where pst.projectStepTypeId = ps.projectStepTypeId and ps.projectId = ?1 and ps.isActive=1 ")
	java.util.List<ProjectStepTypeView> getActivStep(long projectId );
	
	
	@Query(" select pst FROM ProjectStepTypeView pst,  ProjectStepView ps  where pst.projectStepTypeId = ps.projectStepTypeId and ps.projectId = ?1 ")
	java.util.List<ProjectStepTypeView> getStep(long projectId );
}
