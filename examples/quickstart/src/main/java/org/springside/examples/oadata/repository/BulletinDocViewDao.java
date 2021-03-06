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
import org.springside.examples.oadata.entity.BulletinDocView;


public interface BulletinDocViewDao extends PagingAndSortingRepository<BulletinDocView, Long>, JpaSpecificationExecutor<BulletinDocView> {
	
	@Query("FROM BulletinDocView where projectId = ?1")
	List<BulletinDocView> queryBulletinDocView(String projectId);
}
