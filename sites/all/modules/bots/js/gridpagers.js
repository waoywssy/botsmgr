jQuery().ready(function(){
	var $ = jQuery; 
	// this is for developer to manage bot's data 
	$("#botsList").navGrid(	'#botsPager',	{view:true,search:false,edit:true,add:true,del:true});
	$("#jobsList").navGrid(	'#jobsPager',	{view:true,search:false,edit:true,add:true,del:true});
	$("#tasksList").navGrid(	'#tasksPager',	{view:true,search:false,edit:true,add:true,del:true});
	$("#runsList").navGrid(	'#runsPager',	{view:true,search:false,edit:false,add:false,del:true});
	$("#eventsList").navGrid(	'#eventsPager',	{view:true,search:false,edit:false,add:false,del:false});
	$("#serversList").navGrid('#serversPager',{view:true,search:false,edit:true,add:true,del:false});


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

	// check if the extention is valide
	function checkInputExt(id, extArray){
		var ext = $('#' + id).val().split('.').pop().toLowerCase();
		if($.inArray(ext, extArray) == -1) {
		    return false;
		}
		return true;
	}

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
















});