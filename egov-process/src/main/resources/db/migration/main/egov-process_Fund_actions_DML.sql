Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'New-Fund','/fund/new',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'New-Fund',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='New-Fund'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Create-Fund','/fund/create',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Create-Fund',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Create-Fund'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Update-Fund','/fund/update',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Update-Fund',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Update-Fund'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View-Fund','/fund/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'View-Fund',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='View-Fund'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit-Fund','/fund/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Edit-Fund',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Edit-Fund'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Result-Fund','/fund/result',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Result-Fund',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Result-Fund'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View Fund','/fund/search/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),2,'View Fund',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='View Fund'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit Fund','/fund/search/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),3,'Edit Fund',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Edit Fund'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and View Result-Fund','/fund/ajaxsearch/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Search and View Result-Fund',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Search and View Result-Fund'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and Edit Result-Fund','/fund/ajaxsearch/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Search and Edit Result-Fund',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Search and Edit Result-Fund'));

