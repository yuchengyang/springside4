/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
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
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.oadata.entity.TenderView;
import org.springside.examples.oadata.repository.TenderViewDao;
import org.springside.examples.quickstart.entity.ProjectData;
import org.springside.examples.quickstart.entity.xmlnode.BodyXml;
import org.springside.examples.quickstart.entity.xmlnode.HeadXml;
import org.springside.examples.quickstart.entity.xmlnode.ProjectXml;
import org.springside.examples.quickstart.entity.xmlnode.SupplierXml;
import org.springside.examples.schedule.bean.Result;
import org.springside.modules.utils.PropertiesLoader;


// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class TenderViewService {
	private static Logger logger = LoggerFactory.getLogger(TenderViewService.class);
	
	static Marshaller marshaller ;
	
	static CloseableHttpClient httpClient;
	
	static {
		//发送
		try {
			JAXBContext context = JAXBContext.newInstance(SupplierXml.class);
			marshaller = context.createMarshaller();  
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  
			
			// 创建包含connection pool与超时设置的client
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20 * 1000).setConnectTimeout(20 * 1000).build();
			httpClient = HttpClientBuilder.create().setMaxConnTotal(20).setMaxConnPerRoute(20).setDefaultRequestConfig(requestConfig).build();
			
		} catch (JAXBException e) {
			logger.error("初始化{}类错误:"+ TenderViewService.class.getName() + e.getStackTrace());
		} 
	}
	
	@Autowired
	PropertiesLoader propertiesLoader;
	
	@Autowired
	private TenderViewDao tenderViewDao;

	public Result synTenderProccess(TenderView tenderView) {
		
		boolean result = false;
		//发送
        StringWriter writer = new StringWriter();  
        
        BodyXml<TenderView> bodyXml = new BodyXml<TenderView>();
        bodyXml.setProjectInfo(tenderView);;
        SupplierXml supplierXml =  new SupplierXml();
        supplierXml.setBody(bodyXml);
        try {
			marshaller.marshal(supplierXml, writer);
		} catch (JAXBException e) {
			logger.error("投标人{}|{}XML对象转换错误:"+e.getStackTrace(), tenderView.getTenderId() , tenderView.getTenderName() );
		}  
		try {
			HttpPost httpPost = new HttpPost( propertiesLoader.getProperty("syn.synSupplierUrl") );
            // 创建名/值组列表  
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
            
            String xmlContent = writer.toString();
            
            parameters.add(new BasicNameValuePair( "xmlContent",xmlContent ));  
            // 创建UrlEncodedFormEntity对象  
            UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity( parameters , "UTF-8");  
            httpPost.setEntity(formEntiry); 
            
			// 创建包含connection pool与超时设置的client
			CloseableHttpResponse closeableHttpResponse  = httpClient.execute(httpPost);
			closeableHttpResponse.getEntity().getContent();
			String xmlContentresp = IOUtils.toString(closeableHttpResponse.getEntity().getContent());
			if(xmlContentresp.contains("operTag")){
				String operTag = StringUtils.substringBetween(xmlContentresp, "<operTag>", "</operTag>");
				String operDesc = StringUtils.substringBetween(xmlContentresp, "<operDesc>", "</operDesc>") ;
				if("Y".equals(operTag)){//成功   
					result = true;
				}else{
					logger.error("投标人【{}|{}】同步失败！{}", tenderView.getTenderId() , tenderView.getTenderName(), operDesc);
				}
			}
			closeableHttpResponse.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("投标人【{}|{}】同步接口连接错误:"+e.getStackTrace(),tenderView.getTenderId() , tenderView.getTenderName());
		}
		return new Result(result ,null );
	}
}
