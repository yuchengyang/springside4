CREATE OR REPLACE VIEW view_oa_service_projectinfo AS 
SELECT
	(case when  ISNULL( pbs.BID_SECTION_ID )= 1 then `r`.`project_id` else concat ( 'S_',pbs.BID_SECTION_ID)  end) AS project_id  , -- `项目id`,
	(case when  ISNULL( pbs.BID_SECTION_ID )= 1 then `r`.PROJECT_CODE else pbs.BID_SECTION_CODE end) AS project_code,	
	(case when  ISNULL( pbs.BID_SECTION_ID )= 1 then `r`.project_name else pbs.BID_SECTION_NAME end) as project_name,
	-- `r`.`project_code` AS project_code, -- `项目编号`,
	-- `r`.`project_name` AS project_name, -- `项目名称`,
	`r`.`project_secret_level` AS project_secret_level, -- `招标方式编号`,
	(CASE `r`.`project_secret_level`
		WHEN 1 THEN
			'公开'
		WHEN 0 THEN
			'邀请'
		END )AS project_secret_level_Name, -- `招标方式名称`,
	`r`.`project_type_id` AS project_type_id, -- `招标类型编号`,
	(CASE `r`.`project_type_id`
		WHEN 'BID' THEN
			'招标'
		WHEN 'PROCUREMENT' THEN
			'采购'
		END )AS project_type_name,-- `招标类型名称`,
	`r`.BID_TYPE, -- 招标类型
	`r`.BID_TYPE_NAME, -- 招标类型名称
	`r`.`bid_content` AS bid_content,-- `招标内容`,
	`r`.`investment_scale_foreign` AS investment_scale_foreign, -- `项目投资规模（万元）`,
	`r`.`organization_name` AS organization_name, -- `招标部门`,
	`r`.DEPARTMENT_ID,-- 招标部门代码
	`r`.DELEGATE_COMPANY,-- 委托单位（委托单位的机构代码）
	 rc.CUSTOMER_CORPORATE,	--  预算单位联系人
   	 rc.CUSTOMER_TELEPHONE,	--  预算单位联系电话
	pp.CREATE_DATE as DELEGATE_DATE,-- 立项日期 （编号日期是否就是立项日期? 或者 委托日期是否就是立项日期?）
	`r`.DELEGATE_AMOUNT,-- 项目投资规模（单位：元）
	`r`.`creator_name` -- AS `项目经理`,
   ,`r`.CREATOR -- AS `项目经理id`,
	,(case  when  ISNULL(pbs.BID_SECTION_ID)=1 then 'm' 
	else 's'
	 end) as proj_type 
   , pbs.PROJECT_ID as PROJECT_parent_id-- 父级项目
FROM
	`report_project` `r` 
left join res_customer rc on rc.CUSTOMER_ID = `r`.DELEGATE_COMPANY
LEFT join pro_bid_section pbs on `r`.PROJECT_ID = pbs.PROJECT_ID
, pro_project   pp 
WHERE
 `r`.PROJECT_ID = pp.PROJECT_ID 
and `r`.`finished_approval` = 1
AND `r`.`project_type_id` IN('BID', 'PROCUREMENT')
-- AND `r`.`organization_type` = 3
-- AND `r`.`not_include` = 0

UNION all 

	select 
	r.project_id  , -- `项目id`,
	r.`project_code` AS project_code, -- `项目编号`,
	r.`project_name` AS project_name, -- `项目名称`,
	r.`project_secret_level` AS project_secret_level, -- `招标方式编号`,
	(CASE r.`project_secret_level`
		WHEN 1 THEN
			'公开'
		WHEN 0 THEN
			'邀请'
		END )AS project_secret_level_Name, -- `招标方式名称`,
	r.`project_type_id` AS project_type_id, -- `招标类型编号`,
	(CASE r.`project_type_id`
		WHEN 'BID' THEN
			'招标'
		WHEN 'PROCUREMENT' THEN
			'采购'
		END )AS project_type_name,-- `招标类型名称`,
	r.BID_TYPE, -- 招标类型
	r.BID_TYPE_NAME, -- 招标类型名称
	r.`bid_content` AS bid_content,-- `招标内容`,
	r.`investment_scale_foreign` AS investment_scale_foreign, -- `项目投资规模（万元）`,
	r.`organization_name` AS organization_name, -- `招标部门`,
	r.DEPARTMENT_ID,-- 招标部门代码
	r.DELEGATE_COMPANY,-- 委托单位（委托单位的机构代码）
	rc.CUSTOMER_CORPORATE,	--  预算单位联系人
  rc.CUSTOMER_TELEPHONE,	--  预算单位联系电话
	pp.CREATE_DATE as DELEGATE_DATE,-- 立项日期 （编号日期是否就是立项日期? 或者 委托日期是否就是立项日期?）
	r.DELEGATE_AMOUNT,-- 项目投资规模（单位：元）
	r.`creator_name` -- AS `项目经理`,
   ,r.CREATOR -- AS `项目经理id`,
	,'m'as proj_type 
   , null as PROJECT_parent_id -- 父级项目
   from report_project r 
	left join res_customer rc on rc.CUSTOMER_ID = r.DELEGATE_COMPANY
, pro_project   pp 
where 	
 `r`.PROJECT_ID = pp.PROJECT_ID 
and r.`finished_approval` = 1
AND r.`project_type_id` IN('BID', 'PROCUREMENT')
-- AND r.`organization_type` = 3
-- AND r.`not_include` = 0 
and EXISTS ( select '1' from pro_bid_section pbss where pbss.PROJECT_ID = r.PROJECT_ID );

create or replace view view_oa_service_customer as
SELECT distinct `r`.`customer_id`                  ,-- AS `招标人id`,
       `r`.`customer_name`                ,-- AS `招标人名称`,
	   -- 缺少 招标人代码
       `r`.`customer_parent_organization` ,-- AS `上级单位`,
       `r`.`customer_industry`           ,--  AS `所属行业id`,
       `r`.`customer_mail`               ,--  AS `企业邮箱`,
       `r`.`customer_address`            ,--  AS `企业地址`,
       `r`.`customer_post_no`            --  AS `邮编`
	   ,`r`.CUSTOMER_FAX-- 传真
,`r`.CUSTOMER_TELEPHONE
FROM  `res_customer` `r` , report_project p  
where  `r`.CUSTOMER_ID  = p.DELEGATE_COMPANY 
and p.`finished_approval` = 1
AND p.`project_type_id` IN('BID', 'PROCUREMENT');
	

create or replace view view_oa_service_announcement  as 

SELECT
	pba.project_id AS project_id,-- 项目id,
	p.PROJECT_CODE AS project_code,-- 项目code
	p.PROJECT_NAME ,-- 缺少 公告标题
	pba.announcement_date AS announcement_date,-- 发出公告日期,
	pbp.bid_doc_end_date AS bid_doc_end_date,-- 报名结束日期,
	pba.bid_doc_begin_sell_date AS bid_doc_begin_sell_date,-- 招标文件发售时间,
	-- 缺少 投标开始时间
	pba.bid_doc_end_sell_date AS bid_doc_end_sell_date,-- 投标截止时间,
	p.bid_open_date AS bid_open_date,-- 开标时间,
	p.bid_open_addr AS bid_open_addr, -- 开标地址
	pba.BID_ANNOUNCEMENT_ID   -- 公告内容
, saa.ATTACHMENT_ID,
  saa.attachment_name          AS attachment_name,-- 文件名称,
  saa.upload_date              AS upload_date,-- 上传时间,
  '招标公告或采购邀请书' AS  filetype,-- 文件类型,
  saa.attachment_path          AS attachment_path-- 文件保存服务器路径
FROM
	pro_bid_announcement pba
LEFT JOIN pro_bid_project pbp ON pba.project_id = pbp.bid_project_id
LEFT JOIN pro_project p ON pba.project_id = p.project_id
 , pro_project_attachment ppa
,sys_attachment saa , report_project r 
where 
	ppa.attachment_id = saa.attachment_id
AND   ppa.doc_type_id IN (18,42)
and ppa.PROJECT_ID = pba.PROJECT_ID
and r .finished_approval = 1
AND r.project_type_id IN('BID', 'PROCUREMENT')
and r.project_id = pba.PROJECT_ID;
	
 
create or replace view view_oa_service_announcementdoc  as 
/*
招标公告信息附件视图表
*/
SELECT    
					 saa.ATTACHMENT_ID,
					ppa.project_id               AS project_id ,-- 项目id,
          saa.attachment_name          AS attachment_name,-- 文件名称,
--          oe.employee_name             AS employee_name,-- 上传人,
          saa.upload_date              AS upload_date,-- 上传时间,
          '招标公告或采购邀请书' AS  filetype,-- 文件类型,
          saa.attachment_path          AS attachment_path-- 文件保存服务器路径
FROM      pro_project_attachment ppa
,sys_attachment saa

WHERE     ppa.attachment_id = saa.attachment_id
          AND   ppa.doc_type_id IN (18,42);
          
create or replace view view_oa_service_projectrule as 

select DISTINCT bd.BID_DOCUMENT_ID ,  vp.project_id, s.BID_SECTION_CODE , s.BID_SECTION_NAME,
 vp.project_code ,
 case bd.SELLING_TYPE  when 1069  then '1' when 1070 then '2' end as selling_type, -- 招标文件售卖方式
 bd.BID_DEPOSIT* 10000  as BID_DEPOSIT, -- 投标保证金
 bd.BID_DOC_PRICE * 10000 as BID_DOC_PRICE -- 招标文件价格
 from pro_bid_document  bd left join pro_bid_section s on bd.BID_SECTION_ID = s.BID_SECTION_ID , 
view_oa_service_projectinfo vp where vp.project_id 
 = bd.PROJECT_ID  and vp.proj_type = 'm'  ;
 
create or replace view view_oa_service_projectdoc as 
select 
ppa.PROJECT_ATTACHMENT_ID , 
ppa.PROJECT_ID  , -- 项目id 
ppa.DOC_TYPE_ID  ,  -- 文件类型
sa.ATTACHMENT_NAME , -- 文件名称
sa.ATTACHMENT_PATH  -- 文件path
, sa.UPLOAD_DATE
 from pro_project_attachment  ppa , sys_attachment sa 

where ppa.ATTACHMENT_ID = sa.ATTACHMENT_ID;
