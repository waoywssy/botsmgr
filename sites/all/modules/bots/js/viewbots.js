var targetPath = 'bots_data/'; 
var botsPath = '/com/boryi/bots/jobs/'; 
var imgPath = 'sites/all/modules/bots/image/'; 
var $ = jQuery;
var tabs;
var botsArray = [];
var jobsArray = [];

function ShowTab(index){
	var theTabs = $("#tabsContainer");
	theTabs.tabs('enable', index).tabs("select", index);
	for (var i = index + 1; i < 5; i++) {
		// disable lower level tabs
		theTabs.tabs('disable', i);
	};
}

function ReloadByBot(botId){
	$("#botid").val(botId); // set the hidden value
	$('#botsList').jqGrid('setSelection',botId) // set the row as chosen
	reloadJobs(botId);

	ShowTab(1);	// go check jobs
}

function ReloadByJob(jobId){
	$("#jobid").val(jobId);	// set current job
	$('#jobsList').jqGrid('setSelection',jobId);
	reloadTasks(jobId);

	ShowTab(2);
}

function ReloadByTask(taskId){
	$("#taskid").val(taskId);
	$('#tasksList').jqGrid('setSelection',taskId); 
	reloadRuns(taskId);

	ShowTab(3);
}

function ReloadByRun(runId){
	$('#runsList')
		.jqGrid('setSelection',runId); 
	$("#runid").val(runId);
	reloadEvents();

	ShowTab(4);
}

function reloadJobs(botId){
	$('#jobsList')
		.setGridParam({
			url: targetPath + '?type=job&bid=' + botId,
			editurl: 'bots_data_edit?&type=job&bid=' + botId,});
	$('#jobsList')
		.jqGrid('setCaption', 'View jobs of ' + botsArray[botId])
			.trigger("reloadGrid");
}

function reloadTasks(jobId){
	$('#tasksList')
		.setGridParam({
			url:targetPath + '?type=task&bid=' + $("#botid").val() + '&jid=' + jobId,
			editurl: 'bots_data_edit?&type=task&bid=' + $("#botid").val() + '&jid=' + jobId})
		.jqGrid('setCaption', 'View tasks of ' + jobsArray[jobId])
			.trigger("reloadGrid");
}

function reloadRuns(taskId){
	$('#runsList')
		.setGridParam({
			url:targetPath + '?type=run&bid=' + $("#botid").val() + '&jid=' + $("#jobid").val() + '&tid=' + taskId,
			editurl: 'bots_data_edit?&type=run&bid=' + $("#botid").val() + '&jid=' + $("#jobid").val() + '&tid=' + taskId})
		//.jqGrid('setCaption', 'View runs of ' + tasksArray[taskId])
			.trigger("reloadGrid");
}

function reloadEvents(){ 
	$('#eventsList').setGridParam( 
			{url:targetPath + '?type=event&bid=' + $("#botid").val() + '&jid=' + $("#jobid").val() + '&tid=' + $("#taskid").val() + '&rid=' + $("#runid").val()})
		.trigger("reloadGrid");
}

function DumpBot(botId, jobId){ 
	$('#dialog').dialog({
		width:'auto', 
		maxWidth:500, 
		maxHeight:400, 
		minWidth:300, 
		minHeight:200, 
		close: function(){ 
			$('#datalist').empty(); 
		} 
	});
	
	$("#dgdata").button().unbind('click').bind('click', function(){
		$(this).button("disable");
		$('#datalist').empty();
		$.ajax({
			url: 'bots_data_edit?bid=' + botId + '&jid=' + jobId + '&type=job&oper=check',
			success: function(json){
				//var json = $.parseJSON(response.responseText);
				$.each(json.files, function(i, item){
					$('#datalist').prepend('<li><a href="' +json.path + item+ '">' + item + '</a></li>');
				});

				$("#dgdata").button("enable");
				$('#dialog').dialog().show('fast');
			}
		});
	});

	$("#dgexport").button().unbind('click').bind('click', function(){
		$(this).button("disable");
		$("#loading").show();
		$.ajax({
			url: 'bots_data_edit?bid='+botId+'&jid='+jobId+'&type=job&oper=export',
			success: function(response){
				alert(response);
				$("#loading").hide();
			}
		});
		$("#dgexport").button("enable");
	});

	$("#ui-dialog-title-dialog").html('Dump job data - JobId: ' + jobId);
}


function check_server_ip(value, colName, length){
	var reg = /^(\d|[1-9]\d|1\d\d|2([0-4]\d|5[0-5]))\.(\d|[1-9]\d|1\d\d|2([0-4]\d|5[0-5]))\.(\d|[1-9]\d|1\d\d|2([0-4]\d|5[0-5]))\.(\d|[1-9]\d|1\d\d|2([0-4]\d|5[0-5]))$/g;
	if (value.match(reg)){
		// check if ip exists here
		return [true, ''];
	} else  {
		return [false, 'Incorrect IP'];	
	}
}

function check_name(value, colName, length){
	var reg = /^([-_0-9a-zA-Z][-_0-9a-zA-Z\s]*)$/g;
	if (value.match(reg)){
		return [true, ''];
	}
	return [false, 'Incorrect Name'];	
}


function on_name_change(e){
	if (e.keyCode == 32) { 
		$(e.target).val(
			$.trim($(e.target).val().replace(/\s+/g, '')));
	}
	//$('#bts_name').val($.trim($('#bts_name').val().replace(' ', '')));
	$("#bts_path").val(botsPath + $.trim($('#bts_id').val()) 
		+ "-" + $.trim($(e.target).val()) + "/");
}

function on_id_change(e){
	$("#bts_path").val(botsPath + $.trim($(e.target).val()) 
		+ "-" + $.trim($('#bts_name').val()) + "/");
}

jQuery().ready(function(){
	var $ = jQuery; 
	$("#tabs").tabs(); 
	tabs =$('#tabsContainer').tabs({disabled: [1, 2, 3 ,4],}); 
	$("#dialog").hide(); 

	$('#file_upload').uploadify({
		'formData'     	: { 
			'timestamp' : '1358739522', 
			'token'     : 'c376e347a876096d1fee91a9e64b77ac', 
			'type'		: 'bot', 
			'oper'		: 'upload', 
		}, 
		'swf'      		: 'sites/all/modules/bots/misc/uploadify.swf', 
		'uploader' 		: 'bots_data_edit', 
		'fileSizeLimit'	: '10MB', 
		'fileTypeExts'  : '*.zip;*.rar;', 
		'successTimeout': '300',
		'removeTimeout': '200', 
		'onUploadStart' : function(file) { 
            $("#file_upload").uploadify("settings", "formData", 
            	{'botid':$('#botid').val(), 'jobid':$('#h_jid').val()});
        }, 
	}); 

	/*	
	$("#datefrom").datepicker({
		dateFormat: 'yy-mm-dd',
		//beforeShow: function() {$('#ui-datepicker-div').css("z-index","9999!important;"); }
	});
	$("#dateto" ).datepicker({ dateFormat: 'yy-mm-dd',});
	var today = new Date();
	var a_week_ago = new Date();
	a_week_ago.setDate(a_week_ago.getDate() -7);

	$("#datefrom").datepicker('setDate', a_week_ago);
	$("#dateto").datepicker('setDate', today);
	*/
	//var lastSel;
	// Bots list
	var botsView = $("#botsList").jqGrid({ 
		url:targetPath + "?type=bot", 
		datatype: "json", 
		colNames:['Id','Bot Name','Company','Sector1','Sector2','Sector3', 'Version', 'Description','Enabled','Created','Last Updated','Path','Jar File','File'], 
		colModel:[ 
				{name:'bts_id',index:'bts_id', editable: true, editrules:{number:true, 
						required:true, maxValue:99999,},width:8,
						editoptions:{dataEvents: [{ type: 'keyup', fn: on_id_change }]},}, 
				{name:'bts_name',index:'bts_name', editable: true, editrules:{required:true, custom:true, custom_func:check_name},
						editoptions:{dataEvents: [{ type: 'keyup', fn: on_name_change }]}, width:75}, 
				{name:'bts_company',index:'bts_company', editable: true,width:30, edittype:"select",
						editoptions: { value: "-:-"},}, 
				{name:'bts_sector1',index:'bts_sector1', editable: true, editrules:{required:true,},width:50, edittype:"select",
						editoptions: { value: "eCommerce:eCommerce;RetailListing:RetailListing"},}, 
				{name:'bts_sector2',index:'bts_sector2', editable: true,width:10, 
					//edittype:"select", editoptions: { value: "-:-"},
				}, 
				{name:'bts_sector3',index:'bts_sector3', editable: true,width:10, 
					//edittype:"select", editoptions: { value: "-:-"},
				}, 
				{name:'bts_version',index:'bts_version', editable: true,width:22, editoptions: { defaultValue: "1.0"},}, 
				{name:'bts_description',index:'bts_description', editable: true,width:90, }, 
				{name:'bts_enabled',index:'bts_enabled', width:4, align:"center", }, 
				{name:'bts_created',index:'bts_created', width:60, }, 
				{name:'bts_last_updated',index:'bts_last_updated', width:60, }, 
				{name:'bts_path',index:'bts_path', editable: true, editoptions: { defaultValue: botsPath}, width:140,}, 
				{name:'bts_filename',index:'bts_filename', editable: true, width:80, editrules:{required:true,}}, 
				{name:'bts_file',index:'bts_file', editable: true, edittype: 'file', width:1,
						editoptions: { enctype: "multipart/form-data", }, //formatter:uploadBot,
						formoptions: {elmprefix:'Rar/Zip',},}], //formoptions: {elmsuffix:'Rar/Zip package'},
		rowNum:15,
		mtype:'GET',
		width:900,
		height:'100%',
		//gridResize:[{minWidth:450, minHeight:200}], // not work 
		editurl: "bots_data_edit?&type=bot",
		rowList:[15,25,50],
		pager: $('#botsPager'),
		/*
		onSelectRow: function(id) {
            if (id && id !== lastSel) {
            	var grid = $("#botsList");
                grid.jqGrid('restoreRow',lastSel);
                var cm = grid.jqGrid('getColProp','bts_id');
                cm.editable = false;
                //grid.jqGrid('editRow', id, true);
                cm.editable = true;
                lastSel = id;
            }
        },*/
		gridComplete: function(){
			var ids = $("#botsList").jqGrid('getDataIDs');

			for (var i = ids.length - 1; i >= 0; i--) {
				var rowData = $('#botsList').jqGrid('getRowData', ids[i]);
				botsArray[rowData.bts_id]=rowData.bts_name;
			}

			if (ids.length > 0) {
				$("#botid").attr("value", ids[0]);
				$('#botsList').jqGrid('setSelection',ids[0]);
			}
		},
		/*ondblClickRow: function(id, irow, icol, e){*/// note the params
		ondblClickRow: function(id){ 
			ReloadByBot(id);
		},/*
		beforeSelectRow: function(id){
			$('#botsList tr.ui-state-highlight').removeClass('ui-state-highlight');
			$('#botsList tr#' + id).addClass('ui-state-highlight'); 
		},*/
		viewrecords: true, 
		sortname: 'bts_id',
		sortorder: "asc", 
		caption:"View Bots",
		loadComplete: function() {
			$("tr.jqgrow", this).contextMenu('menuBot', {
				menuStyle:{'width':'120px'}, // custom the pop-up menu's width
				bindings: {
					'menuBotEnable': function(trigger){menuEnable(trigger.id, 'bot', 'botsList')}, 
					'menuBotClearEvents': function(trigger){menuClear(trigger.id, 'bot','clearevent')}, 
					'menuBotClearAllEvents': function(trigger){menuClear(trigger.id, 'bot','clearallevent')}, 
					'menuBotClearRuns': function(trigger){menuClear(trigger.id, 'bot','clearrun')}, 
					'menuBotClearAllRuns': function(trigger){menuClear(trigger.id, 'bot','clearallrun')}, 
					'menuBotClearTasks': function(trigger){menuClear(trigger.id, 'bot','cleartask')}, 
					'menuBotClearJobs': function(trigger){menuClear(trigger.id, 'bot','clearjob')}, 
				},
				//onContextMenu: function(event) { return true;}
			});
		},
	});
	botsView.navGrid('#botsPager',{view:true,search:false,edit:true,add:true,del:true});
	
	// the add button events
	$("#add_botsList").unbind('click').click(function() {
		$("#botsList").jqGrid('editGridRow',"new", {
			height:'auto', 
			width:'200xp', 
			recreateForm:true, 
			closeAfterAdd:true,
			beforeSubmit: function(postdata) {
							if (!checkInputExt('bts_file', ['zip','rar'])){
								return[false, ".zip or .rar expected"];
							}

							if (!checkInputExt('bts_filename', ['jar'])){
								return[false, ".jar expected"];
							}

							postdata['bts_file'] = $("#bts_file").val();
							// put validation here to filter the package
							// about to be uploaded
							if ($("#bts_file").val() != "") {
								return[true, ""];
							} else {
								return[false, "No file attached!"];
							}
						},
			beforeShowForm: function (form) {
				$('#tr_bts_id', form).show();
				//$("#botsList").showCol("bts_file");
                // get suggested botid
                $.post( "bots_data_edit?&type=bot", 
                	{ oper:'botid'},
                	function(data){
                		$('#bts_id').val(data);
                	});

                getCompanyList();
			},
			afterSubmit: function(response, postdata) {
							var data = $.parseJSON(response.responseText);
							if (data.id != -1) {
								if ($("#bts_file").val() != "") {
						            ajaxFileUpload(data.id, data.path,
						            	'bots_data_edit?&type=bot&oper=file',
						            	'bts_file');
						        }
							}
							$('#ui-widget-overlay').hide();
						    //botsView.trigger('reloadGrid');
					    	return [true, '', data.id];
						}, // use extra plugin to upload bots/jobs package
		});
		//$("#botsList").setGridParam({ datatype: 'json' }); 
		// This is the line that undoes the loadonce = true
		// $('#botsList').trigger('reloadGrid');
	});

	// check if the extention is valide
	function checkInputExt(id, extArray){
		var ext = $('#' + id).val().split('.').pop().toLowerCase();
		if($.inArray(ext, extArray) == -1) {
		    return false;
		}
		return true;
	}

	function getCompanyList(selectCompany){
		$.post( "bots_data_edit?&type=company", { oper:'list',},
	    	function(json){
	    		if (json!=null && json != undefined){
	    			var options = '';
	    			for (var i = json.length - 1; i >= 0; i--) {
	    				var s = json[i];
	    				options += '<option value="' + s.id;

	    				if (selectCompany != null && selectCompany != undefined 
	    					&& selectCompany == s.name){
	    					 options += '" selected="selected">';
	    				} else {
	    					 options += '">';
	    				}
	    				options += s.name + '</option>';
	    			}
	    			$('#bts_company').empty().append(options);
				}
	    	});
	}

		// the edit button events
	$("#edit_botsList").click(function(){
		var gr = $("#botsList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			var rowData = $('#botsList').jqGrid('getRowData', gr);

			$("#botsList").jqGrid('editGridRow', gr, {
				closeAfterEdit:true, 
				recreateForm:true, 
				beforeSubmit: function(postdata) { 
							
							if ($("#bts_file").val() != '' 
								&& !checkInputExt('bts_file', ['zip','rar'])){
								return[false, ".zip or .rar expected"];
							}
							
							if (!checkInputExt('bts_filename', ['jar'])){
								return[false, ".jar expected"];
							}

							postdata['bts_file'] = $("#bts_file").val();
							//return[success, message];
							return[true, ""];
						},
				beforeShowForm: function (form) {
					$('#tr_bts_id', form).hide();
	                getCompanyList(rowData.bts_company);
				},
				afterSubmit: function(response, postdata) {
							$('.ui-widget-overlay').hide();
							var data = $.parseJSON(response.responseText);
							if (data.id != -1){
								if ($("#bts_file").val() != "") {
						            ajaxFileUpload(data.id, data.path,
						            	'bots_data_edit?&type=bot&oper=file',
						            	'bts_file');
						        }
							}
						    //botsView.trigger('reloadGrid');
							var cm = $("#botsList").jqGrid('getColProp','bts_id');
	        				cm.editable = true;
					    	return [true, '', data.id];
						}, // use extra plugin to upload bots/jobs package*/
				});
		}
	});

	function ajaxFileUpload(id, path, target, element) {
	    $("#loading")
	    	.ajaxStart(function() {$(this).show();})
	    	.ajaxComplete(function() {$(this).hide();}); 

	    if (element == 'bts_file'){
	    	$.ajaxFileUpload({
		        url: 'bots_data_edit?type=bot&oper=file',
		        secureuri: false,
		        fileElementId: 'bts_file',
		        dataType: 'json',
		        data: {id: id, path: path},
		        success: function (data, status) {
		        	alert(data);
		        },
		        // error: function (data, status, e)
		    });
	    } else {
	    	$.ajaxFileUpload({
		        url: 'bots_data_edit?type=job&oper=file',
		        secureuri: false,
		        fileElementId: 'jbs_file',
		        dataType: 'json',
		        data: {id: id, path: path},
		        success: function (data, status) {
		        	alert(data);
		        },
		        // error: function (data, status, e)
		    });
	    }
	}   

	// Jobs list
	var jobsView = $("#jobsList").jqGrid({
		datatype: 'local', 
		colNames:['Save', 'Job Id', 'Job Name', 'Description', 'Enabled', 'Created', 'Last Updated', 'Path', 'XML File', 'File',], 
		colModel:[ 
				{name:'act',index:'act', width:30, align:'center',sortable:false},
				{name:'jbs_id',index:'jbs_id', editable: true,width:25,editrules:{required:true,},}, 
				{name:'jbs_name',index:'jbs_name', editable: true,width:75,
					editrules:{required:true, custom:true, custom_func:check_name}}, 
				{name:'jbs_description',index:'jbs_description', editable: true,width:75}, 
				{name:'jbs_enabled',index:'jbs_enabled', editable: false,width:33, align:"center"}, 
				{name:'jbs_created',index:'jbs_created', editable: false,width:60}, 
				{name:'jbs_last_updated',index:'jbs_last_updated', editable: false,width:60}, 
				{name:'jbs_path',index:'jbs_path', editable: false,width:40, editrules:{required:true,},}, 
				{name:'jbs_filename',index:'jbs_filename', width:40, editable: true, }, 
				{name:'jbs_file',index:'jbs_file', editable: true, edittype: 'file', width:1,
						editoptions: { enctype: "multipart/form-data"},},
				],
		rowNum:15, 
		editurl: "bots_data_edit?&type=job&bid=" + $("#botid").val(),
		width:880,
		height:'100%',
		rowList:[15,25,50],
		pager: $('#jobsPager'), 
		emptyrecords: "No jobs for this bot",
		gridComplete: function(){
			var ids = $("#jobsList").jqGrid('getDataIDs');
			// jobsArray = []; // need to be cleared first 
			for (var i = ids.length - 1; i >= 0; i--) {
				var rowData = $('#jobsList').jqGrid('getRowData', ids[i]);
				jobsArray[rowData.jbs_id] = rowData.jbs_name;
			}
		},
		loadComplete: function(){ 
			$("tr.jqgrow", this).contextMenu('menuJob', {
				menuStyle:{'width':'120px'}, // custom the pop-up menu's width
				bindings: {
					'menuJobEnable': function(trigger){menuEnable(trigger.id, 'job', 'jobsList', '&bid=' + $('#botid').val())},
					'menuJobClearEvents': function(trigger){menuClear(trigger.id, 'job', 'clearevent', '&bid=' + $('#botid').val())},
					'menuJobClearRuns': function(trigger){menuClear(trigger.id, 'job', 'clearrun', '&bid=' + $('#botid').val())},
					'menuJobClearTasks': function(trigger){menuClear(trigger.id, 'job', 'cleartask', '&bid=' + $('#botid').val())},
					'menuJobStart': function(trigger){menuClear(trigger.id, 'job', 'start', '&bid=' + $('#botid').val())},
					'menuJobUpload':function(trigger){ 
						//var data = $('#results-grid').jqGrid('getRowData');
						//var data = $('#results-grid').jqGrid('getRowData', trigger.id);
						//$('#h_bid').val(trigger.id);
						var jobname = $('#jobsList').jqGrid ('getCell', trigger.id, 'jbs_name');
						var uploadPanel = $('#upload_container').dialog({
							width:'auto',
							title: 'Upload Scripts for :  ' + jobname, 
						});

						$('#h_jid').val(trigger.id);
					},
				},
				//onContextMenu: function(event) { return true;}
			});
			
			var ids = $("#jobsList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var ret = $("#jobsList").jqGrid('getRowData', i + 1);
				var cl = ids[i];
				var disabled = (ret.count > 0);

				var de = "<img src='" + imgPath + "save.png' class='jobIcon' onclick=\"DumpBot('"+$("#botid").val()+"', '"+cl+"');\"  />";
				
				$("#jobsList").jqGrid('setRowData',ids[i],{act:de}); //ce1 + de
				if (i==0){
					$("#jobid").attr("value", ids[0]);
					$('#jobsList').jqGrid('setSelection',ids[0]);
				}
			}
		},
		ondblClickRow: function(id){
			ReloadByJob(id);
		},
		viewrecords: true, 
		sortname: 'jbs_id', 
		sortorder: "asc", 
		caption:"View Jobs"}
	);
	jobsView.navGrid('#jobsPager',{view:true,search:false,edit:true,add:true,del:true});

	// the add button events
	$("#add_jobsList").unbind('click').click(function(){
		$("#jobsList").jqGrid('editGridRow',"new",{
			height:'auto', 
			width:'200xp', 
			recreateForm:true, 
			closeAfterAdd:true,
			addCaption:"Add job for - " + botsArray[$("#botid").val()],
			beforeSubmit: checkJobFormBeforeSubmit,
			beforeShowForm: function (form) {
							$('#tr_jbs_id', form).show();
			                // get suggested jobid
			                $.post( "bots_data_edit?&type=job", 
			                	{ oper:'jobid', bid:$("#botid").val()},
			                	function(data){
			                		$('#jbs_id').val(data);
			                	});
							// set default value for path
							$('#jbs_path').val(botsPath + botsArray[$("#botid").val()] + '/config/');
							$('#jbs_name').val(botsArray[$("#botid").val()] + " - job");
            			},
			afterSubmit: checkJobFormAfterSubmit, 
			// use extra plugin to upload bots/jobs package
		});
	});
	
	// the edit button events
	$("#edit_jobsList").click(function(){
		var gr = $("#jobsList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			$("#jobsList").jqGrid('editGridRow',gr,{
				closeAfterEdit:true, 
				recreateForm:true, 
				beforeSubmit: checkJobFormBeforeSubmit,
				afterSubmit: checkJobFormAfterSubmit,
				beforeShowForm: function (form) {
							$('#tr_jbs_id', form).hide();
				},
			});
		}
	});
	$("#jobsList").setGridParam({ datatype: 'json' }); 

	function checkJobFormBeforeSubmit(postdata){
		var jbs_filename = $("#jbs_filename").val();
		if (jbs_filename != ""){
			if (!checkInputExt('jbs_filename', ['xml'])){
				return[false, ".xml expected"];
			}	
		} else {
			if ($("#jbs_file").val() == ""){
				return[false, "Please give an XML name or choose a config file"];
			} else {
				if(!checkInputExt('jbs_file', ['xml'])){
					return[false, ".xml expected"];
				}
			}
			postdata['jbs_filename'] = $("#jbs_file").val();
		}
		postdata['jbs_file'] = $("#jbs_file").val();
		postdata['bts_id'] = $("#botid").val();

		return[true, ""];
	}

	function checkJobFormAfterSubmit(response, postdata) {
		var data = $.parseJSON(response.responseText);
		if (data.id != -1){
			if ($("#jbs_file").val() != "") {
			ajaxFileUpload(data.id, data.path,
				'bots_data_edit?type=job&oper=file',
				'jbs_file');
			}
		}
		$('#ui-widget-overlay').hide();
		//botsView.trigger('reloadGrid');
		return [true, '', data.id];
	}

	// Tasks list
	var tasksView = $("#tasksList").jqGrid({
		datatype: "local", 
		colNames:['Task Id','Task Name', 'Description','Enabled','Created','Last Updated','Minute', 'Hour', 'DOM', 'Month', 'DOW', 'Status','Server'], 
		colModel:[ 
				{name:'tsk_id',index:'tsk_id', editable: true,width:8}, 
				{name:'tsk_name',index:'tsk_name', editable: true,width:55, 
					editrules:{required:false, custom:true, custom_func:check_name}}, 
				{name:'tsk_description',index:'tsk_description', editable: true,width:40}, 
				{name:'tsk_enabled',index:'tsk_enabled', width:4, align:"center"}, 
				{name:'tsk_created',index:'tsk_created', width:50}, 
				{name:'tsk_last_updated',index:'tsk_last_updated', width:50}, 
				{name:'tsk_minute',index:'tsk_minute', editable: true,width:12, //editrules:{number:true, required:true, minValue:0, maxValue:59, }, editoptions: { defaultValue: '0'}, },
					edittype:"select", editoptions: { value: '0:0;1:1;2:2;3:3;4:4;5:5;6:6;7:7;8:8;9:9;10:10;11:11;12:12;13:13;14:14;15:15;16:16;17:17;18:18;19:19;20:20;21:21;22:22;23:23;24:24;25:25;26:26;27:27;28:28;29:29;30:30;31:31;32:32;33:33;34:34;35:35;36:36;37:37;38:38;39:39;40:40;41:41;42:42;43:43;44:44;45:45;46:46;47:47;48:48;49:49;50:50;51:51;52:52;53:53;54:54;55:55;56:56;57:57;58:58;59:59'},}, 
				{name:'tsk_hour',index:'tsk_hour', editable: true,width:12, //editrules:{number:true, required:true, minValue:0, maxValue:23, }, editoptions: { defaultValue: '0'},
					edittype:"select", editoptions: { value: '*:*;1:1;2:2;3:3;4:4;5:5;6:6;7:7;8:8;9:9;10:10;11:11;12:12;13:13;14:14;15:15;16:16;17:17;18:18;19:19;20:20;21:21;22:22;23:23'},}, 
				{name:'tsk_dom',index:'tsk_dom', editable: true,width:12, //editrules:{number:true, minValue:1, maxValue:31,},
					edittype:"select", editoptions: { value: '*:*;1:1;2:2;3:3;4:4;5:5;6:6;7:7;8:8;9:9;10:10;11:11;12:12;13:13;14:14;15:15;16:16;17:17;18:18;19:19;20:20;21:21;22:22;23:23;24:24;25:25;26:26;27:27;28:28;29:29;30:30;31:31'},}, 
				{name:'tsk_month',index:'tsk_month', editable: true,width:12, //editrules:{number:true, minValue:1, maxValue:12,},
					edittype:"select", editoptions: { value: '*:*;1:Jan;2:Feb;3:Mar;4:Apr;5:May;6:Jun;7:Jul;8:Aug;9:Sep;10:Oct;11:Nov;12:Dec'},}, 
				{name:'tsk_dow',index:'tsk_dow', editable: true,width:12, //editrules:{number:true, minValue:0, maxValue:6,},
					edittype:"select", editoptions: { value: '*:*;0:Sun;1:Mon;2:Tue;3:Wed;4:Thu;5:Fri;6:Sat'},}, 
				{name:'tsk_running',index:'tsk_running', width:30}, 
				{name:'tsk_srv_id',index:'tsk_srv_id', editable: false,width:60, edittype:"select",
						editoptions: { value: '-1:-'},}, 
				],
		rowNum:15, 
		width:880,
		height:'100%',
		rowList:[15,25,50],
		emptyrecords: "No tasks for the job",
		pager: $('#tasksPager'), 
		gridComplete: function() {
			var ids = $("#tasksList").jqGrid('getDataIDs');
			if (ids.length > 0) {
				$("#taskid").attr("value", ids[0]);
				$('#tasksList').jqGrid('setSelection',ids[0]);
			}
		},
		ondblClickRow: function(id){
			ReloadByTask(id);
		},
		loadComplete: function(){
			$("tr.jqgrow", this).contextMenu('menuTask', {
				menuStyle:{'width':'120px'}, // custom the pop-up menu's width
				bindings: {
					'menuTaskEnable': function(trigger){menuEnable(trigger.id, 'task', 'tasksList', 
										'&bid=' + $('#botid').val() + "&jid=" + $('#jobid').val())},
					'menuTaskStart': function(trigger){menuClear(trigger.id, 'task', 'start', 
										'&bid=' + $('#botid').val() + "&jid=" + $('#jobid').val())},
					'menuTaskStop': function(trigger){menuClear(trigger.id, 'task', 'stop', 
										'&bid=' + $('#botid').val() + "&jid=" + $('#jobid').val())},
					'menuTaskClearEvents': function(trigger){menuClear(trigger.id, 'bot','clearallevent')}, 
					'menuTaskClearRuns': function(trigger){menuClear(trigger.id, 'bot','clearrun')}, 
				},
				//onContextMenu: function(event) { return true;}
			});
		},
		viewrecords: true, 
		sortname: 'tsk_id', 
		sortorder: "asc", 
		caption:"View Tasks"}
	);
	tasksView
		.navGrid('#tasksPager',{view:true,search:false,edit:true,add:true,del:true})
		.setGridParam({ datatype: 'json' }); 
	
	// the add button events
	$("#add_tasksList").unbind('click').click(function(){
		$("#tasksList").jqGrid('editGridRow',"new",{
			height:'auto', 
			width:'200xp', 
			recreateForm:true, 
			closeAfterAdd:true, 
			addCaption:"Add task for - " + jobsArray[$("#jobid").val()], 
			beforeShowForm: function (formID) {
                // get suggested jobid
                $.post( "bots_data_edit?&type=task", 
                	{ oper:'taskid', bid:$("#botid").val(), jid:$("#jobid").val()},
                	function(data){
                		$('#tsk_id').val(data);
                	});

	                //getServerList();

				// set default value for path
				$('#tsk_name').val(botsArray[$("#botid").val()] + " - " + 
					jobsArray[$("#jobid").val()] + "- task");
				//return[true, ""];
			},
		});
	});

		// the edit button events
	$("#edit_tasksList").click(function(){
		var gr = $("#tasksList").jqGrid('getGridParam','selrow');
		if(gr != null) {
			var rowData = $('#tasksList').jqGrid('getRowData', gr);

			$("#tasksList").jqGrid('editGridRow',gr,{
				closeAfterEdit:true, 
				recreateForm:true, 
				beforeShowForm: function(){
					//getServerList(rowData.tsk_srv_id);
					//var cm = $("#tasksList").jqGrid('getColProp','tsk_srv_id');
					//cm.editable = false;
				},
				onClose: function(){
					$('#ui-widget-overlay').hide();
				},
				beforeSubmit: function(postdata) { 
							postdata['bts_id'] = $("#botid").val();
							postdata['jbs_id'] = $("#jobid").val();
							return [true, ""];
				},
			});
		}
	});
	
	// this method is obsolete for now 
	function getServerList(selectServer){ 
		$.post("bots_data_edit?&type=server", { oper:'list',},
	    	function(json){
	    		if (json!=null && json != undefined){
	    			var options = '';
	    			for (var i = json.length - 1; i >= 0; i--) {
	    				var s = json[i];
	    				options += '<option value="' + s.id;

	    				if (selectServer != null && selectServer != undefined 
	    					&& selectServer == s.server){
	    					 options += '" selected="selected">';
	    				} else {
	    					 options += '">';
	    				}
	    				options += s.server + '</option>';
	    			}
	    			$('#tsk_srv_id').empty().append(options);
				}
	    	});
	}

	// Runs list
	var runsView = $("#runsList").jqGrid({
		datatype: "local",
		colNames:['Run Id','Start', 'End', 'Duration(hour)','Result','Server'],
		colModel:[
				{name:'rns_id',index:'rns_id', editable: true,width:30, align:'right'},
				{name:'rns_start',index:'rns_start', editable: true,width:60, align:'center'},
				{name:'rns_end',index:'rns_end', editable: true,width:60, align:'center'},
				{name:'rns_duration',index:'rns_duration', editable: true,width:40, align:'right'},
				{name:'rns_result',index:'rns_result', editable: true,width:33, align:'center'},
				{name:'rns_srv_id',index:'rns_srv_id', editable: true,width:20}],
		rowNum:15, 
		width:880,
		height:'100%',
//		editurl: "bots_data_edit?&type=run&bid=" + $("#botid").val() 
//					+ "&jid= " + $("#jobid").val()
//					+ "&tid= " + $("#taskid").val(),
		rowList:[15,25,50],
		emptyrecords: "No runs for the task",
		pager: $('#runsPager'), 
		gridComplete: function(){
			var ids = $("#runsList").jqGrid('getDataIDs');
			if (ids.length > 0){
				$("#runid").attr("value", ids[0]);
				$('#runsList').jqGrid('setSelection',ids[0]);
			}
		},
		ondblClickRow: function(id){
			ReloadByRun(id);
			$("#tabsContainer").tabs("select", 4);
		},
		viewrecords: true,
		sortname: 'rns_id',
		sortorder: "desc",
		caption:"View Runs"}
	);
	runsView
		.navGrid('#runsPager',{view:true,add:false, edit:false, search:false, del:true})
		.setGridParam({ datatype: 'json' }); 
	
	// Events list
	var eventsView = $("#eventsList").jqGrid({
		datatype: "local", 
		colNames:['EventTime','Subject', 'Detail', 'Severity'], 
		colModel:[
				{name:'vnt_datetime',index:'vnt_datetime', width:60, align:'center'}, 
				{name:'vnt_subject',index:'vnt_subject', width:75}, 
				{name:'vnt_detail',index:'vnt_detail', width:200}, 
				{name:'vnt_severity',index:'vnt_severity', width:20}], 
		rowNum:10, 
		width:880,
		height:'100%',
		rowList:[10,20,30], 
		emptyrecords: "No events for the run",
		pager: $('#eventsPager'), 
		viewrecords: true, 
		sortname: 'vnt_datetime', 
		sortorder: "desc", 
		caption:"View Events" }
	);
	eventsView
		.navGrid('#eventsPager',{view:true,search:false,edit:false,add:false,del:false})
		.setGridParam({ datatype: 'json' }); 

	// Servers list
	var serversView = $("#serversList").jqGrid({
		url:targetPath + '?type=server', 
		datatype: "json", 
		colNames:['ServerId','Name', 'IP', 'Enabled', 'CPU', 'Memory', 'Disk', 'Receiving', 'Sending', 'Created', 'Disabled', 'Description'], 
		colModel:[ 
				{name:'srv_id',index:'srv_id', editable: true, width:10}, 
				{name:'srv_name',index:'srv_name', editable: true, 
					editrules:{required:true, custom:true, custom_func:check_name}, width:35}, 
				{name:'srv_ip',index:'srv_ip', editable: true, 
					editrules:{required:true, custom:true, custom_func:check_server_ip}, width:30,
					/*formoptions:{elmprefix:'(*)', rowpos:1, colpos:2....}*/}, 
				{name:'srv_enable',index:'srv_enable', width:4}, 
				{name:'srv_cpu',index:'srv_cpu', width:10}, 
				{name:'srv_memory',index:'srv_memory', width:10},
				{name:'srv_disk',index:'srv_disk', width:15},
				{name:'srv_receiving',index:'srv_receiving', width:15},
				{name:'srv_sending',index:'srv_sending', width:15},
				{name:'srv_created',index:'srv_created', width:30},
				{name:'srv_disabled',index:'srv_disabled', width:30},
				{name:'srv_description',index:'srv_description', width:20}],
		rowNum:15, 
		width:880, 
		height:'100%',
		rowList:[15,25,50],
		emptyrecords: "No servers to display",
		pager: $('#serversPager'),
		viewrecords: true, 
		sortname: 'srv_created', 
		sortorder: "desc", 
		editurl: "bots_data_edit?&type=server",
		caption: "View Servers",
		loadComplete: function() {
			$("tr.jqgrow", this).contextMenu('menuServer', {
				bindings: {
					'menuServerView': function(trigger){menuEnable(trigger.id, 'server', 'serversList')},
					'menuServerEnable': function(trigger){menuEnable(trigger.id, 'server', 'serversList')},
				},
				onContextMenu: function(event) {
					return true;
				}
			});
		},
		} 
	);
	serversView.navGrid('#serversPager',{view:true,search:false,edit:true,add:true,del:false});

	// the add button events
	$("#add_serversList").unbind('click').click(function(){
		$("#serversList").jqGrid('editGridRow',"new",{
			height:'auto', 
			width:'200xp', 
			reloadAfterSubmit:true,
			recreateForm:true, 
			closeAfterAdd:true,
			afterShowForm:addStarToServerLable,
			beforeShowForm: function (formID) {
                // get suggested jobid
                $.post( "bots_data_edit?&type=server", 
                	{ oper:'serverid',},
                	function(data){
                		$('#srv_id').val(data);
                	});
            },
		});
		serversView.setGridParam({ datatype: 'json' }); 
		// This is the line that undoes the loadonce = true
		serversView.trigger('reloadGrid');
	});

		// the edit button events
	$("#edit_serversList").click(function(){
		var gr = $("#serversList").jqGrid('getGridParam','selrow');
		if( gr != null ) {
			$("#serversList").jqGrid('editGridRow',gr,{
				closeAfterEdit:true,
				afterShowForm:addStarToServerLable,
			});
		}
	});

	function addStarToServerLable(){
		$("#tr_srv_ip .CaptionTD").html('*IP');
		$("#tr_srv_name .CaptionTD").html('*Name');
	}

	function menuEnable(tid, type, target, extra){
		if (extra==null || extra==undefined){ extra=''; }

		$.post( "bots_data_edit?&type=" + type + extra, { oper:'enable', id:tid}, 
			function( data ) {
				if (target != null && target != undefined){
					$('#' + target).trigger('reloadGrid');
				}
			});
	}

	function menuClear(tid, type, clear, extra){
		if (extra==null || extra==undefined){ extra=''; }
		$.post( "bots_data_edit?&type=" + type + extra, { oper:clear, id:tid},
			function( data ) {
				alert(data);
			});
	}
}) 