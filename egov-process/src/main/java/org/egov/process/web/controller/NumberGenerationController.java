package org.egov.process.web.controller;

import org.egov.process.service.NumberGenerationService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
