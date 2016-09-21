package org.egov.process.web.controller;

import org.egov.process.service.NumberGenerationService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @author mani
 *
 *  Sample execution flow
 *  http://localhost:8180/egov-process/gen/start?message=startingon21sep
 *	http://localhost:8180/egov-process/gen/process?userName=kavya&message=kavyaworking&processInstaceId=91a89322-7fde-11e6-9292-34e6ad894cd4
 *	http://localhost:8180/egov-process/gen/process?userName=venki&message=venkiworking&processInstaceId=6acce775-7fcf-11e6-a94e-34e6ad894cd4

 */
@RestController
public class NumberGenerationController{
	private static  Logger Log=Logger.getLogger(NumberGenerationController.class);
	@Autowired
	private NumberGenerationService numberGenerationService;
	
	@RequestMapping("/gen/start")
	public String start(@RequestParam String message) {
		
		String result=numberGenerationService.start(message);
		return result;
	
	}
	
	@RequestMapping("/gen/process")
	public String process(@RequestParam String userName,@RequestParam String message,@RequestParam String processInstaceId) {
 		String result=numberGenerationService.process(userName,message,processInstaceId);
		return result;
	
	}
	
	
	

}
