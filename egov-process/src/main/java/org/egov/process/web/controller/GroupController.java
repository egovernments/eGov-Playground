package org.egov.process.web.controller;


import java.util.List;

import javax.validation.Valid;

import org.egov.process.entity.Group;
import org.egov.process.service.GroupService;
import org.egov.process.web.adaptor.GroupJsonAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller 
@RequestMapping("/group")
 public class GroupController {
private final static String GROUP_NEW="group-new";
private final static String GROUP_RESULT="group-result";
private final static String GROUP_EDIT="group-edit";
private final static String GROUP_VIEW="group-view";
private final static String GROUP_SEARCH="group-search";
@Autowired
	private  GroupService groupService;
@Autowired 
 private MessageSource messageSource;private void prepareNewForm(Model model) {
}

@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newForm(final Model model){
prepareNewForm(model);
model.addAttribute("group", new Group() );
	return GROUP_NEW;
}

@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute final Group group,final BindingResult errors,final Model model,final RedirectAttributes redirectAttrs){
	if (errors.hasErrors()) {
prepareNewForm(model);
	return GROUP_NEW; }
groupService.create(group);
redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.group.success",null,null));
return "redirect:/group/result/"+group.getId();
}

@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final Long id,Model model){
Group group  = groupService.findOne(id);prepareNewForm(model);
model.addAttribute("group", group);	return GROUP_EDIT;
}

@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute final Group group,final BindingResult errors,final Model model,final RedirectAttributes redirectAttrs){
	if (errors.hasErrors()){
prepareNewForm(model);return GROUP_EDIT;
}groupService.update(group);
redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.group.success",null,null));
return "redirect:/group/result/"+group.getId();
}

@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") final Long id,Model model){
Group group  = groupService.findOne(id);
prepareNewForm(model);
model.addAttribute("group", group);	return GROUP_VIEW;
}

@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
 public String result(@PathVariable("id") final Long id,Model model){
Group group  = groupService.findOne(id);model.addAttribute("group", group);
return GROUP_RESULT;}

@RequestMapping(value =  "/search/{mode}", method = RequestMethod.GET)
public String search(@PathVariable("mode") final String  mode,Model model)
{
Group group  = new Group();
prepareNewForm(model);
model.addAttribute("group",group);
return GROUP_SEARCH;

}

@RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE)
public @ResponseBody String ajaxsearch(@PathVariable("mode") final String mode, Model model,@ModelAttribute final Group group ) 
{
List<Group> searchResultList = groupService.search(group);
String result = new StringBuilder("{ \"data\":").append(toSearchResultJson(searchResultList)).append("}").toString();
return result;
}
public Object toSearchResultJson(final Object object)
 {
final GsonBuilder gsonBuilder = new GsonBuilder();
final Gson gson = gsonBuilder.registerTypeAdapter(Group.class,new GroupJsonAdaptor()).create();
final String json = gson.toJson(object);
return json;
}
}