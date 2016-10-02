
Create table eg_group( 
id bigint,
	name varchar(20),
	type varchar(20),
	version bigint,
	,
	 createddate timestamp without time zone,
	 createdby bigint,
	 lastmodifieddate timestamp without time zone,
	 lastmodifiedby bigint,
	 version bigint
);
alter table eg_group add constraint pk_eg_group primary key (id);
create sequence seq_eg_group;
