/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.quickstart.entity.BulletinData;
import org.springside.examples.quickstart.entity.BuyerData;
import org.springside.examples.quickstart.entity.ProjectData;
import org.springside.examples.quickstart.entity.xmlnode.BodyXml;
import org.springside.examples.quickstart.entity.xmlnode.BulletinXml;
import org.springside.examples.quickstart.repository.BulletinDataDao;
import org.springside.examples.quickstart.repository.ProjectDataDao;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.utils.PropertiesLoader;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class BulletinDataService {
	
	private static Logger logger = LoggerFactory.getLogger(BuyerDataService.class);
	
	static Marshaller marshaller ;
	
	static CloseableHttpClient httpClient;
	
	@Autowired
	PropertiesLoader propertiesLoader;
	
	static {
		//发送
		try {
			JAXBContext context = JAXBContext.newInstance(BulletinXml.class);
			marshaller = context.createMarshaller();  
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  
			
			// 创建包含connection pool与超时设置的client
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20 * 1000).setConnectTimeout(20 * 1000).build();
			httpClient = HttpClientBuilder.create().setMaxConnTotal(20).setMaxConnPerRoute(20).setDefaultRequestConfig(requestConfig).build();
			
		} catch (JAXBException e) {
			logger.error("初始化{}类错误:"+ BuyerDataService.class.getName() + e.getStackTrace());
		}  
	}

	@Autowired
	private BulletinDataDao bulletinDataDao;
	
	@Autowired
	private ProjectDataDao projectDataDao;
	
	public Page<BulletinData> getBulletinData( Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<BulletinData> spec = buildSpecification( searchParams);
		return bulletinDataDao.findAll(spec, pageRequest);
	}
	
	/**创建分页请求*/
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		} else {
			sort = new Sort(Direction.ASC, "announcementDate");

		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<BulletinData> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		//filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<BulletinData> spec = DynamicSpecifications.bySearchFilter(filters.values(), BulletinData.class);
		return spec;
	}

	/**
	 * 向阳光易购发送公告数据
	 * @param ids
	 */
	public boolean synBulletin(Long[] ids) {
		//方法执行结果
		boolean result = false;
		List<Long> idls = new ArrayList<Long>();
		CollectionUtils.addAll(idls, ids);
		Iterable<BulletinData> bulletinDatas = bulletinDataDao.findAll(idls);
		for (BulletinData bulletinData:   bulletinDatas){
			
			//设置bulletinData
//			bulletinData.setBulletinUrl("http://gxoa.cc/attachmentDownload.do?filePath="+bulletinData.getAttachmentPath()+"&amp;fileName="+bulletinData.getAttachmentId());
			
			//发送
	        StringWriter writer = new StringWriter();  
	        
	        BodyXml<BulletinData> bodyXml = new BodyXml<BulletinData>();
	        bodyXml.setProjectInfo(bulletinData);
	        BulletinXml bulletinXml =  new BulletinXml();
	        bulletinXml.setBody(bodyXml);
	        
	        try {
				marshaller.marshal(bulletinXml, writer);
			} catch (JAXBException e) {
				logger.error("采购公告{}|{}XML对象转换错误:"+e.getStackTrace(), bulletinData.getId() , bulletinData.getAnnouncementDate() );
			}  
			try {
				
				HttpPost httpPost = new HttpPost( propertiesLoader.getProperty("syn.synBulletinUrl") );
	            // 创建名/值组列表  
	            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  

	            parameters.add(new BasicNameValuePair( "xmlContent", writer.toString() ));  
	            // 创建UrlEncodedFormEntity对象  
	            UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity( parameters , "UTF-8");  
	            httpPost.setEntity(formEntiry); 
	            
				// 创建包含connection pool与超时设置的client
				CloseableHttpResponse closeableHttpResponse  = httpClient.execute(httpPost);
				closeableHttpResponse.getEntity().getContent();
				String xmlContentresp = IOUtils.toString(closeableHttpResponse.getEntity().getContent());
				if(xmlContentresp.contains("operTag")){
					String operTag = StringUtils.substringBetween(xmlContentresp, "<operTag>", "</operTag>");
					if("Y".equals(operTag)){//成功   
						//更新同步状态 
						bulletinData.setSynStatus( BuyerData.SYNSTATUS_SUCCESS );
						bulletinDataDao.save( bulletinData );
						result = true;
					}else{
						
						logger.error("采购公告【{}|{}】同步失败！", bulletinData.getId() , bulletinData.getAnnouncementDate());
					}
				}
				//httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("采购公告【{}|{}】同步接口连接错误:"+e.getStackTrace(),bulletinData.getId() , bulletinData.getAnnouncementDate());
			}
		}
		return result;
	}
	
	public boolean sysBulletinProccess(ProjectData projectData){
		boolean result = false;
		
		BulletinData bulletinData  = projectData.getBulletinDataSelected();
		
			//设置bulletinData 
			//bulletinData.setBulletinUrl("http://gxoa.cc/attachmentDownload.do?filePath="+bulletinData.getAttachmentPath()+"&amp;fileName="+bulletinData.getAttachmentName());
			
			//发送
	        StringWriter writer = new StringWriter();  
	        
	        BodyXml<BulletinData> bodyXml = new BodyXml<BulletinData>();
	        bodyXml.setProjectInfo(bulletinData);
	        BulletinXml bulletinXml =  new BulletinXml();
	        bulletinXml.setBody(bodyXml);
	        
	        try {
				marshaller.marshal(bulletinXml, writer);
			} catch (JAXBException e) {
				logger.error("采购公告{}|{}XML对象转换错误:"+e.getStackTrace(), bulletinData.getId() , bulletinData.getAnnouncementDate() );
			}  
			try {
				
				HttpPost httpPost = new HttpPost( propertiesLoader.getProperty("syn.synBulletinUrl") );
	            // 创建名/值组列表  
	            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  

	            parameters.add(new BasicNameValuePair( "xmlContent", writer.toString() ));  
	            // 创建UrlEncodedFormEntity对象  
	            UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity( parameters , "UTF-8");  
	            httpPost.setEntity(formEntiry); 
	            
				// 创建包含connection pool与超时设置的client
				CloseableHttpResponse closeableHttpResponse  = httpClient.execute(httpPost);
				closeableHttpResponse.getEntity().getContent();
				String xmlContentresp = IOUtils.toString(closeableHttpResponse.getEntity().getContent());
				if(xmlContentresp.contains("operTag")){
					String operTag = StringUtils.substringBetween(xmlContentresp, "<operTag>", "</operTag>");
					if("Y".equals(operTag)){//成功   
						//更新同步状态 
						bulletinData.setSynStatus( BuyerData.SYNSTATUS_SUCCESS );
						bulletinDataDao.save( bulletinData );
						//更新项目中的采购人信息成功状态
						projectData.setSynStatus(ProjectData.SYNSTATUS_BULLETIN_SUCCESS );
						projectDataDao.save(projectData);
						result = true;
					}else{
						logger.error("采购公告【{}|{}】同步失败！", bulletinData.getId() , bulletinData.getAnnouncementDate());
					}
				}
				closeableHttpResponse.close();
				//httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("采购公告【{}|{}】同步接口连接错误:"+e.getStackTrace(),bulletinData.getId() , bulletinData.getAnnouncementDate());
			}
		return result;
	}
	
}
