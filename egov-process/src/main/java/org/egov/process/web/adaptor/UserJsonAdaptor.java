package org.egov.process.web.adaptor;

import java.lang.reflect.Type;
import org.egov.process.entity.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
  public class UserJsonAdaptor implements JsonSerializer<User>
 {
@Override
 public JsonElement serialize(final User user, final Type type,final JsonSerializationContext jsc) 
{
  final JsonObject jsonObject = new JsonObject();
 if (user != null)
 {
if(user.getUserName()!=null)
 jsonObject.addProperty("userName", user.getUserName());
else
 jsonObject.addProperty("userName","");
if(user.getFirstName()!=null)
 jsonObject.addProperty("firstName", user.getFirstName());
else
 jsonObject.addProperty("firstName","");
if(user.getLastName()!=null)
 jsonObject.addProperty("lastName", user.getLastName());
else
 jsonObject.addProperty("lastName","");
if(user.getEmail()!=null)
 jsonObject.addProperty("email", user.getEmail());
else
 jsonObject.addProperty("email","");
 jsonObject.addProperty("id", user.getId());
     } 
 return jsonObject;  }
 }