/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springside.examples.schedule.service.ScheduleService;

@Component
public class TransferOaDataToGx {
	
	private static Logger logger = LoggerFactory.getLogger(TransferOaDataToGx.class);

	@Autowired
	private ScheduleService scheduleService;
	
	/**从oa抓取数据*/
	public void getDataFromOA() {
		try {
			//处理采购人
			scheduleService.updateGetedBuyer();
			//处理公告
			scheduleService.updateGetedBulletin();
			//处理项目
			scheduleService.updateGetedProject();

		} catch (Exception e) {
			logger.error("抓取数据出现异常" + e.getMessage() );
		}
	}
	
//	
//	public static void main(String[] args){
//		
//		// 获取内容
//		HttpPost httpPost = new HttpPost("http://gxoa.cc/login!login.do?userName=王月谭&userPassword=gxuser&loginState=1");
//		
//		// 创建包含connection pool与超时设置的client
//		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20 * 1000)
//				.setConnectTimeout(20 * 1000).build();
//
//		CloseableHttpClient  httpClient = HttpClientBuilder.create().setMaxConnTotal(20).setMaxConnPerRoute(20)
//				.setDefaultRequestConfig(requestConfig).build();
//		try {
//			CloseableHttpResponse  closeableHttpResponse = httpClient.execute(httpPost);
//			System.out.println(IOUtils.toString(closeableHttpResponse.getEntity().getContent(), "UTF-8"));
//			HttpGet httpGet = new HttpGet("http://gxoa.cc/attachmentDownload.do?filePath=2010-07/2010-07-28-4bdd9279-c09e-4b68-80fc-c9d049c6bdfc-GXTC-1005124-项目批件-发改投资【2009】1062.rar");
//			CloseableHttpResponse closeableHttpResponseFile = httpClient.execute(httpGet);
//			//FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\"));
//			System.out.println(IOUtils.toString(closeableHttpResponseFile.getEntity().getContent(), "UTF-8"));
//
//			
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
}
