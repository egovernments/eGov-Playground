package org.egov.process.web.controller;


import java.util.List;

import javax.validation.Valid;

import org.egov.process.entity.User;
import org.egov.process.service.UserService;
import org.egov.process.web.adaptor.UserJsonAdaptor;
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
@RequestMapping("/user")
 public class UserController {
private final static String USER_NEW="user-new";
private final static String USER_RESULT="user-result";
private final static String USER_EDIT="user-edit";
private final static String USER_VIEW="user-view";
private final static String USER_SEARCH="user-search";
@Autowired
	private  UserService userService;
@Autowired 
 private MessageSource messageSource;private void prepareNewForm(Model model) {
}

@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newForm(final Model model){
prepareNewForm(model);
model.addAttribute("user", new User() );
	return USER_NEW;
}

@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute final User user,final BindingResult errors,final Model model,final RedirectAttributes redirectAttrs){
	if (errors.hasErrors()) {
prepareNewForm(model);
	return USER_NEW; }
userService.create(user);
redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.user.success",null,null));
return "redirect:/user/result/"+user.getId();
}

@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final Long id,Model model){
User user  = userService.findOne(id);prepareNewForm(model);
model.addAttribute("user", user);	return USER_EDIT;
}

@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute final User user,final BindingResult errors,final Model model,final RedirectAttributes redirectAttrs){
	if (errors.hasErrors()){
prepareNewForm(model);return USER_EDIT;
}userService.update(user);
redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.user.success",null,null));
return "redirect:/user/result/"+user.getId();
}

@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") final Long id,Model model){
User user  = userService.findOne(id);
prepareNewForm(model);
model.addAttribute("user", user);	return USER_VIEW;
}

@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
 public String result(@PathVariable("id") final Long id,Model model){
User user  = userService.findOne(id);model.addAttribute("user", user);
return USER_RESULT;}

@RequestMapping(value =  "/search/{mode}", method = RequestMethod.GET)
public String search(@PathVariable("mode") final String  mode,Model model)
{
User user  = new User();
prepareNewForm(model);
model.addAttribute("user",user);
return USER_SEARCH;

}

@RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE)
public @ResponseBody String ajaxsearch(@PathVariable("mode") final String mode, Model model,@ModelAttribute final User user ) 
{
List<User> searchResultList = userService.search(user);
String result = new StringBuilder("{ \"data\":").append(toSearchResultJson(searchResultList)).append("}").toString();
return result;
}
public Object toSearchResultJson(final Object object)
 {
final GsonBuilder gsonBuilder = new GsonBuilder();
final Gson gson = gsonBuilder.registerTypeAdapter(User.class,new UserJsonAdaptor()).create();
final String json = gson.toJson(object);
return json;
}
}