Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'New-Bill','/bill/new',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'New-Bill',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='New-Bill'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Create-Bill','/bill/create',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Create-Bill',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Create-Bill'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Update-Bill','/bill/update',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Update-Bill',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Update-Bill'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View-Bill','/bill/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'View-Bill',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='View-Bill'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit-Bill','/bill/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Edit-Bill',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Edit-Bill'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Result-Bill','/bill/result',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Result-Bill',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Result-Bill'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View Bill','/bill/search/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),2,'View Bill',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='View Bill'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit Bill','/bill/search/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),3,'Edit Bill',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Edit Bill'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and View Result-Bill','/bill/ajaxsearch/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Search and View Result-Bill',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Search and View Result-Bill'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and Edit Result-Bill','/bill/ajaxsearch/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Search and Edit Result-Bill',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Search and Edit Result-Bill'));

