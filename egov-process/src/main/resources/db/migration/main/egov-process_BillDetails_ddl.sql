
Create table EG_BILLDETAILS( 
id bigint,
	glcode varchar(20),
	coaName varchar(20),
	debit numeric (13,2),
	credit numeric (13,2),
	billid bigint ,
	version bigint,
	 createddate timestamp without time zone,
	 createdby bigint,
	 lastmodifieddate timestamp without time zone,
	 lastmodifiedby bigint,
	 version bigint
);
alter table EG_BILLDETAILS add constraint pk_EG_BILLDETAILS primary key (id);
alter table EG_BILLDETAILS add constraint fk_EG_BILLDETAILS_billid  FOREIGN KEY (billid) REFERENCES EG_BILL(id);
create sequence seq_EG_BILLDETAILS;
