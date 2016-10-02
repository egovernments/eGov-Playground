package org.egov.process.web.adaptor;

import java.lang.reflect.Type;
import org.egov.process.entity.Fund;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
  public class FundJsonAdaptor implements JsonSerializer<Fund>
 {
@Override
 public JsonElement serialize(final Fund fund, final Type type,final JsonSerializationContext jsc) 
{
  final JsonObject jsonObject = new JsonObject();
 if (fund != null)
 {
if(fund.getName()!=null)
 jsonObject.addProperty("name", fund.getName());
else
 jsonObject.addProperty("name","");
if(fund.getActive()!=null)
 jsonObject.addProperty("active", fund.getActive());
else
 jsonObject.addProperty("active","");
 jsonObject.addProperty("id", fund.getId());
     } 
 return jsonObject;  }
 }