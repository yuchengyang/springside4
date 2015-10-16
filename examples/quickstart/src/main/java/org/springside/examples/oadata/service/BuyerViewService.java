/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.oadata.entity.BuyerView;
import org.springside.examples.oadata.repository.BuyerViewDao;


// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class BuyerViewService {
	@Autowired
	private BuyerViewDao buyerViewDao;
	
	public String getBuyerViewName(String delegateCompany) {
		BuyerView buyerView = buyerViewDao.findOne(Integer.parseInt(delegateCompany));
		return buyerView!=null ?buyerView.getCustomerName():null;
	}
}
