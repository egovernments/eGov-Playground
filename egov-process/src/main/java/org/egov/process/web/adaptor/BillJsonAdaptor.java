package org.egov.process.web.adaptor;

import java.lang.reflect.Type;

import org.egov.process.entity.Bill;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
  public class BillJsonAdaptor implements JsonSerializer<Bill>
 {
@Override
 public JsonElement serialize(final Bill bill, final Type type,final JsonSerializationContext jsc) 
{
  final JsonObject jsonObject = new JsonObject();
 if (bill != null)
 {
if(bill.getFund()!=null)
 jsonObject.addProperty("fund", bill.getFund().getName());
else
 jsonObject.addProperty("fund","");
if(bill.getDepartment()!=null)
 jsonObject.addProperty("department", bill.getDepartment().getName());
else
 jsonObject.addProperty("department","");
if(bill.getBillNumber()!=null)
 jsonObject.addProperty("billNumber", bill.getBillNumber());
else
 jsonObject.addProperty("billNumber","");
if(bill.getBillDate()!=null)
 jsonObject.addProperty("billDate", bill.getBillDate().toLocaleString());
else
 jsonObject.addProperty("billDate","");
if(bill.getBillAmount()!=null)
 jsonObject.addProperty("billAmount", bill.getBillAmount());
else
 jsonObject.addProperty("billAmount","");
if(bill.getBillType()!=null)
 jsonObject.addProperty("billType", bill.getBillType());
else
 jsonObject.addProperty("billType","");
 jsonObject.addProperty("id", bill.getId());
     } 
 return jsonObject;  }
 }