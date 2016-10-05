package org.egov.process.web.controller;


import java.util.List;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.egov.process.entity.Fund;
import org.egov.process.entity.Inbox;
import org.egov.process.service.InboxService;
import org.egov.process.web.adaptor.InboxJsonAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller 

public class InboxController {
	
	@Autowired
	private  InboxService inboxService;
	
	@Autowired
	private IdentityService iservice;
	
	@RequestMapping(value = "/login/{userName}", method = RequestMethod.GET)
    public String   login(@PathVariable String userName,HttpServletRequest request)
    {
		User user = iservice.createUserQuery().userId(userName).singleResult();
		if(user.getId()!=null)
		{
		request.getSession().setAttribute("userName", user.getFirstName());
		return	"inbox-frame";
		}
		else
			return	"inbox-frame";
			
    }
	
	@RequestMapping(value = "/logout/", method = RequestMethod.GET ) 
    public  String  logout(HttpServletRequest request)
    {
		
		request.getSession().removeAttribute("userName");
		return	"inbox-frame";
    }
	
	@RequestMapping(value =  "inbox", method = RequestMethod.GET)
	public String search()
	{
		
		return "inbox-search";

	}


	@RequestMapping(value = "/inbox/ajaxsearch", method = RequestMethod.GET,produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String ajaxsearch(HttpServletRequest request) 
	{
		if(null !=request.getSession().getAttribute("userName"))
		{
		String  userName = (String)request.getSession().getAttribute("userName");
		List<Inbox> searchResultList = inboxService.search(userName);
		String result = new StringBuilder("{ \"data\":").append(toSearchResultJson(searchResultList)).append("}").toString();
		return result;
		}
		else
		{
			return "User Not Loged in ";
			
		}
		
	}
	public Object toSearchResultJson(final Object object)
	{
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.registerTypeAdapter(Inbox.class,new InboxJsonAdaptor()).create();
		final String json = gson.toJson(object);
		return json;
	}
}