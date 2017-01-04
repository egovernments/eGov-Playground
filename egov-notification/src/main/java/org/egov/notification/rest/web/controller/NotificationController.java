/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.notification.rest.web.controller;

import static org.egov.notification.rest.web.messaging.MessagePriority.HIGH;

import org.apache.log4j.Logger;
import org.egov.notification.rest.web.messaging.service.MessagingService;
import org.egov.notification.rest.web.model.Error;
import org.egov.notification.rest.web.model.ErrorRes;
import org.egov.notification.rest.web.model.RequestInfo;
import org.egov.notification.rest.web.model.ResponseInfo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

	private static final Logger LOGGER = Logger.getLogger(NotificationController.class);

	@Autowired
	private MessagingService messagingService;
	private ResponseInfo resInfo = null;
	
	@RequestMapping(value = "/sms", method = RequestMethod.POST)
	public ResponseInfo sms(@RequestParam(value = "mobile_no", required = true) String mobile_no,
			@RequestParam(value = "message", required = true) String message, @RequestBody RequestInfo request)
			throws Exception {

		if (mobile_no == null || mobile_no.isEmpty()) {
			resInfo = new ResponseInfo(request.getApiId(), request.getVer(), new DateTime().toString(), "uief87324",
					request.getMsgId(), "Mobile number is required");
			throw new Exception("Mobile number is required");
		}
		if (mobile_no.matches("\\d+")) {
			if (!mobile_no.matches("\\d{10}")) {
				resInfo = new ResponseInfo(request.getApiId(), request.getVer(), new DateTime().toString(), "uief87324",
						request.getMsgId(), mobile_no + " is not valid mobile number");
				throw new Exception(mobile_no + " is not valid mobile number");
			}
		} else {
			resInfo = new ResponseInfo(request.getApiId(), request.getVer(), new DateTime().toString(), "uief87324",
					request.getMsgId(), mobile_no + " is not valid mobile number");
			throw new Exception(mobile_no + " is not valid mobile number");
		}

		messagingService.sendSMS(mobile_no, message, HIGH);
		return new ResponseInfo(request.getApiId(), request.getVer(), new DateTime().toString(), "uief87324",
				request.getMsgId(), "Sent SMS successfully to " + mobile_no);

	}

	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public ResponseInfo email(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "subject", required = true) String subject,
			@RequestParam(value = "body", required = true) String body, @RequestBody RequestInfo request)
			throws Exception {
		if (email == null || email.isEmpty()) {
			resInfo = new ResponseInfo(request.getApiId(), request.getVer(), new DateTime().toString(), "uief87324",
					request.getMsgId(), "Email Address is required");
			throw new Exception("Email Address is required");

		}
		if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			resInfo = new ResponseInfo(request.getApiId(), request.getVer(), new DateTime().toString(), "uief87324",
					request.getMsgId(), email + " is not valid email address");
			throw new Exception(email + " is not valid email address");
		}
		messagingService.sendEmail(email, subject, body);
		return new ResponseInfo(request.getApiId(), request.getVer(), new DateTime().toString(), "uief87324",
				request.getMsgId(), "Sent Email successfully to " + email);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorRes> handleError(Exception ex) {
		LOGGER.error(ex.getMessage(), ex);
		ErrorRes response = new ErrorRes();
		response.setResposneInfo(resInfo);
		Error error = new Error();
		error.setCode(400);
		error.setDescription(ex.getMessage());
		response.setError(error);
		return new ResponseEntity<ErrorRes>(response, HttpStatus.BAD_REQUEST);
	}
}