package org.egov.process.web.adaptor;

import java.lang.reflect.Type;
import org.egov.process.entity.Group;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
  public class GroupJsonAdaptor implements JsonSerializer<Group>
 {
@Override
 public JsonElement serialize(final Group group, final Type type,final JsonSerializationContext jsc) 
{
  final JsonObject jsonObject = new JsonObject();
 if (group != null)
 {
if(group.getName()!=null)
 jsonObject.addProperty("name", group.getName());
else
 jsonObject.addProperty("name","");
if(group.getType()!=null)
 jsonObject.addProperty("type", group.getType());
else
 jsonObject.addProperty("type","");
 jsonObject.addProperty("id", group.getId());
     } 
 return jsonObject;  }
 }