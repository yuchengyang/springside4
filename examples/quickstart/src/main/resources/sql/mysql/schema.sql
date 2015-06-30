drop table if exists ss_task;
drop table if exists ss_user;

create table ss_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	user_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table ss_user (
	id bigint auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	register_date timestamp not null default 0,
	primary key (id)
) engine=InnoDB;

insert into ss_user (id, login_name, name, password, salt, roles, register_date) values(1,'admin','admin','2cee6d37b34ff12f62066ad1190791af342e0c35','8701f6f67d53fa49','user','2015-06-04 17:29:54');
