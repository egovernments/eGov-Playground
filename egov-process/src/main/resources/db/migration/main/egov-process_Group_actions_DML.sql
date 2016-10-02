Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'New-Group','/group/new',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'New-Group',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='New-Group'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Create-Group','/group/create',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Create-Group',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Create-Group'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Update-Group','/group/update',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Update-Group',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Update-Group'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View-Group','/group/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'View-Group',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='View-Group'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit-Group','/group/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Edit-Group',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Edit-Group'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Result-Group','/group/result',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Result-Group',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Result-Group'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View Group','/group/search/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),2,'View Group',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='View Group'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit Group','/group/search/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),3,'Edit Group',true,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Edit Group'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and View Result-Group','/group/ajaxsearch/view',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Search and View Result-Group',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Search and View Result-Group'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and Edit Result-Group','/group/ajaxsearch/edit',(select id from eg_module where name='Process' and parentmodule=(select id from eg_module where name='Process Management' and parentmodule is null)),1,'Search and Edit Result-Group',false,'egov-process',(select id from eg_module where name='Process Management' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='Super User'),(select id from eg_action where name='Search and Edit Result-Group'));

