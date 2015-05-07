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
import org.springside.examples.oadata.entity.BuyerView;


public interface BuyerViewDao extends PagingAndSortingRepository<BuyerView, String>, JpaSpecificationExecutor<BuyerView> {

	@Query("from BuyerView where customerId > ?1 and customerId < ?2")
	List<BuyerView> getBuyerViewFromToID(Integer from, Integer to);

	@Query("SELECT min( customerId ) FROM BuyerView")
	Integer findMinCustomerId();
}
