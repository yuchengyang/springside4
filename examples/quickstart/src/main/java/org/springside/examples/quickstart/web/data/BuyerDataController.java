/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.web.data;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.examples.quickstart.entity.BuyerData;
import org.springside.examples.quickstart.service.BuyerDataService;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

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
@RequestMapping(value = "/buyer")
public class BuyerDataController {

	private static final String PAGE_SIZE = "20";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private BuyerDataService buyerDataService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		//Long userId = getCurrentUserId();

		Page<BuyerData> buyers = buyerDataService.getBuyerData(searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("buyers", buyers);
		model.addAttribute("sortType", sortType);
		//model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "buyer/buyerList";
	}
	
/*	@RequestMapping(value = "synBuyer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> synBuyer(final @RequestParam(value = "ids[]")Integer... ids) {
		return new HashMap<String, Object>(){
			private static final long serialVersionUID = 1L;
			{
				put("success", buyerDataService.synBuyer(ids));
			}
		};
	}*/	
}
