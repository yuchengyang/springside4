/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.web.task;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springside.examples.oadata.entity.ApplyCompanyView;
import org.springside.examples.oadata.repository.AppCompanyViewDao;

/**
 * Task管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /task/
 * Create page : GET /task/create
 * Create action : POST /task/create
 * Update page : GET /task/update/{id}
 * Update action : POST /task/update
 * Delete action : GET /task/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/oa")
public class OaController {
	
	@Autowired
	private AppCompanyViewDao appCompanyViewDao;
	
	@RequestMapping(value = "getProjectDetail" , method = RequestMethod.GET)
	public String list(Model model,ServletRequest request) {
		
		ApplyCompanyView AppCompanyView = appCompanyViewDao.findOne(102764l);

		return "oa/projectDetail";
	}
}
