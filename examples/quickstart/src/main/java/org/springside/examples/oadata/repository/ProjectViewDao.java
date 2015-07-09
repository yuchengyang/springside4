/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.oadata.entity.ProjectView;


public interface ProjectViewDao extends PagingAndSortingRepository<ProjectView, String>, JpaSpecificationExecutor<ProjectView> {

	@Query("from ProjectView as project  where project.delegateDate > ?1 and project.delegateDate < ?2 ")
	List<ProjectView> getProjectViewFromToTime(Date from, Date to);

	@Query("SELECT min( delegateDate ) FROM ProjectView")
	Date findMinDelegateDate();

	@Query("SELECT count(projectId) FROM ProjectView where delegateDate < ?1")
	Long countBeforeDate(Date date);
}
