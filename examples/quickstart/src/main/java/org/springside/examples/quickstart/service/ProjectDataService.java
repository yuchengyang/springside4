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
import org.springside.examples.oadata.repository.BulletinDocViewDao;
import org.springside.examples.oadata.service.ProjecgtRuleViewService;
import org.springside.examples.quickstart.entity.ProjectData;
import org.springside.examples.quickstart.entity.xmlnode.BodyXml;
import org.springside.examples.quickstart.entity.xmlnode.HeadXml;
import org.springside.examples.quickstart.entity.xmlnode.ProjectXml;
import org.springside.examples.quickstart.repository.BulletinDataDao;
import org.springside.examples.quickstart.repository.ProjectDataDao;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.utils.PropertiesLoader;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class ProjectDataService {

	private static Logger logger = LoggerFactory.getLogger(ProjectDataService.class);
	
	static CloseableHttpClient httpClient;
	
	static Marshaller marshaller ;
	
	static {
		//发送
		try {
			JAXBContext context = JAXBContext.newInstance(ProjectXml.class);
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
	PropertiesLoader propertiesLoader;

	@Autowired
	private ProjectDataDao projectDataDao;
	
	@Autowired
	private BulletinDocViewDao bulletinDocViewDao;
	
	@Autowired
	private BulletinDataDao bulletinDataDao;
	
	@Autowired
	private BulletinDataService bulletinDataService;
	
	@Autowired
	private BuyerDataService buyerDataService;
	
	@Autowired
	private ProjecgtRuleViewService projecgtRuleViewService;
	
	
	public Page<ProjectData> getProjectData( Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<ProjectData> spec = buildSpecification( searchParams);
		return projectDataDao.findAll(spec, pageRequest);
	}
	
	/**创建分页请求*/
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		} else {
			sort = new Sort(Direction.DESC, "delegateDate");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<ProjectData> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("projType", new SearchFilter("projType", Operator.EQ, ProjectData.PROJTYPE_PROJ));
		Specification<ProjectData> spec = DynamicSpecifications.bySearchFilter(filters.values(), ProjectData.class);
		return spec;
	}
	
	/**
	 * 向阳光易购发送项目数据
	 * @param ids
	 * @return
	 */
	public boolean synProject(String[] ids) {
		//方法执行结果
		
		boolean resultreturn = true;

		for(String id : ids){
			boolean result = true;
			
			String [] idArray = id.split(":");
			
			long projId = new Long(idArray[0] );
			ProjectData projectData = projectDataDao.findOne( projId );
			
			if( idArray.length > 1 ){
				long attachId = new Long( idArray[1] );
				projectData.setBulletinDataSelected( bulletinDataDao.findOne(attachId) );
			}
			
			if(ProjectData.SYNSTATUS_STANBY  >=  projectData.getSynStatus()  ){
				//同步采购人
				result = result && buyerDataService.synBuyerProccess( projectData );
			}
			if(ProjectData.SYNSTATUS_BUYERINFO_SUCCESS  >=  projectData.getSynStatus()  ){
				//同步项目
				result = result && sysProjectProccess( projectData );
			}
			if(ProjectData.SYNSTATUS_BASEINFO_SUCCESS >= projectData.getSynStatus() ){
				//同步招标文件
				result = result && projecgtRuleViewService.sysProjectDoc(projectData);
			}
			if(ProjectData.SYNSTATUS_DOC_SUCCESS >= projectData.getSynStatus() ){
				//同步公告
				if( projectData.getBulletinDataSelected() != null ){
					result = result && bulletinDataService.sysBulletinProccess( projectData );
				}
			}
			resultreturn = resultreturn &  result;
		}
		return resultreturn;
	}
	
	public boolean sysProjectProccess( ProjectData projectData){
		boolean result = false;
		//发送
        StringWriter writer = new StringWriter();  
        
        BodyXml<ProjectData> bodyXml = new BodyXml<ProjectData>();
        bodyXml.setProjectInfo(projectData);
        ProjectXml projectXml =  new ProjectXml();
        projectXml.setBody(bodyXml);
        HeadXml header = new HeadXml( projectData.getDepartmentId() ,projectData.getCreator() );
        projectXml.setHeader(header);
        
        try {
			marshaller.marshal(projectXml, writer);
		} catch (JAXBException e) {
			logger.error("采购项目{}|{}XML对象转换错误:"+e.getStackTrace(), projectData.getId() , projectData.getProjectName() );
		}  
		try {
			
			HttpPost httpPost = new HttpPost( propertiesLoader.getProperty("syn.synProjectUrl") );
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
					projectData.setSynStatus( ProjectData.SYNSTATUS_BASEINFO_SUCCESS );
					projectDataDao.save( projectData );
					result = true;
				}else{
					logger.error("采购项目【{}|{}】同步失败！", projectData.getId() , projectData.getProjectName());
				}
			}
			closeableHttpResponse.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("采购项目【{}|{}】同步接口连接错误:"+e.getStackTrace(),projectData.getId() , projectData.getProjectName());
		}
		return result;
	}

}
