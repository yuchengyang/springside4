/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.ProjectData;

public interface ProjectDataDao extends PagingAndSortingRepository<ProjectData, Long>, JpaSpecificationExecutor<ProjectData> {

	@Query("SELECT max( delegateDate ) FROM ProjectData")
	Date findMaxDelegateDate();

	@Query("SELECT count(1) FROM ProjectData as project where project.useStatus=1 and project.delegateDate < ?1")
	Long countBeforeDate(Date date);

	@Query("SELECT count(1) FROM ProjectData as project where project.projectId = ?1")
	Long countByProjectId(String projectId);
	
	@Query("from ProjectData as project where project.projectId = ?1  ")
	ProjectData getProject(String projectId);
	
	@Query("from ProjectData as project  where project.useStatus=1 and project.delegateDate > ?1 and project.delegateDate <= ?2 ")
	List<ProjectData> getProjectViewFromToTime(Date from, Date to);
}
