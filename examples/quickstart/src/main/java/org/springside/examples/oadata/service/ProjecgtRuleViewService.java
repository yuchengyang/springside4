/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.service;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.beanutils.BeanUtils;
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
import org.springside.examples.oadata.entity.ProjectDocView;
import org.springside.examples.oadata.entity.ProjectRulePkgView;
import org.springside.examples.oadata.entity.ProjectRuleView;
import org.springside.examples.oadata.entity.xmlnode.ProjectRuleXml;
import org.springside.examples.oadata.repository.ProjectDocViewDao;
import org.springside.examples.oadata.repository.ProjectRuleViewDao;
import org.springside.examples.quickstart.entity.ProjectData;
import org.springside.examples.quickstart.entity.xmlnode.BodyXml;
import org.springside.examples.quickstart.entity.xmlnode.HeadXml;
import org.springside.examples.quickstart.repository.ProjectDataDao;
import org.springside.examples.quickstart.service.BuyerDataService;
import org.springside.modules.utils.PropertiesLoader;


// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class ProjecgtRuleViewService {
	
	private static Logger logger = LoggerFactory.getLogger(ProjecgtRuleViewService.class);
	
	static CloseableHttpClient httpClient;
	
	static Marshaller marshaller ;
	
	static {
		//发送
		try {
			JAXBContext context = JAXBContext.newInstance(ProjectRuleXml.class);
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
	private ProjectRuleViewDao projectRuleViewDao;
	
	@Autowired
	private ProjectDocViewDao projectDocViewDao;
	
	@Autowired
	private ProjectDataDao projectDataDao;
	
	@Autowired
	PropertiesLoader propertiesLoader;
	
	public boolean sysProjectDoc(ProjectData projectData){
		boolean result  = false;
		List<ProjectRuleView> projectRuleViews =  projectRuleViewDao.queryProjectRuleView( projectData.getProjectId() );
		 
		ProjectRuleView projectRuleView = null;
		if(projectRuleViews != null && projectRuleViews.size() >0  ){
			
			projectRuleView = projectRuleViews.get(0);
			
			//是否分包
			projectRuleView.setIsPack( projectData.getProjectPkgDatas().size() > 0? "1":"0" );
			if( projectData.getProjectPkgDatas().size() > 0 ){
				
				for(ProjectRuleView projRView :  projectRuleViews){
					ProjectRulePkgView projectRulePkgView = new ProjectRulePkgView();
					try {
						BeanUtils.copyProperties(projectRulePkgView, projRView);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					projectRuleView.getPack().add(projectRulePkgView);
				}
			}
			
			//采购文件地址
			List<ProjectDocView> projectDocViews = projectDocViewDao.queryProjectDocView( projectData.getProjectId() , "2");
			 
			if( projectDocViews != null && projectDocViews.size() >0 ){//设置招标文件地址
				projectRuleView.setPurchaseDocUrl( projectDocViews.get(0).getAttachmentPath()  );
			}
			
			//发送
	        StringWriter writer = new StringWriter();  
	        
	        BodyXml<ProjectRuleView> bodyXml = new BodyXml<ProjectRuleView>();
	        bodyXml.setProjectInfo(projectRuleView);
	        ProjectRuleXml projectXml =  new ProjectRuleXml();
	        projectXml.setBody(bodyXml);
	        HeadXml header = new HeadXml( projectData.getDepartmentId() ,projectData.getCreator() );
	        projectXml.setHeader(header);
	        
	        try {
				marshaller.marshal(projectXml, writer);
			} catch (JAXBException e) {
				logger.error("采购项目规则{}|{}XML对象转换错误:"+e.getStackTrace(), projectData.getId() , projectData.getProjectName() );
			}  
			try {
				
				HttpPost httpPost = new HttpPost( propertiesLoader.getProperty("syn.synProjectRuleUrl") );
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
						projectData.setSynStatus( ProjectData.SYNSTATUS_DOC_SUCCESS );
						projectDataDao.save( projectData );
						result = true;
					}else{
						logger.error("采购项目规则【{}|{}】同步失败！", projectData.getId() , projectData.getProjectName());
					}
				}
				closeableHttpResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("采购项目规则【{}|{}】同步接口连接错误:"+e.getStackTrace(),projectData.getId() , projectData.getProjectName());
			}
		}
		return result;
	}
	
}
