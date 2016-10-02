
Create table eg_fund( 
id bigint,
	name varchar(20),
	code varchar(20),
	active boolean,
	version bigint,
	 createddate timestamp without time zone,
	 createdby bigint,
	 lastmodifieddate timestamp without time zone,
	 lastmodifiedby bigint,
	 version bigint
);
alter table eg_fund add constraint pk_eg_fund primary key (id);
create sequence seq_eg_fund;
