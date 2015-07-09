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
import org.springside.examples.oadata.entity.BulletinView;


public interface BulletinViewDao extends PagingAndSortingRepository<BulletinView, String>, JpaSpecificationExecutor<BulletinView> {
	
	@Query("from BulletinView where announcementDate > ?1 and announcementDate <=?2 ")
	List<BulletinView> getBulletinViewFromToTime(Date from, Date to);

	@Query("SELECT min( announcementDate ) FROM BulletinView ")
	Date findMinAnnouncementDate();
}
