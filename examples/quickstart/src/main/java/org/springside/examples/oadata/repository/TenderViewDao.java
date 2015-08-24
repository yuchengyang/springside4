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
import org.springside.examples.oadata.entity.TenderView;


public interface TenderViewDao extends PagingAndSortingRepository<TenderView, Long>, JpaSpecificationExecutor<TenderView> {

	
	/**同步用方法 start**/
	@Query("SELECT min( tenderId ) FROM TenderView")
	Long findMinId4Syn();
	
	@Query("SELECT max( tenderId ) FROM TenderView")
	Long findMaxId4Syn();
	
	@Query("from TenderView as tender  where  tender.tenderId > ?1 and tender.tenderId <= ?2 ")
	List<TenderView> getSynTenderViewFromTo(Long synSupplierfromId, Long synSupplierToId);
	/**同步用方法 end **/
}
