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
import org.springside.examples.oadata.entity.ApplyCompanyView;


public interface AppCompanyViewDao extends PagingAndSortingRepository<ApplyCompanyView, Long>, JpaSpecificationExecutor<ApplyCompanyView> {
	
	@Query("from ApplyCompanyView where projectId = ?1")
	List<ApplyCompanyView> getAppCompanyView(Long projectId);
	
	
	@Query("from ApplyCompanyView where projectId = ?1 and bidSectionId = ?2"  )
	List<ApplyCompanyView> getAppCompanyView(Long projectId ,Long sectionId  );
	
}
