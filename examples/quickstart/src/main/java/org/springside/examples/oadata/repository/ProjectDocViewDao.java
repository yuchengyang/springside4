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
import org.springside.examples.oadata.entity.ProjectDocView;


public interface ProjectDocViewDao extends PagingAndSortingRepository<ProjectDocView, Long>, JpaSpecificationExecutor<ProjectDocView> {
	
	@Query("FROM ProjectDocView where projectId = ?1 and docTypeId = ?2")
	List<ProjectDocView> queryProjectDocView(String projectId , String docTypeId);
	
	@Query("FROM ProjectDocView where projectId = ?1 order by uploadDate ")
	List<ProjectDocView> queryProjectDocView(String projectId );
}
