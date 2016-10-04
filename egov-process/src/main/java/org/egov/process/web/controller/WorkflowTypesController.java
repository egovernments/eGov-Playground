package org.egov.process.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.process.entity.WorkflowTypes;
import org.egov.process.service.WorkflowTypesService;
import org.egov.process.web.adaptor.WorkflowTypesJsonAdaptor;
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
@RequestMapping("/workflowtypes")
public class WorkflowTypesController {
	private final static String WORKFLOWTYPES_NEW = "workflowtypes-new";
	private final static String WORKFLOWTYPES_RESULT = "workflowtypes-result";
	private final static String WORKFLOWTYPES_EDIT = "workflowtypes-edit";
	private final static String WORKFLOWTYPES_VIEW = "workflowtypes-view";
	private final static String WORKFLOWTYPES_SEARCH = "workflowtypes-search";
	@Autowired
	private WorkflowTypesService workflowTypesService;
	@Autowired
	private MessageSource messageSource;

	private void prepareNewForm(Model model) {
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newForm(final Model model) {
		prepareNewForm(model);
		model.addAttribute("workflowTypes", new WorkflowTypes());
		return WORKFLOWTYPES_NEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute final WorkflowTypes workflowTypes, final BindingResult errors,
			final Model model, final RedirectAttributes redirectAttrs,HttpServletRequest request) {
		System.err.println(request.getClass().getClassLoader().getClass().getSimpleName()+""+request.getClass().getSimpleName());
		System.err.println(request.getParameterNames()+"########################44444444444444444444444444444");
		
		System.err.println(request.getParameterMap());
		Map<String, String[]> parameterMap = request.getParameterMap();
		for(String s:parameterMap.keySet())
		{
		System.err.println(parameterMap.get(s));
		for(String sss:parameterMap.get(s))
		{
			System.err.println(parameterMap.get(sss));
		}
		}  
		
		System.err.println("########################44444444444444444444444444444");
		
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return WORKFLOWTYPES_NEW;
		}
		workflowTypesService.create(workflowTypes);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.workflowtypes.success", null, null));
		return "redirect:/workflowtypes/result/" + workflowTypes.getId();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final Long id, Model model) {
		WorkflowTypes workflowTypes = workflowTypesService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("workflowTypes", workflowTypes);
		return WORKFLOWTYPES_EDIT;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update( @ModelAttribute final WorkflowTypes workflowTypes, final BindingResult errors,
			final Model model, final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return WORKFLOWTYPES_EDIT;
		}
		workflowTypesService.update(workflowTypes);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.workflowtypes.success", null, null));
		return "redirect:/workflowtypes/result/" + workflowTypes.getId();
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") final Long id, Model model) {
		WorkflowTypes workflowTypes = workflowTypesService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("workflowTypes", workflowTypes);
		return WORKFLOWTYPES_VIEW;
	}

	@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
	public String result(@PathVariable("id") final Long id, Model model) {
		WorkflowTypes workflowTypes = workflowTypesService.findOne(id);
		model.addAttribute("workflowTypes", workflowTypes);
		return WORKFLOWTYPES_RESULT;
	}

	@RequestMapping(value = "/search/{mode}", method = RequestMethod.GET)
	public String search(@PathVariable("mode") final String mode, Model model) {
		WorkflowTypes workflowTypes = new WorkflowTypes();
		prepareNewForm(model);
		model.addAttribute("workflowTypes", workflowTypes);
		return WORKFLOWTYPES_SEARCH;

	}

	@RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String ajaxsearch(@PathVariable("mode") final String mode, Model model,
			@ModelAttribute final WorkflowTypes workflowTypes) {
		List<WorkflowTypes> searchResultList = workflowTypesService.search(workflowTypes);
		String result = new StringBuilder("{ \"data\":").append(toSearchResultJson(searchResultList)).append("}")
				.toString();
		return result;
	}

	public Object toSearchResultJson(final Object object) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.registerTypeAdapter(WorkflowTypes.class, new WorkflowTypesJsonAdaptor()).create();
		final String json = gson.toJson(object);
		return json;
	}
}