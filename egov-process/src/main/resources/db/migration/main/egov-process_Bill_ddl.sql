
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

          Create table EG_WORKFLOWTYPE(
          id bigint,
          type varchar(120),
          className varchar(120),
          serviceName varchar(120),
          businessKey varchar(50),
          link varchar(,
          displayName varchar(,
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




          Create table EG_BILL(
          id bigint,
          fundid bigint ,
          departmentid bigint ,
          billNumber varchar(20),
          billDate date,
          billAmount numeric (13,2),
          createdDate date,
          lastModifiedDate date,
          billType varchar(20),
          createdby bigint,
          lastmodifiedby bigint,
          version bigint
          );
          alter table EG_BILL add constraint pk_EG_BILL primary key (id);
          alter table EG_BILL add constraint fk_EG_BILL_fundid  FOREIGN KEY (fundid) REFERENCES eg_fund(id);
          alter table EG_BILL add constraint fk_EG_BILL_departmentid  FOREIGN KEY (departmentid) REFERENCES eg_department(id);
          create sequence seq_EG_BILL;


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


