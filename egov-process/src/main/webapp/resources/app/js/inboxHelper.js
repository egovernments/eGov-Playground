jQuery('#btnsearch').click(function(e) {

	callAjaxSearch();
});

function callAjaxSearch() {
	drillDowntableContainer = jQuery("#resultTable");
	jQuery('.report-section').removeClass('display-hide');
	reportdatatable = drillDowntableContainer
			.dataTable({
				ajax : {
					url : "/egov-process/inbox/ajaxsearch",
					type : "GET"
				},
				"fnRowCallback" : function(row, data, index) {
					$(row).on(
							'click',
							function() {
								console.log(data.id);
								window.open('/egov-process/'+data.link, '',
										'width=800, height=600');
							});
				},
				"sPaginationType" : "bootstrap",
				"bDestroy" : true,
				"sDom" : "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-xs-3'i><'col-xs-3 col-right'l><'col-xs-3 col-right'<'export-data'T>><'col-xs-3 text-right'p>>",
				"aLengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "All" ] ],
				"oTableTools" : {
					"sSwfPath" : "../../../../../../egov-process/resources/global/swf/copy_csv_xls_pdf.swf",
					"aButtons" : [ "xls", "pdf", "print" ]
				},
				aaSorting : [],
				columns : [ {
					"data" : "sender",
					"sClass" : "text-left"
				},  {
					"data" : "wfDate",
					"sClass" : "text-left"
				},{
					"data" : "natureOfWork",
					"sClass" : "text-left"
				}, {
					"data" : "details",
					"sClass" : "text-left"
				} ]
			});
}