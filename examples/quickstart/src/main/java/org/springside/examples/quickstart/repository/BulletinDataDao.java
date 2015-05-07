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
import org.springside.examples.quickstart.entity.BulletinData;

public interface BulletinDataDao extends PagingAndSortingRepository<BulletinData, Long>, JpaSpecificationExecutor<BulletinData> {

	@Query("SELECT max( announcementDate ) FROM BulletinData")
	Date findMaxAnnouncementDate();
	
	
	@Query("FROM BulletinData where projectId = ?1 ")
	List<BulletinData> findBulletinData(String projectId);
}
