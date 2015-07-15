/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.schedule.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.oadata.entity.BulletinView;
import org.springside.examples.oadata.entity.BuyerView;
import org.springside.examples.oadata.entity.ProjectPkgView;
import org.springside.examples.oadata.entity.ProjectView;
import org.springside.examples.oadata.repository.BulletinViewDao;
import org.springside.examples.oadata.repository.BuyerViewDao;
import org.springside.examples.oadata.repository.ProjectViewDao;
import org.springside.examples.quickstart.entity.BulletinData;
import org.springside.examples.quickstart.entity.BuyerData;
import org.springside.examples.quickstart.entity.ProjectData;
import org.springside.examples.quickstart.entity.ProjectPkgData;
import org.springside.examples.quickstart.repository.BulletinDataDao;
import org.springside.examples.quickstart.repository.BuyerDataDao;
import org.springside.examples.quickstart.repository.ProjectDataDao;


// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class ScheduleService {
	
	static final int STEPJUMPBUYERNUM = 500;
	static final int STEPJUMPPROJECTNUM = 2;
	static final int STEPJUMPBULLETINNUM = 2;

	
	/*抓取频率  采购人以个为单位 一次抓500个*/
	static int stepJumpBuyer = STEPJUMPBUYERNUM ;
	/*抓取频率  项目以月为单位 2个月*/
	static int stepJumpProject = STEPJUMPPROJECTNUM;
	/*抓取频率  以月为单位 2个月*/
	static int stepJumpBulletin = STEPJUMPBULLETINNUM;
	
	static Date backSynProjectFromDate = null;
	
	static Date backSynBulletinFromDate = null;


	@Autowired
	private BulletinViewDao bulletinViewDao;
	@Autowired
	private BulletinDataDao bulletinDataDao;
	@Autowired
	private ProjectViewDao projectViewDao;
	@Autowired
	private ProjectDataDao projectDataDao;
	@Autowired
	private BuyerViewDao buyerViewDao;
	@Autowired
	private BuyerDataDao buyerDataDao;
	
	public void updateGetedBulletin() throws Exception {
		//选出最大的发公告时间,也就是 已更新至XX时间
		Date maxAnnouncementDate = bulletinDataDao.findMaxAnnouncementDate();
		
		//查询开始时间
		Date getFrom;
		if( maxAnnouncementDate != null ){//以前同步过 ，则取已经  更新至 XX时间
			getFrom = maxAnnouncementDate;
		}else{//从未同步过
			getFrom = DateUtils.addDays(bulletinViewDao.findMinAnnouncementDate(), -1);
		}
		//查询结束时间 (以一个月为step)
		Date getTo = DateUtils.addMonths(getFrom, stepJumpBulletin);
		
		//取出来
		List<BulletinView> bulletinViews = bulletinViewDao.getBulletinViewFromToTime(getFrom , getTo);
		
		//本地存储
		List<BulletinData> bulletinDatas = new ArrayList<BulletinData>();
		if(bulletinViews!=null && bulletinViews.size()>0){
			for(BulletinView bulletinView : bulletinViews){
				BulletinData dest = new BulletinData();
				BeanUtils.copyProperties(dest, bulletinView);
				dest.setSynStatus(BulletinData.SYNSTATUS_STANBY);
				bulletinDatas.add(dest); 
			}
		}
		//保存
		bulletinDataDao.save( bulletinDatas );
		System.out.println("抓取公告数据"+bulletinDatas.size()+"条，step="+stepJumpBulletin);
		//step计数器
		if(bulletinDatas.size() == 0 && getTo.before( DateUtils.addMonths(new Date(), 12) )){//最多抓从今日至一年后的数据
			stepJumpBulletin += stepJumpBulletin;
		}else{
			stepJumpBulletin = STEPJUMPBULLETINNUM;
		}
	}
	
	public void updateGetedProject() throws Exception {
		//选出最大的立项时间,也就是 已更新至XX时间
		Date maxDelegateDate = projectDataDao.findMaxDelegateDate();
		
		//查询开始时间
		Date getFrom;
		if( maxDelegateDate != null ){//以前同步过 ，则取已经  更新至 XX时间
			getFrom = maxDelegateDate;
		}else{//从未同步过
			getFrom = DateUtils.addDays(projectViewDao.findMinDelegateDate(), -1);
		}
		//查询结束时间 (以一个月为step)
		Date getTo = DateUtils.addMonths(getFrom, stepJumpProject);
		
		//取出来
		List<ProjectView> projectViews = projectViewDao.getProjectViewFromToTime(getFrom , getTo);
		
		//本地存储
		List<ProjectData> projectDatas = new ArrayList<ProjectData>();
		List<ProjectPkgData> projectPkgDatas = new ArrayList<ProjectPkgData>();
		this.buildProjectAndPkg(projectViews, projectDatas, projectPkgDatas);
		
		//保存
		projectDataDao.save( projectDatas );
		projectDataDao.save( projectPkgDatas );
		
		//此处有一个特殊处理逻辑：回溯, 因为有些数据会在当前抓取日期之后冒出来，而当前最大日期 getFrom 却已经跨过  这些数据的创建时间， 这样的话这些数据是抓取不到的
		Date currentDate = new Date();
		if( getTo .after( currentDate ) ){//只有getTo 时间 大于当前时间，才走回溯逻辑
			if( backSynProjectFromDate == null ){
				backSynProjectFromDate = currentDate;//第一次赋值为当前时间
			}
			long oaprojCount = projectViewDao.countBeforeDate( backSynProjectFromDate );
			long projCount = projectDataDao.countBeforeDate( backSynProjectFromDate );
			if(oaprojCount != projCount){
				
				System.out.println("oaprojCount:"+oaprojCount +", projCount:"+projCount);
				//继续回溯 
				
				//回溯时间-7  至  回溯时间 内的项目（oa）
				List<ProjectView> oaProjectViews = projectViewDao.getProjectViewFromToTime( DateUtils.addDays( backSynProjectFromDate , -7 ), backSynProjectFromDate);
				//回溯时间-7  至  回溯时间 内的项目（local）
				List<ProjectData> localProjectDatas = projectDataDao.getProjectViewFromToTime( DateUtils.addDays( backSynProjectFromDate , -7 ), backSynProjectFromDate);
				
				//不存在于local 中
				List<ProjectView> notInLocalProjectViews = new ArrayList<ProjectView>();
				//不存在于oa    中
				List<ProjectData> notInOaProjectDatas = new ArrayList<ProjectData>();

				System.out.println("projectviews:"+ oaProjectViews.size() +",projectdatas:"+localProjectDatas.size());
				//遍历第一次
				for( ProjectView projectView : oaProjectViews ){
					boolean inLocal = false;
					for( ProjectData projectData : localProjectDatas ){
						if(StringUtils.equals(projectView.getProjectId(), projectData.getProjectId() )){
							inLocal = true;
							break;
						}
					}
					if(!inLocal){
						System.out.println("notInLocalProjectViewId:"+ projectView.getProjectId());
						notInLocalProjectViews.add(projectView);
					}
				}
				//遍历第二次
				for( ProjectData projectData : localProjectDatas ){
					boolean inOa = false;
					for( ProjectView projectView : oaProjectViews ){
						if(StringUtils.equals( projectData.getProjectId(), projectView.getProjectId())){
							inOa = true;
							break;
						}
					}
					if(!inOa){
						System.out.println("notInOaProjectDataId:"+ projectData.getProjectId());
						notInOaProjectDatas.add(projectData);
					}
				}
				
				//补足不在local中的
				if(notInLocalProjectViews != null && notInLocalProjectViews.size() > 0 ){
					List<ProjectData> projectDataBks = new ArrayList<ProjectData>();
					List<ProjectPkgData> projectPkgDataBks = new ArrayList<ProjectPkgData>();
					this.buildProjectAndPkg(notInLocalProjectViews, projectDataBks, projectPkgDataBks);
					projectDataDao.save( projectDataBks );
					projectDataDao.save( projectPkgDataBks );
					System.out.println("回溯抓取项目数据"+projectDataBks.size()+"条, 包数据："+projectPkgDataBks.size()+"条");
				}
				
				//删除不在oa中的
				if(notInOaProjectDatas != null && notInOaProjectDatas.size() >0 ){
					for(ProjectData projectData : notInOaProjectDatas){
						projectData.setUseStatus(ProjectData.USESTATUS_INVALID);//设置删除
						//TODO 要不要关联同步属性
					}
					projectDataDao.save(notInOaProjectDatas);
					System.out.println("回溯删除项目/包数据"+notInOaProjectDatas.size()+"条");
				}
				
				backSynProjectFromDate =  DateUtils.addDays( backSynProjectFromDate , -7 ) ;//将回溯日期往前推
			} else{
				//已经相等则此次回溯完成 重新设置  backSynProjectFromDate 为null
				backSynProjectFromDate  =null ;
			}
			System.out.println("已回溯至：" + backSynProjectFromDate);
		}
		System.out.println("抓取项目数据"+projectDatas.size()+"条, 包数据："+projectPkgDatas.size()+"条，step="+stepJumpProject);
		//step计数器
		if(projectDatas.size() == 0  && getTo.before( DateUtils.addMonths(new Date(), 12) ) ){//最多抓从今日至一年后的数据
			stepJumpProject += stepJumpProject;
		}else{
			stepJumpProject = STEPJUMPPROJECTNUM;
		}
	}
	
	/**
	 * 装配  project  和  package 
	 * @param projectViews
	 * @param projectDatas
	 * @param projectPkgDatas
	 */
	public void buildProjectAndPkg(List<ProjectView> projectViews ,  List<ProjectData>  projectDatas , List<ProjectPkgData> projectPkgDatas ){
		
		if(projectViews!=null && projectViews.size()>0){
			for(ProjectView projectView : projectViews){
				if(projectView instanceof ProjectPkgView){
					ProjectPkgView projectPkgView = (ProjectPkgView)projectView;
					System.out.println("包ID:"+projectPkgView.getProjectId()+ ", 名称:"+projectPkgView.getProjectName());
					ProjectPkgData dest = new ProjectPkgData();
					org.springframework.beans.BeanUtils.copyProperties(projectPkgView, dest,"parentProject");
					if( projectPkgView.getParentProject() != null ){
						ProjectData parentDest = new ProjectData();
						parentDest.setProjectId( projectPkgView.getParentProject().getProjectId());
						dest.setParentProject(parentDest);
					}
					dest.setSynStatus(ProjectData.SYNSTATUS_STANBY);
					dest.setUseStatus(ProjectData.USESTATUS_VALID);
					projectPkgDatas.add(dest);
				} else{
					System.out.println("项目ID:"+projectView.getProjectId() +", 名称:"+projectView.getProjectName());
					ProjectData dest = new ProjectData();
					org.springframework.beans.BeanUtils.copyProperties(projectView, dest, "parentProject");
					dest.setSynStatus(ProjectData.SYNSTATUS_STANBY);
					dest.setUseStatus(ProjectData.USESTATUS_VALID);
					dest.setProjectPkgDatas(null);
					projectDatas.add(dest);
				}
			}
		}

		for(ProjectPkgData projectPkgData: projectPkgDatas){
			boolean match  = false;
			for ( ProjectData  projectData: projectDatas){
				if (projectData.getProjectId()  .equals( projectPkgData.getParentProject().getProjectId()  ) ){
					projectPkgData.setParentProject(projectData);
					match = true;
					break;
				}
			}
			
			if( !match ){
				ProjectData  parentProject =  projectDataDao.getProject( projectPkgData.getParentProject().getProjectId() );
				if(parentProject != null){
					projectPkgData.setParentProject( parentProject );
					match = true;
				}
			}
			
			System.out.println("nomatch:"+ match +":id"+ projectPkgData.getProjectId() + "pid:"+projectPkgData.getParentProject().getProjectId() );
		}
	}
	
	public void updateGetedBuyer() throws Exception{
		//选出最大的立项时间,也就是 已更新至XX时间
		Integer maxCustomerId = buyerDataDao.findMaxCustomerId();
		
		//查询开始时间
		Integer getFrom;
		if( maxCustomerId != null ){//以前同步过 ，则取已经  更新至 XX时间
			getFrom = maxCustomerId;
		}else{//从未同步过
			getFrom = buyerViewDao.findMinCustomerId()-1 ;
		}
		//查询结束时间 (以500为step)
		Integer getTo = getFrom + stepJumpBuyer;
		
		//取出来
		List<BuyerView> buyertViews = buyerViewDao.getBuyerViewFromToID(getFrom , getTo);
		
		//本地存储
		List<BuyerData> buyerDatas = new ArrayList<BuyerData>();
		if(buyertViews!=null && buyertViews.size()>0){
			for(BuyerView buyerView : buyertViews){
				BuyerData dest = new BuyerData();
				BeanUtils.copyProperties(dest, buyerView);
				dest.setSynStatus( BuyerData.SYNSTATUS_STANBY );//初始同步状态为未开始
				buyerDatas.add(dest);
			}
		}
		//保存
		buyerDataDao.save( buyerDatas );
		System.out.println("抓取采购人数据"+buyerDatas.size()+"条，step="+stepJumpBuyer);

		//step计数器
		if(buyerDatas.size() == 0 && stepJumpBuyer <=  300000 ){
			stepJumpBuyer += stepJumpBuyer;
		}else{
			stepJumpBuyer = STEPJUMPBUYERNUM;
		}
	}
}
