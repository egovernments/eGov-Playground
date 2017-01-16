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

var currentType = "";
jQuery(document).ready(function($)
		{

	$('html, body').animate({
		scrollTop: 0
	});

	$('#complaintTypeCategory').change(function() {
		$('#complaintType').find('option:gt(0)').remove();
		if (this.value === '') {
			return;
		} else {
			$.ajax({
				type: "GET",
				url: "complainttypes-by-category",
				cache: true,
				data:{'categoryId' : this.value}
			}).done(function(data) {
				$.each(data, function(key,value) {
					$('#complaintType').append('<option value="'+value.id+'">' + value.name + '</option>');
				});
				if (currentType != "") {
					$("#complaintType").val(currentType);
					currentType="";
				}
			});
		}
	});

	// Instantiate the Bloodhound suggestion engine
	var complaintlocation = new Bloodhound({
		datumTokenizer: function (datum) {
			return Bloodhound.tokenizers.whitespace(datum.value);
		},
		queryTokenizer: Bloodhound.tokenizers.whitespace,
		remote: {
			url: 'locations?locationName=%QUERY',
			filter: function (data) {
				// Map the remote source JSON array to a JavaScript object array
				return $.map(data, function (cl) {
					return {
						name: cl.name,
						value: cl.id
					};
				});
			}
		}
	});

	// Initialize the Bloodhound suggestion engine
	complaintlocation.initialize();

	// Instantiate the Typeahead UI
	$('#location').typeahead({
		hint: true,
		highlight: true,
		minLength: 1
	}, {
		displayKey: 'name',
		source: complaintlocation.ttAdapter()
	}).on('typeahead:selected', function(event, data){            
		//$("#locationid").val(data.locId);    
		$("#crosshierarchyId").val(data.value);    
		$('#lat, #lng').val(0.0);
	});

	$(":input").inputmask();


	$('.freq-ct').click(function(){ 
		$('#complaintTypeName').typeahead('val',$(this).html().trim());
		$("#complaintTypeName").trigger('blur');
	});

	$('#create-griev').click(function() {
//		var token = sessionStorage.getItem("token");
//		$.ajax
//		({
//		  type: "GET",
//		  url: "http://localhost:8090/pgrrest/users/_login?jurisdiction_id=ap.test",
//		  dataType: 'json',
//		  async: false,
//		  headers: {
//			"Content-Type" : "application/x-www-form-urlencoded",  
//		    "Authorization": "Basic " + btoa("egov-pgrrest" + ":" + "egov-pgrrest")
//		  },
//		  data:{
//			  "username": "9999999999",
//			  "scope": "read+write",
//			  "password": "demo",
//			  "grant_type": "password"
//			 },
//		  success: function (){
//		    alert('Thanks for your comment!'); 
//		  }
//		});
//		var host = $('#resthost').val();
//		if(host === "" || host === null)
//			host = "http://localhost:8080";
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/pgrrest/a1/requests?jurisdiction_id=ap.test&tenant_id=ap.test",
			processData: false,
			data:JSON.stringify(prepareRequest()),
			success : function(response) {
				console.log("success"+response );
				$.each(response.ServiceRequests[0],function(key,val){
					$('div[data-api-key='+key+']').html(val);
				});
				
				$('div[data-api-key="location"]').html(response.ServiceRequests[0].values[0].values);
				$('#success').show();
				$('#registration').hide();
			},
			error : function(response) {
				console.log("failed");
			}
		});
	});

	function prepareRequest(type){
		var params={};
		$('form *[data-api-key]').each(function() {
			params[$(this).attr('data-api-key')]=$(this).val();
		});
		
		//Format Date
		var date = new Date();
		var day = date.getDay()+1;
		var month = date.getMonth()+1;
		var year = date.getFullYear();
		var hours = date.getHours();
		var minitues = date.getMinutes();
		var seconds = date.getSeconds();
		var currentDate = day+'-'+month+'-'+year;

		//Build attributes array
		var RequestInfo = {};
		RequestInfo.api_id = "org.egov.pgr";
		RequestInfo.ver = "1.0";
		RequestInfo.ts = "05-01-2017 10:50:00";
		RequestInfo.action = "POST";
		RequestInfo.did = "4354648646";
		RequestInfo.key = "XYZ";
		RequestInfo.msg_id = "654654";
		RequestInfo.requester_id = params['requester_id'];
		RequestInfo.auth_token = "byrfyrfieruiuirugiergh";

		var ServiceRequest = {};
		ServiceRequest.description = params['description'];
		ServiceRequest.lat = params['lat'];
		ServiceRequest.long = params['lng'];
		ServiceRequest.address = params['address'];
		ServiceRequest.service_name = $('#complaintType option:selected').text();
		ServiceRequest.service_code = params["service_code"];
		ServiceRequest.address_id = params["crosshierarchyId"];
		ServiceRequest.status = true;
		ServiceRequest.requested_datetime = "";
		if(type === 'ANONYMOUS'){
			ServiceRequest.first_name = params["first_name"];
			ServiceRequest.phone = params["mobile"];
			ServiceRequest.email = params["email"];
		}

		var request = {};
		request.RequestInfo = RequestInfo;
		request.ServiceRequest = ServiceRequest;

		return request;
	}


	$('#create-anonymous-grievance').click(function() {

//		var host = $('#resthost').val();
//		if(host === "" || host === null)
//			host = "http://localhost:8080";
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/pgrrest/a1/requests?jurisdiction_id=ap.test&tenant_id=ap.test",
			data:JSON.stringify(prepareRequest('ANONYMOUS')),
			success : function(response) {
				$.each(response.ServiceRequests[0],function(key,val){
					$('div[data-api-key='+key+']').html(val);
				});
				$('div[data-api-key="location"]').html(response.ServiceRequests[0].values[0].values);
				$('#success').show();
				$('#registration').hide();
			},
			error : function(response) {
				console.log("failed");
			}
		});
		return false;
	});



	$('input[type=radio][name=receivingMode]').change(function() {
		$('#receivingCenter').prop('selectedIndex',0);
		disableCRN(); 
		if ($("input[name=receivingMode]:checked").val() == 'MANUAL') {
			enableRC();
		} else {
			disableRC();
		}
	});

	$('.tour-section').click(function(){
		$('.demo-class').modal('show', {backdrop: 'static'});
		var tour = new Tour({
			steps: [
			        {
			        	element: "#f-name",
			        	title: "Name",
			        	content: "Enter your full name!"
			        },
			        {
			        	element: "#mob-no",
			        	title: "Mobile Number",
			        	content: "Enter your valid 10 digit mobile number!"
			        },
			        {
			        	element: "#email",
			        	title: "Email ID",
			        	content: "Enter your valid email id!"
			        },
			        {
			        	element: "#address",
			        	title: "Address",
			        	content: "Enter your present residential address!"
			        },
			        {
			        	element: "#topcomplaint",
			        	title: "Top Grievance Types",
			        	content: "Select your grievance from here or else choose it from below grievance category and grievance type!"
			        },
			        {
			        	element: "#complaintTypeCategory",
			        	title: "Grievance Category",
			        	content: "Select your grievance category!"
			        },
			        {
			        	element: "#complaintType",
			        	title: "Grievance Type",
			        	content: "Select your grievance type!"
			        },
			        {
			        	element: "#doc",
			        	title: "Grievance Details",
			        	content: "Describe your grievance details briefly!"
			        },
			        {
			        	element: "#upload-section",
			        	title: "Upload Photograph / Video",
			        	content: "Upload grievance relevated photo / video (Max : 3 files)!"
			        },
			        {
			        	element: "#location-tour",
			        	title: "Grievance Location",
			        	content: "Enter your grievance location or click on the location icon to select your desired location from the map!"
			        },
			        {
			        	element: "#landmarkDetails",
			        	title: "Landmark",
			        	content: "Enter your landmark (if any)!"
			        },
			        {
			        	element: "#captcha-section iframe",
			        	title: "Captcha",
			        	content: "Click on the checkbox to validate captcha!"
			        },
			        {
			        	element: "#create-anonymous-grievance",
			        	title: "Create Grievance",
			        	content: "Finally, Click here to submit your grievance!"
			        }],
			        storage: false,
			        duration: 6000,
			        onShown: function (tour) {
			        	//console.log(tour.getCurrentStep());
			        	var step = tour.getCurrentStep();
			        	if(step == 0){
			        		typingfeel('James Jackson', '#f-name');
			        	}else if(step == 1){
			        		typingfeel('9988776655', '#mob-no');
			        	}else if(step == 2){
			        		typingfeel('james.jackson@gmail.com', '#email');
			        	}else if(step == 3){
			        		typingfeel('Colorado U.S', '#address');
			        	}else if(step == 4){
			        		//typingfeel('Colorado U.S', '#address');
			        	}else if(step == 5){
			        		$('#complaintTypeCategory').val('1').attr("selected", "selected");
			        	}else if(step == 6){
			        		$('<option>').val('1').text('Absenteesim of sweepers').appendTo('#complaintType');
			        		$('#complaintType').val('1').attr("selected", "selected");
			        	}else if(step == 7){
			        		typingfeel('Dog menace in madiwala', '#doc');
			        	}else if(step == 9){ 
			        		typingfeelintypeahead('Rev','#location','Revenue, Zone-4, Srikakulam  Municipality');
			        	}else if(step == 10){
			        		typingfeel('Spencer Plaza', '#landmarkDetails');
			        	}
			        },
			        onEnd: function (tour) {
			        	location.reload();
			        },
			        template : "<div class='popover tour'> <div class='arrow'></div> <h3 class='popover-title'></h3> <div class='popover-content'></div> </nav> </div>"
		});
		// Initialize the tour
		tour.init();
		// Start the tour
		tour.start();

	});

		});

$("#receivingCenter").change(function(){
	if (this.value === '') {
		disableCRN();
		return;
	} else {
		$.ajax({
			type: "GET",
			url: "isCrnRequired",
			cache: true,
			data:{'receivingCenterId' : this.value}
		}).done(function(value) {
			if(value === true) {
				enabledCRN();
			} else {
				disableCRN();
			}
		});
	}
});	

function setComplaintTypeId(type,category) {
	$("#complaintTypeCategory").val(category);
	$("#complaintTypeCategory").trigger('change');
	currentType = type;
}

function typingfeel(text, input){
	$.each(text.split(''), function(i, letter){
		setTimeout(function(){
			//we add the letter to the container
			$(input).val($(input).val() + letter);
		}, 200*(i+1));
	});
}

function typingfeelintypeahead(text, input, typeaheadtext){
	//text is split up to letters
	$.each(text.split(''), function(i, letter){
		setTimeout(function(){
			//we add the letter to the container
			$(input).val($(input).val() + letter);
			$(input).trigger("input");
			$("span.twitter-typeahead .tt-suggestion > p").mouseenter();
			if(i == 2)
			{ 
				$(input).typeahead('val',typeaheadtext); 
				$(input).blur(); 
			}
		}, 1000*(i+1));
	});
}

function enableRC() {
	$('#recenter').show();
	$("#receivingCenter").removeAttr('disabled');
}

function disableRC(){
	$('#recenter').hide();
	$("#receivingCenter").attr('disabled', true)
}

function enabledCRN() {
	$('#regnoblock').show();
	$("#crnReq").show();
//	$("#crn").attr('required','required');
	$("#crn").removeAttr('disabled');
}

function disableCRN() {
	$('#regnoblock').hide();
	$("#crnReq").hide();
	$("#crn").val("");
	$("#crn").removeAttr('required');
	$("#crn").attr('disabled',true);
}

/*demo code*/
function showChangeDropdown(dropdown)
{
	$('.drophide').hide();
	var showele = $(dropdown).find("option:selected").data('show');
	if(showele)
	{
		$(showele).show();	
	}
}
