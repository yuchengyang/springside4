--drop table if exists ss_task;
--drop table if exists ss_user;

create table ss_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	user_id bigint not null,
    primary key (id)
) engine=InnoDB ;

create table ss_user (
	id bigint auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	register_date timestamp not null default 0,
	query_org varchar(255) ,
	primary key (id)
) engine=InnoDB charset utf8 collate utf8_general_ci;

insert into ss_user (id, login_name, name, password, salt, roles, register_date) values(1,'admin','admin','2cee6d37b34ff12f62066ad1190791af342e0c35','8701f6f67d53fa49','admin,user','2015-06-04 17:29:54');


insert into ss_user (id, login_name, name, password, salt, roles, register_date) values(2,'taoy','taoy','77b6d24559fd1d28c8fa142386aa2f8552063794','0492396a45d231de','user','2015-06-04 17:29:54');
insert into ss_user (id, login_name, name, password, salt, roles, register_date) values(3,'zhanglb','zhanglb','77b6d24559fd1d28c8fa142386aa2f8552063794','0492396a45d231de','user','2015-06-04 17:29:54');
insert into ss_user (id, login_name, name, password, salt, roles, register_date) values(4,'hejn','hejn','77b6d24559fd1d28c8fa142386aa2f8552063794','0492396a45d231de','user','2015-06-04 17:29:54');
insert into ss_user (id, login_name, name, password, salt, roles, register_date) values(5,'zhangxc','zhangxc','77b6d24559fd1d28c8fa142386aa2f8552063794','0492396a45d231de','user','2015-06-04 17:29:54');
