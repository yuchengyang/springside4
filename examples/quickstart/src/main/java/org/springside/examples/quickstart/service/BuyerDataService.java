/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.service;

import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
import org.springside.examples.oadata.repository.BuyerViewDao;
import org.springside.examples.quickstart.entity.BuyerData;
import org.springside.examples.quickstart.entity.ProjectData;
import org.springside.examples.quickstart.entity.xmlnode.BuyerXml;
import org.springside.examples.quickstart.repository.BuyerDataDao;
import org.springside.examples.quickstart.repository.ProjectDataDao;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.utils.PropertiesLoader;


// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class BuyerDataService {
	
	private static Logger logger = LoggerFactory.getLogger(BuyerDataService.class);
	
	static Marshaller marshaller ;
	
	static CloseableHttpClient httpClient;
	
	@Autowired
	PropertiesLoader propertiesLoader;
	
	static {
		//发送
		try {
			JAXBContext context = JAXBContext.newInstance(BuyerXml.class);
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
	private BuyerDataDao buyerDataDao;
	
	@Autowired
	private BuyerViewDao buyerViewDao;
	
	@Autowired
	private ProjectDataDao projectDataDao;
	
	public Page<BuyerData> getBuyerData( Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<BuyerData> spec = buildSpecification( searchParams);
		return buyerDataDao.findAll(spec, pageRequest);
	}
	
	/**创建分页请求*/
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		} else {
			sort = new Sort(Direction.ASC, "customerId");

		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<BuyerData> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		//filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<BuyerData> spec = DynamicSpecifications.bySearchFilter(filters.values(), BuyerData.class);
		return spec;
	}

//	/**
//	 * 向阳光易购发送采购人数据
//	 * @param ids
//	 */
//	public boolean synBuyer(Long[] ids) {
//		//方法执行结果
//		boolean result = false;
//		List<Long> idls = new ArrayList<Long>();
//		CollectionUtils.addAll(idls, ids);
//		Iterable<BuyerData> buyerDatas = buyerDataDao.findAll(idls);
//		for (BuyerData buyerData:   buyerDatas){
//			//发送
//	        StringWriter writer = new StringWriter();  
//	        
//	        BodyXml<BuyerData> bodyXml = new BodyXml<BuyerData>();
//	        bodyXml.setBuyerInfo(buyerData);
//	        BuyerXml buyerXml =  new BuyerXml();
//	        buyerXml.setBody(bodyXml);
//	        
//	        try {
//				marshaller.marshal(buyerXml, writer);
//			} catch (JAXBException e) {
//				logger.error("采购人{}|{}XML对象转换错误:"+e.getStackTrace(), buyerData.getId() , buyerData.getCustomerName() );
//			}  
//			try {
//				
//				HttpPost httpPost = new HttpPost( propertiesLoader.getProperty("syn.synBuyerUrl") );
//	            // 创建名/值组列表  
//	            List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
//
//	            parameters.add(new BasicNameValuePair( "xmlContent", writer.toString() ));  
//	            // 创建UrlEncodedFormEntity对象  
//	            UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity( parameters , "UTF-8");  
//	            httpPost.setEntity(formEntiry); 
//	            
//				// 创建包含connection pool与超时设置的client
//				CloseableHttpResponse closeableHttpResponse  = httpClient.execute(httpPost);
//				closeableHttpResponse.getEntity().getContent();
//				String xmlContentresp = IOUtils.toString(closeableHttpResponse.getEntity().getContent());
//				if(xmlContentresp.contains("operTag")){
//					String operTag = StringUtils.substringBetween(xmlContentresp, "<operTag>", "</operTag>");
//					if("Y".equals(operTag)){//成功   
//						//更新同步状态 
//						buyerData.setSynStatus( BuyerData.SYNSTATUS_SUCCESS );
//						buyerDataDao.save( buyerData );
//						result = true;
//					}else{
//						
//						logger.error("采购人【{}|{}】同步失败！", buyerData.getId() , buyerData.getCustomerName());
//					}
//				}
//				//httpClient.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//				logger.error("采购人【{}|{}】同步接口连接错误:"+e.getStackTrace(),buyerData.getId() , buyerData.getCustomerName());
//			}
//		}
//		return result;
//	}
	
	public boolean synBuyerProccess( ProjectData projectData){
		//方法执行结果
		boolean result = false;
		
//		//采购人的代码不能为空
//		if( projectData.getDelegateCompany() == null ){
//			return result;
//		}
//		
//		BuyerView buyerView  = buyerViewDao.findOne( Integer.parseInt( projectData.getDelegateCompany() ) );
//		
//		//List<BuyerData> buyerDatas = buyerDataDao.findBuyerData( Integer.parseInt( projectData.getDelegateCompany() ) );
//		
//		//if(buyerDatas!=null && buyerDatas.size() > 0 ){
//			//BuyerData buyerData = buyerDatas.get(0);
//			//发送
//			StringWriter writer = new StringWriter();  
//			
//			BodyXml<BuyerView> bodyXml = new BodyXml<BuyerView>();
//			bodyXml.setBuyerInfo(buyerView);
//			BuyerXml buyerXml =  new BuyerXml();
//			buyerXml.setBody(bodyXml);
//			
//			try {
//				marshaller.marshal(buyerXml, writer);
//			} catch (JAXBException e) {
//				logger.error("采购人{}|{}XML对象转换错误:"+e.getStackTrace(), buyerView.getCustomerId() , buyerView.getCustomerName() );
//			}  
//			try {
//				
//				HttpPost httpPost = new HttpPost( propertiesLoader.getProperty("syn.synBuyerUrl") );
//				// 创建名/值组列表  
//				List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
//				
//				parameters.add(new BasicNameValuePair( "xmlContent", writer.toString() ));  
//				// 创建UrlEncodedFormEntity对象  
//				UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity( parameters , "UTF-8");  
//				httpPost.setEntity(formEntiry); 
//				
//				// 创建包含connection pool与超时设置的client
//				CloseableHttpResponse closeableHttpResponse  = httpClient.execute(httpPost);
//				closeableHttpResponse.getEntity().getContent();
//				String xmlContentresp = IOUtils.toString(closeableHttpResponse.getEntity().getContent());
//				if(xmlContentresp.contains("operTag")){
//					String operTag = StringUtils.substringBetween(xmlContentresp, "<operTag>", "</operTag>");
//					if("Y".equals(operTag)){//成功   
//						//更新同步状态 
////						buyerData.setSynStatus( BuyerData.SYNSTATUS_SUCCESS );
////						buyerDataDao.save( buyerData );
//						//更新项目中的采购人信息成功状态
//						projectData.setSynStatus(ProjectData.SYNSTATUS_BUYERINFO_SUCCESS );
//						projectDataDao.save(projectData);
//						result = true;
//					}else{
//						logger.error("采购人【{}|{}】同步失败！", buyerView.getCustomerId() , buyerView.getCustomerName());
//					}
//				}
//				closeableHttpResponse.close();
//				//httpClient.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//				logger.error("采购人【{}|{}】同步接口连接错误:"+e.getStackTrace(),buyerView.getCustomerId() , buyerView.getCustomerName());
//			}
//		//}
			projectData.setSynStatus(ProjectData.SYNSTATUS_BUYERINFO_SUCCESS );
			projectDataDao.save(projectData);
			result = true;
			
		return result;
	}

	public String getBuyerDataName(String delegateCompany) {
		BuyerData buyerData = buyerDataDao.findOne(Long.parseLong(delegateCompany));
		return buyerData!=null ?buyerData.getCustomerName():null;
	}
}
