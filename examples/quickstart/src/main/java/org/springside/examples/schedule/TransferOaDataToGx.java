/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.schedule;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
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
	public static void main(String[] args){
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
		
		
		HttpPost httpPost = new HttpPost("http://219.239.33.123:9090/quickstart/api/getFileFromFTP");
		
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        
        nvps.add( new BasicNameValuePair("filePath", "2015-04/2015-04-28-718f376f-b725-4e18-87e2-43e30124b2b5-GXTC-1506112-招标公告&投标邀请-招标公告.rar" ));  
        
        String salt = "GXCX_OA_SALT";
        long currentTime =  ( ( new Date().getTime() * 4 +2 ) * 5 -1 ) * 8 + 3 ;//约定算法 by yucy
        String key  = salt + currentTime;
        nvps.add( new BasicNameValuePair("key",Base64.encodeBase64String(key.getBytes())));  

        try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps , "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 创建包含connection pool与超时设置的client
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20 * 1000).setConnectTimeout(20 * 1000).build();
		CloseableHttpClient  httpClient = HttpClientBuilder.create().setMaxConnTotal(20).setMaxConnPerRoute(20).setDefaultRequestConfig(requestConfig).build();
		try {
			CloseableHttpResponse closeableHttpResponseFile = httpClient.execute( httpPost );
			
			FileUtils.writeByteArrayToFile(new File("D:/upload/templateBulletin.rar"), IOUtils.toByteArray( closeableHttpResponseFile.getEntity().getContent() ));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		//TODO  需要真实的文件下载
		//FileUtils.writeByteArrayToFile( file, FileUtils.readFileToByteArray( new File("D:/upload/templateBulletin.zip") ) );

	}
	
}
