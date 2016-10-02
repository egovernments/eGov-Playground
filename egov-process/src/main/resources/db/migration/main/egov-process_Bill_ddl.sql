
Create table EG_BILL( 
id bigint,
	fundid bigint ,
	departmentid bigint ,
	billNumber varchar(20),
	billDate date,
	billAmount numeric (13,2),
	 
	createdDate date,
	lastModifiedDate date,
	billType varchar(,
	 
	 createddate timestamp without time zone,
	 createdby bigint,
	 lastmodifieddate timestamp without time zone,
	 lastmodifiedby bigint,
	 version bigint
);
alter table EG_BILL add constraint pk_EG_BILL primary key (id);
alter table EG_BILL add constraint fk_EG_BILL_fundid  FOREIGN KEY (fundid) REFERENCES eg_fund(id);
alter table EG_BILL add constraint fk_EG_BILL_departmentid  FOREIGN KEY (departmentid) REFERENCES eg_department(id);
create sequence seq_EG_BILL;
