
Create table eg_user( 
id bigint,
	userName varchar(20),
	firstName varchar(20),
	lastName varchar(20),
	email varchar(20),
	password varchar(20),
	version bigint,
	 createddate timestamp without time zone,
	 createdby bigint,
	 lastmodifieddate timestamp without time zone,
	 lastmodifiedby bigint,
	 version bigint
);
alter table eg_user add constraint pk_eg_user primary key (id);
create sequence seq_eg_user;
