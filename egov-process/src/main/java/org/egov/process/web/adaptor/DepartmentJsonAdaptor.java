package org.egov.process.web.adaptor;

import java.lang.reflect.Type;

import org.egov.process.entity.Department;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
  public class DepartmentJsonAdaptor implements JsonSerializer<Department>
 {
@Override
 public JsonElement serialize(final Department department, final Type type,final JsonSerializationContext jsc) 
{
  final JsonObject jsonObject = new JsonObject();
 if (department != null)
 {
if(department.getName()!=null)
 jsonObject.addProperty("name", department.getName());
else
 jsonObject.addProperty("name","");
if(department.getActive()!=null)
 jsonObject.addProperty("active", department.getActive());
else
 jsonObject.addProperty("active","");
 jsonObject.addProperty("id", department.getId());
     } 
 return jsonObject;  }
 }