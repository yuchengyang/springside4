-- drop table if exists oa_bulletin;

create table oa_bulletin (
	id bigint auto_increment ,
	project_id varchar(19) not null,
	project_code varchar(50),
	project_name varchar(200),
	announcement_date timestamp ,
	bid_doc_end_date timestamp ,
	bid_doc_begin_sell_date timestamp,
	bid_doc_end_sell_date timestamp ,
	bid_open_date timestamp ,
	bid_open_addr varchar(200),
	bid_announcement_id varchar(50),
	SYN_STATUS char(1),
	ATTACHMENT_ID varchar(50),
	attachment_name varchar(200),
	attachment_path varchar(200),
	upload_date timestamp,
	primary key (id)
);

-- drop table if exists oa_project;

create table oa_project (
	id bigint auto_increment,
	project_id varchar(19) not null,
	project_code varchar(50),
	project_name varchar(200),
	project_secret_level varchar(50),
	project_secret_level_Name varchar(50),
	project_type_id varchar(50),
	project_type_name varchar(50),
	BID_TYPE varchar(50),
	BID_TYPE_NAME varchar(50),
	bid_content varchar(2000),
	investment_scale_foreign varchar(18),
	organization_name varchar(100),
	DEPARTMENT_ID varchar(50),
	DELEGATE_COMPANY varchar(50),
	CUSTOMER_CORPORATE varchar(50),
	CUSTOMER_TELEPHONE varchar(100),
	DELEGATE_DATE timestamp,
	DELEGATE_AMOUNT decimal(18,6),
	creator_name varchar(50),
	creator varchar(50),
	SYN_STATUS int(1),
	PROJ_TYPE char(1),
	PROJECT_PARENT_ID varchar(19),
	primary key (id)
);

-- drop table if exists oa_buyer;
create table oa_buyer (
	id bigint auto_increment,
	customer_id int(19) not null,
	customer_name varchar(100),
	customer_parent_organization varchar(200),
	customer_industry varchar(50),
	customer_mail varchar(50),
	customer_address varchar(200),
	customer_post_no varchar(50),
	customer_fax varchar(50),
	CUSTOMER_TELEPHONE varchar(100),
	SYN_STATUS char(1),
	primary key (id)
);


create index IDX_PROJ_TYPE  on OA_PROJECT  (PROJ_TYPE ); 
create index IDX_PROJECT_PARENT_ID  on OA_PROJECT  (PROJECT_PARENT_ID  ); 
create index IDX_BULLETIN_PROJ_ID   on OA_BULLETIN  (PROJECT_ID ); 

