/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.BuyerData;

public interface BuyerDataDao extends PagingAndSortingRepository<BuyerData, Long>, JpaSpecificationExecutor<BuyerData> {

	@Query("SELECT max( customerId ) FROM BuyerData")
	Integer findMaxCustomerId();
	
	@Query("FROM BuyerData  where customerId = ?1 ")
	List<BuyerData> findBuyerData(int customerId);
}
