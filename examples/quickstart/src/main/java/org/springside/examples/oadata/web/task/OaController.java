/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.web.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.examples.oadata.entity.ApplyCompanyView;
import org.springside.examples.oadata.entity.ProjectDocView;
import org.springside.examples.oadata.entity.ProjectStepTypeView;
import org.springside.examples.oadata.entity.TenderView;
import org.springside.examples.oadata.repository.AppCompanyViewDao;
import org.springside.examples.oadata.repository.ProjectDocViewDao;
import org.springside.examples.oadata.repository.ProjectStepTypeViewDao;
import org.springside.examples.oadata.repository.TenderViewDao;
import org.springside.examples.quickstart.entity.ProjectData;
import org.springside.examples.quickstart.repository.ProjectDataDao;
import org.springside.modules.utils.PropertiesLoader;
import org.springside.modules.web.Servlets;

import com.guoxin.business.FTP.util.ContinueFTP;


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
	private static Logger logger = LoggerFactory.getLogger(OaController.class);

	@Autowired
	PropertiesLoader propertiesLoader;
	
	@Autowired
	private AppCompanyViewDao appCompanyViewDao;
	
	@Autowired
	private TenderViewDao TenderViewDao;
	
	@Autowired
	private ProjectDataDao projectDataDao;
	
	@Autowired
	TenderViewDao tenderViewDao;
	
	@Autowired
	ProjectDocViewDao projectDocViewDao;
	
	@Autowired
	ProjectStepTypeViewDao projectStepTypeViewDao;
	
	@RequestMapping(value = "getProjectDetail" , method = RequestMethod.GET)
	public String getProjectDetail(Model model,HttpServletRequest request , @RequestParam(value = "projectId") String projectId) {
		ProjectData projectData = projectDataDao.getProject(projectId);
		
		model.addAttribute("projectStepTypeViews", projectStepTypeViewDao.getStep(Long.parseLong(projectId)));
		
		List<ProjectStepTypeView> lastedProjectStepTypeViews = projectStepTypeViewDao.getLastedStep(Long.parseLong(projectId) );
		List<ProjectStepTypeView> activeProjectStepTypeViews = projectStepTypeViewDao.getActivStep(Long.parseLong(projectId) );
		model.addAttribute("lastedProjectStepTypeView", lastedProjectStepTypeViews!=null && lastedProjectStepTypeViews.size()>0 ? lastedProjectStepTypeViews.get(0):null);
		model.addAttribute("activeProjectStepTypeView", activeProjectStepTypeViews!=null && activeProjectStepTypeViews.size()>0 ? activeProjectStepTypeViews.get(0):null);

		model.addAttribute("projectData", projectData);
		
		List<ProjectDocView>  projectDocViews = projectDocViewDao.queryProjectDocView(projectId);
		model.addAttribute("projectDocViews", projectDocViews);
		return "oa/projectDetail";
	}
	
	@RequestMapping(value = "div/getProjectTender" , method = RequestMethod.GET)
	public String getProjectTender(Model model,HttpServletRequest request , @RequestParam(value = "projectId") String projectId , @RequestParam(value = "sectionId" ,required=false) String sectionId) {
		List<ApplyCompanyView> appCompanyViews = null;
		if(sectionId!=null && !"".equals(sectionId)){
			appCompanyViews = appCompanyViewDao.getAppCompanyView( Long.parseLong(projectId ) , Long.parseLong(sectionId.replace("S_", "")) );
		}else{
			appCompanyViews = appCompanyViewDao.getAppCompanyView( Long.parseLong(projectId ) );
		}
		if(appCompanyViews!=null &&  appCompanyViews.size()>0 ){
			 for (ApplyCompanyView applyCompanyView : appCompanyViews){
				 TenderView  tenderView = tenderViewDao.findOne(applyCompanyView.getCompanyId());
				 applyCompanyView.setCompanyName(tenderView.getTenderName());
			 }
		}
		model.addAttribute("appCompanyViews", appCompanyViews);
		return "oa/projectTender";
	}
	
	@RequestMapping(value = "getProjectFile" , method = RequestMethod.GET)
	public String getProjectFile(Model model,HttpServletRequest request ,HttpServletResponse resp,  @RequestParam(value = "filePath") String filePath ) {
        ContinueFTP myFtp = new ContinueFTP();
        InputStream in  = null;
        OutputStream os = null;
        byte[] attachment = null;
        try {
   			myFtp.connect(
   					propertiesLoader.getProperty("oa.ftp.url"), 
   					Integer.parseInt( propertiesLoader.getProperty("oa.ftp.port")),
   					propertiesLoader.getProperty("oa.ftp.username"),
   					propertiesLoader.getProperty("oa.ftp.password"));
   			String path = propertiesLoader.getProperty("oa.ftp.resUploadFilePath") + filePath;
   			logger.info("path:{} ,name:{}" ,path ,filePath);
   			in = myFtp.download( path , filePath);
            if(in!=null){
            	int len = in.available();
	        		attachment = new byte[len];
	        		in.read(attachment);
            	}
            os = resp.getOutputStream();
            
            Servlets.setFileDownloadHeader(request, resp, filePath);;
            
            os.write( attachment );
            os.close();
   		} catch (IOException e) {
   				e.printStackTrace();
   				logger.error("连接FTP时出错");
   		} finally {
   			try {
   				myFtp.disconnect();
   			} catch (IOException e) {
   				logger.error("断开FTP时IO异常");
   			}
   		}
		logger.info("filePath："+filePath+ "获取文件size：" + ( attachment!= null ? attachment.length : 0 ));
		return null;
	}
	
}
