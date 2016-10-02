
Create table eg_department( 
id bigint,
	name varchar(20),
	code varchar(20),
	active boolean,
 
	 createddate timestamp without time zone,
	 createdby bigint,
	 lastmodifieddate timestamp without time zone,
	 lastmodifiedby bigint,
	 version bigint
);
alter table eg_department add constraint pk_eg_department primary key (id);
create sequence seq_eg_department;
