/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.web.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.utils.PropertiesLoader;

import com.guoxin.business.FTP.util.ContinueFTP;

@Controller
@RequestMapping(value = "/api")
public class ApiListController {
	private static Logger logger = LoggerFactory.getLogger(ApiListController.class);
	
	@Autowired
	PropertiesLoader propertiesLoader;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "api/list";
	}
	
	@RequestMapping(value = "getFileFromFTP", method = RequestMethod.POST)
	public void getFileFromFTP(@RequestParam(value="filePath" , required = true ) String filePath ,@RequestParam(value="key" , required = true )String key , String fileName  , HttpServletResponse resp ){
		try{
	        String salt = "GXCX_OA_SALT";
			key = new String (Base64.decodeBase64(key) ) ;
			long keyTime = Long.parseLong(key.replace(salt, "") ) ;
			// ( ( new Date().getTime() * 4 +2 ) * 5 -1 ) * 8 + 3 
			keyTime = ( ( ( keyTime -3 )/8 +1 ) /5 -2 )/4 ;// 约定算法  一定要注意两遍服务器的时间一定要同步   查看时间：date  同步时间：ntpdate cn.pool.ntp.org   
			long currentTime = new Date().getTime();
			if(! (  currentTime - 60000 < keyTime && keyTime < currentTime + 60000 )){
				throw new Exception("服务器时间校验失败！");
			}
		} catch (Exception e) {
			System.out.println("FTP请求校验出错!"+ e.getMessage());
			logger.error("FTP请求校验出错!"+e.getMessage());
			return ;
		}
		
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
   			logger.info("path:{} ,name:{}" ,path ,fileName);
   			in = myFtp.download( path , fileName);
            if(in!=null){
            	int len = in.available();
        		attachment = new byte[len];
        		in.read(attachment);
            }
            os = resp.getOutputStream();
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
	}
}
