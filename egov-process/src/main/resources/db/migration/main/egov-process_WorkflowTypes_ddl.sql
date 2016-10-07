
Create table EG_WORKFLOWTYPE( 
   id bigint,
	 className varchar(120),
	 type varchar(120),
 	 serviceName varchar(120),
	 businessKey varchar(50),
	 displayName varchar(20),
	 link varchar(20),
	 enabled boolean,
	 grouped boolean,
	 createddate timestamp without time zone,
	 createdby bigint,
	 lastmodifieddate timestamp without time zone,
	 lastmodifiedby bigint,
	 version bigint
);
alter table EG_WORKFLOWTYPE add constraint pk_EG_WORKFLOWTYPE primary key (id);
create sequence seq_EG_WORKFLOWTYPE;
