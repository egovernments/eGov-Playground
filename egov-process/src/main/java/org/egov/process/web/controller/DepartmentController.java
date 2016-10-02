package org.egov.process.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.process.entity.Department;
import org.egov.process.service.DepartmentService;
import org.egov.process.web.adaptor.DepartmentJsonAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpRequest;
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
@RequestMapping("/department")
public class DepartmentController {
	private final static String DEPARTMENT_NEW = "department-new";
	private final static String DEPARTMENT_RESULT = "department-result";
	private final static String DEPARTMENT_EDIT = "department-edit";
	private final static String DEPARTMENT_VIEW = "department-view";
	private final static String DEPARTMENT_SEARCH = "department-search";
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private MessageSource messageSource;

	private void prepareNewForm(Model model) {
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newForm(final Model model) {
		prepareNewForm(model);
		model.addAttribute("department", new Department());
		return DEPARTMENT_NEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute("processweb") final Department department, final BindingResult errors,
			final Model model, final RedirectAttributes redirectAttrs,HttpServletRequest request) {
		//HttpServletRequest request=(HttpServletRequest)request1;
		System.err.println(request.getParameterNames()+"########################3333333333333333333333");
		
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
		
		System.err.println("########################3333333333333333333333");
		
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return DEPARTMENT_NEW;
		}
		departmentService.create(department);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.department.success", null, null));
		return "redirect:/department/result/" + department.getId();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final Long id, Model model) {
		Department department = departmentService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("department", department);
		return DEPARTMENT_EDIT;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute final Department department, final BindingResult errors,
			final Model model, final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return DEPARTMENT_EDIT;
		}
		departmentService.update(department);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.department.success", null, null));
		return "redirect:/department/result/" + department.getId();
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") final Long id, Model model) {
		Department department = departmentService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("department", department);
		return DEPARTMENT_VIEW;
	}

	@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
	public String result(@PathVariable("id") final Long id, Model model) {
		Department department = departmentService.findOne(id);
		model.addAttribute("department", department);
		return DEPARTMENT_RESULT;
	}

	@RequestMapping(value = "/search/{mode}", method = RequestMethod.GET)
	public String search(@PathVariable("mode") final String mode, Model model) {
		Department department = new Department();
		prepareNewForm(model);
		model.addAttribute("department", department);
		return DEPARTMENT_SEARCH;

	}

	@RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String ajaxsearch(@PathVariable("mode") final String mode, Model model,
			@ModelAttribute final Department department) {
		List<Department> searchResultList = departmentService.search(department);
		String result = new StringBuilder("{ \"data\":").append(toSearchResultJson(searchResultList)).append("}")
				.toString();
		return result;
	}

	public Object toSearchResultJson(final Object object) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.registerTypeAdapter(Department.class, new DepartmentJsonAdaptor()).create();
		final String json = gson.toJson(object);
		return json;
	}
}