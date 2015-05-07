/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.ProjectData;

public interface ProjectDataDao extends PagingAndSortingRepository<ProjectData, Long>, JpaSpecificationExecutor<ProjectData> {

	@Query("SELECT max( delegateDate ) FROM ProjectData")
	Date findMaxDelegateDate();
}
