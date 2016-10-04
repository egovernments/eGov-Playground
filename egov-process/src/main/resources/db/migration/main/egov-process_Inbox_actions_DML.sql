Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'New-Inbox','/inbox/new',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'New-Inbox',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='New-Inbox'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Create-Inbox','/inbox/create',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Create-Inbox',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Create-Inbox'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Update-Inbox','/inbox/update',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Update-Inbox',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Update-Inbox'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View-Inbox','/inbox/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'View-Inbox',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='View-Inbox'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit-Inbox','/inbox/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Edit-Inbox',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Edit-Inbox'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Result-Inbox','/inbox/result',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Result-Inbox',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Result-Inbox'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View Inbox','/inbox/search/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),2,'View Inbox',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='View Inbox'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit Inbox','/inbox/search/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),3,'Edit Inbox',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Edit Inbox'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and View Result-Inbox','/inbox/ajaxsearch/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Search and View Result-Inbox',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Search and View Result-Inbox'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and Edit Result-Inbox','/inbox/ajaxsearch/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Search and Edit Result-Inbox',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Search and Edit Result-Inbox'));

