Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'New-User','/user/new',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'New-User',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='New-User'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Create-User','/user/create',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Create-User',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Create-User'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Update-User','/user/update',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Update-User',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Update-User'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View-User','/user/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'View-User',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='View-User'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit-User','/user/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Edit-User',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Edit-User'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Result-User','/user/result',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Result-User',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Result-User'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View User','/user/search/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),2,'View User',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='View User'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit User','/user/search/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),3,'Edit User',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Edit User'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and View Result-User','/user/ajaxsearch/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Search and View Result-User',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Search and View Result-User'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and Edit Result-User','/user/ajaxsearch/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Search and Edit Result-User',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Search and Edit Result-User'));

