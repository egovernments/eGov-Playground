package org.egov.process.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.egov.process.entity.Bill;
import org.egov.process.service.BillService;
import org.egov.process.service.DepartmentService;
import org.egov.process.service.FundService;
import org.egov.process.service.UserService;
import org.egov.process.web.adaptor.BillJsonAdaptor;
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
@RequestMapping("/bill")
public class BillController {
	private final static String BILL_NEW = "bill-new";
	private final static String BILL_RESULT = "bill-result";
	private final static String BILL_EDIT = "bill-edit";
	private final static String BILL_VIEW = "bill-view";
	private final static String BILL_SEARCH = "bill-search";

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private FundService fundService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserService userService;
	@Autowired
	private BillService billService;

	private void prepareNewForm(Model model) {
		model.addAttribute("funds", fundService.findAll());
		model.addAttribute("departments", departmentService.findAll());
		model.addAttribute("users", userService.findAll());
		model.addAttribute("bills", billService.findAll());
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newForm(final Model model) {
		prepareNewForm(model);
		model.addAttribute("bill", new Bill());
		return BILL_NEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute final Bill bill, final BindingResult errors, final Model model,
			final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return BILL_NEW;
		}
		billService.create(bill);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.bill.success", null, null));
		return "redirect:/bill/result/" + bill.getId();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") final Long id, Model model) {
		Bill bill = billService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("bill", bill);
		return BILL_EDIT;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute final Bill bill, final BindingResult errors, final Model model,
			final RedirectAttributes redirectAttrs) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return BILL_EDIT;
		}
		billService.update(bill);
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.bill.success", null, null));
		return "redirect:/bill/result/" + bill.getId();
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") final Long id, Model model) {
		Bill bill = billService.findOne(id);
		prepareNewForm(model);
		model.addAttribute("bill", bill);
		return BILL_VIEW;
	}

	@RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
	public String result(@PathVariable("id") final Long id, Model model) {
		Bill bill = billService.findOne(id);
		model.addAttribute("bill", bill);
		return BILL_RESULT;
	}

	@RequestMapping(value = "/search/{mode}", method = RequestMethod.GET)
	public String search(@PathVariable("mode") final String mode, Model model) {
		Bill bill = new Bill();
		prepareNewForm(model);
		model.addAttribute("bill", bill);
		return BILL_SEARCH;

	}

	@RequestMapping(value = "/ajaxsearch/{mode}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String ajaxsearch(@PathVariable("mode") final String mode, Model model,
			@ModelAttribute final Bill bill) {
		List<Bill> searchResultList = billService.search(bill);
		String result = new StringBuilder("{ \"data\":").append(toSearchResultJson(searchResultList)).append("}")
				.toString();
		return result;
	}

	public Object toSearchResultJson(final Object object) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.registerTypeAdapter(Bill.class, new BillJsonAdaptor()).create();
		final String json = gson.toJson(object);
		return json;
	}
}