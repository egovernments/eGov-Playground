package org.egov.process.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.egov.process.entity.Bill;
import org.egov.process.service.*;
import org.egov.process.web.adaptor.BillJsonAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bill")
public class BillController {
	private final static String BILL_NEW = "bill-new";
	private final static String BILL_RESULT = "bill-result";
	private final static String BILL_EDIT = "bill-edit";
	private final static String BILL_VIEW = "bill-view";
	private final static String BILL_WF_VIEW = "bill-wf-view";
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
	@Autowired
	private WorkflowService workflowService;	


	private void prepareNewForm(Model model) {
		model.addAttribute("funds", fundService.findAll());
		model.addAttribute("departments", departmentService.findAll());
		Map billtypes=new LinkedHashMap<String,String>();
		billtypes.put(Bill.BILL_TYPE_EXPENSE,Bill.BILL_TYPE_EXPENSE);
		billtypes.put(Bill.BILL_TYPE_CONTRACTOR,Bill.BILL_TYPE_CONTRACTOR);
		billtypes.put(Bill.BILL_TYPE_SUPPLIER,Bill.BILL_TYPE_SUPPLIER);
		billtypes.put(Bill.BILL_TYPE_SALARY,Bill.BILL_TYPE_SALARY);
		billtypes.put(Bill.BILL_TYPE_PENSION,Bill.BILL_TYPE_PENSION);
		model.addAttribute("billtypes",billtypes );
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
		bill.setMessage(bill.getBillNumber()+""+bill.getBillType()+""+bill.getBillAmount());
		workflowService.initiate(bill.getClass().getName(), bill.getId(),bill.getMessage(),bill.getBillType());
		redirectAttrs.addFlashAttribute("message", messageSource.getMessage("msg.bill.success", null, null));
		return "redirect:/bill/result/" + bill.getId();
	}

	@RequestMapping(value = "/workflow/edit/{id}/{taskId}", method = RequestMethod.GET)
	public String workflowEdit(@PathVariable("id") final Long id,@PathVariable("taskId") final String taskId, Model model) {
		Bill bill = billService.findOne(id);
		bill.setTaskId(taskId);
		prepareNewForm(model);
		model.addAttribute("bill", bill);
		return BILL_EDIT;
	}
	@RequestMapping(value = "/workflow/view/{id}/{taskId}", method = RequestMethod.GET)
	public String workflowView(@PathVariable("id") final Long id,@PathVariable("taskId") final String taskId, Model model) {
		Bill bill = billService.findOne(id);
		bill.setTaskId(taskId);
		prepareNewForm(model);
		model.addAttribute("bill", bill);
		return BILL_WF_VIEW;
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
			final RedirectAttributes redirectAttrs,HttpServletRequest request) {
		if (errors.hasErrors()) {
			prepareNewForm(model);
			return BILL_EDIT;
		}
		billService.update(bill);
		workflowService.update(bill.getTaskId(), bill.getId(),bill.getMessage(),(String)request.getSession().getAttribute("userName"));
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