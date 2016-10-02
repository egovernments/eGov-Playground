package org.egov.process.web.adaptor;

import java.lang.reflect.Type;

import org.egov.process.entity.WorkflowTypes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
  public class WorkflowTypesJsonAdaptor implements JsonSerializer<WorkflowTypes>
 {
@Override
 public JsonElement serialize(final WorkflowTypes workflowTypes, final Type type,final JsonSerializationContext jsc) 
{
  final JsonObject jsonObject = new JsonObject();
 if (workflowTypes != null)
 {
if(workflowTypes.getType()!=null)
 jsonObject.addProperty("type", workflowTypes.getType());
else
 jsonObject.addProperty("type","");
if(workflowTypes.getClassName()!=null)
 jsonObject.addProperty("className", workflowTypes.getClassName());
else
 jsonObject.addProperty("className","");
if(workflowTypes.getServiceName()!=null)
 jsonObject.addProperty("serviceName", workflowTypes.getServiceName());
else
 jsonObject.addProperty("serviceName","");
if(workflowTypes.getBusinessKey()!=null)
 jsonObject.addProperty("businessKey", workflowTypes.getBusinessKey());
else
 jsonObject.addProperty("businessKey","");
if(workflowTypes.getLink()!=null)
 jsonObject.addProperty("link", workflowTypes.getLink());
else
 jsonObject.addProperty("link","");
if(workflowTypes.getDisplayName()!=null)
 jsonObject.addProperty("displayName", workflowTypes.getDisplayName());
else
 jsonObject.addProperty("displayName","");
if(workflowTypes.isEnabled()!=null)
 jsonObject.addProperty("enabled", workflowTypes.isEnabled().toString());
else
 jsonObject.addProperty("enabled","");
 jsonObject.addProperty("id", workflowTypes.getId());
     } 
 return jsonObject;  }
 }