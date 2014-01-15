<?php
/***
* Get the min available id in array, starting from 1
*/
function getMinGapId($array) {
	$len = count($array);
	if ($len == 0) {
		return 1;
	} else if ($array[$len - 1] == $len){
		// the existing ids has no gap
		return $len + 1;
	} else {
		$start = 0;
		$end = $len - 1;

		while ($start <= $end) {
			$mid = (($end - $start) >> 1) + $start;
			if ($array[$mid] == $mid + 1){
				$start = $mid + 1;
			} else if ($array[$mid] > $mid + 1){
				$end = $mid - 1;
			} 
		}
		return $start + 1;
	}
}


/**
* get the cron tab schedule string of certain task
*/
function get_cron_str($bts_id, $jbs_id, $tsk_id, $cmd){
	$old = db_select('tasks_tsk', 'tsk')
	        ->fields('tsk', array('tsk_minute', 'tsk_hour', 'tsk_dom', 'tsk_month', 'tsk_dow',))
	        ->condition('tsk_bts_id', $bts_id)
		    ->condition('tsk_jbs_id', $jbs_id)
		    ->condition('tsk_id', $tsk_id)
	        ->execute()
	        ->fetchAssoc();

	// if the value from database table is null, use '*' in the crontab schedule
	$min = ($old['tsk_minute'] == NULL) ? '*' : $old['tsk_minute'];
	$hour = ($old['tsk_hour'] == NULL) ? '*' : $old['tsk_hour'];
	$dom = ($old['tsk_dom'] == NULL) ? '*' : $old['tsk_dom'];
	$month = ($old['tsk_month'] == NULL) ? '*' : $old['tsk_month'];
	$dow = ($old['tsk_dow'] == NULL) ? '*' : $old['tsk_dow'];

	return "$min $hour $dom $month $dow $cmd";
}

function edit_company_data(){
	$op = $_REQUEST['oper'];

	switch ($op) {
		case 'list':
			db_set_active('bot_central');
			
			$results = db_select('companies_cmp', 'cmp')
			            ->fields('cmp')
			            ->orderBy('cmp_name', 'ASC')
			            ->execute();
		    $companies = array();
		    foreach ($results as $data) {
				$companies[] = array(
					'id' => $data->cmp_id, 
					'name' => $data->cmp_name);
			}
			db_set_active();
			
			drupal_json_output($companies);
			break;
		}	
}

function edit_task_data(){
	$op = $_REQUEST['oper'];
	
	db_set_active('bot_central');

	switch ($op) {
		case 'add':
			$bts_id = $_REQUEST['bid'];
			$jbs_id = $_REQUEST['jid'];

			$tsk_id = $_REQUEST['tsk_id'];
			$tsk_description = $_REQUEST['tsk_description'];
			$tsk_dom = $_REQUEST['tsk_dom'];
			$tsk_dow = $_REQUEST['tsk_dow'];
			$tsk_hour = $_REQUEST['tsk_hour'];
			$tsk_minute = $_REQUEST['tsk_minute'];
			$tsk_month = $_REQUEST['tsk_month'];
			$tsk_name = $_REQUEST['tsk_name'];
			$tsk_srv_id = $_REQUEST['tsk_srv_id'];
			$tsk_srv_id = is_numeric($tsk_srv_id) ? $tsk_srv_id : -1; 

			$asteria = '*';	// use * as default value in crontab is not specified 
			
			$tsk_minute = is_numeric($tsk_minute) ? $tsk_minute : $asteria; 
			$tsk_hour = is_numeric($tsk_hour) ? $tsk_hour : $asteria; 
			$tsk_dom = is_numeric($tsk_dom) ? $tsk_dom : $asteria; 
			$tsk_month = is_numeric($tsk_month) ? $tsk_month : $asteria; 
			$tsk_dow = is_numeric($tsk_dow) ? $tsk_dow : $asteria; 

			// if the same bot, same job already has this task id
			$condition_same_task_id = db_and()
					->condition('tsk_bts_id', $bts_id)
				    ->condition('tsk_jbs_id', $jbs_id)
				    ->condition('tsk_id', $tsk_id);
			
			// if the same bot, same job already has a same schedule
			$condition_same_schedule = db_and()
		            ->condition('tsk_bts_id', $bts_id)
		            ->condition('tsk_jbs_id', $jbs_id)
		            ->condition('tsk_minute', $tsk_minute)
		            ->condition('tsk_hour', is_numeric($tsk_hour) ? $tsk_hour : NULL)
		            ->condition('tsk_dom', is_numeric($tsk_dom) ? $tsk_dom : NULL)
		            ->condition('tsk_month', is_numeric($tsk_month) ? $tsk_month : NULL)
		            ->condition('tsk_dow', is_numeric($tsk_dow) ? $tsk_dow : NULL);

            $or = db_or()
		            ->condition($condition_same_task_id)
		            ->condition($condition_same_schedule);

			$exists = db_select('tasks_tsk', 'tsk')
		            ->fields('tsk')
		            ->condition($or)
		            ->execute()->rowCount();

			if ($exists == 0){
				$now = date('Y-m-d H:i:s');
	        	$fields = array(
						  'tsk_bts_id' => $bts_id,
						  'tsk_jbs_id' => $jbs_id,
						  'tsk_id' => $tsk_id,
						  'tsk_name' => $tsk_name,
						  'tsk_description' => $tsk_description,
						  'tsk_enabled' => 1,
						  'tsk_created' => $now,
						  'tsk_last_updated' => $now,
						  'tsk_minute' => $tsk_minute,
						  'tsk_srv_id' => $tsk_srv_id,
						);

				if (is_numeric($tsk_hour)){
        			$fields += array('tsk_hour' => $tsk_hour);
        		}
        		if (is_numeric($tsk_dom)){
        			$fields += array('tsk_dom' => $tsk_dom);
        		}
        		if (is_numeric($tsk_dow)){
        			$fields += array('tsk_dow' => $tsk_dow);
        		}
        		if (is_numeric($tsk_month)){
        			$fields += array('tsk_month' => $tsk_month);
        		}

        		if (is_numeric($tsk_minute)){
					db_insert('tasks_tsk')
						->fields($fields)
						->execute();

					// the serverid should be selected in a proper method
					// we're using 1 for now 
					$cmd = get_cmd(1, $bts_id, $jbs_id, $tsk_id);

					$task = "$tsk_minute $tsk_hour $tsk_dom $tsk_month $tsk_dow $cmd";

					$output = shell_exec('crontab -l');
					file_put_contents('/tmp/crontab.txt', $output . $task . PHP_EOL);
					exec('crontab /tmp/crontab.txt');

				} else {
					echo 'no minute specified';
				}
			}

			break;

		case 'edit':
			$bts_id = $_REQUEST['bid'];
			$jbs_id = $_REQUEST['jid'];
			$id = $_REQUEST['id']; // the original task id

			$tsk_id = $_REQUEST['tsk_id'];
			$tsk_description = $_REQUEST['tsk_description'];
			$tsk_dom = $_REQUEST['tsk_dom'];
			$tsk_dow = $_REQUEST['tsk_dow'];
			$tsk_hour = $_REQUEST['tsk_hour'];
			$tsk_minute = $_REQUEST['tsk_minute'];
			$tsk_month = $_REQUEST['tsk_month'];
			$tsk_name = $_REQUEST['tsk_name'];
			$tsk_srv_id = $_REQUEST['tsk_srv_id'];
			$tsk_srv_id = is_numeric($tsk_srv_id) ? $tsk_srv_id : -1; 

			$asteria = '*';	// use * as default value in crontab is not specified 

			$tsk_minute = is_numeric($tsk_minute) ? $tsk_minute : $asteria; 
			$tsk_hour = is_numeric($tsk_hour) ? $tsk_hour : $asteria; 
			$tsk_dom = is_numeric($tsk_dom) ? $tsk_dom : $asteria; 
			$tsk_month = is_numeric($tsk_month) ? $tsk_month : $asteria; 
			$tsk_dow = is_numeric($tsk_dow) ? $tsk_dow : $asteria; 

			// if the same bot, same job already has this task id
			$condition_same_task_id = db_and()
					->condition('tsk_bts_id', $bts_id)
				    ->condition('tsk_jbs_id', $jbs_id)
				    ->condition('tsk_id', $tsk_id);
			
			// if the same bot, same job already has a same schedule
			$condition_same_schedule = db_and()
		            ->condition('tsk_bts_id', $bts_id)
		            ->condition('tsk_jbs_id', $jbs_id)
		            ->condition('tsk_minute', $tsk_minute)
		            ->condition('tsk_hour', is_numeric($tsk_hour) ? $tsk_hour : NULL)
		            ->condition('tsk_dom', is_numeric($tsk_dom) ? $tsk_dom : NULL)
		            ->condition('tsk_month', is_numeric($tsk_month) ? $tsk_month : NULL)
		            ->condition('tsk_dow', is_numeric($tsk_dow) ? $tsk_dow : NULL);

            $or = db_or()
		            ->condition($condition_same_task_id)
		            ->condition($condition_same_schedule);

		    // if task id changed, check both schedule and task id
            // if task id not changed, check the new schedule only
            $condition = ($tsk_id != $id) ? $or : $condition_same_schedule;

			$exists = db_select('tasks_tsk', 'tsk')
		            ->fields('tsk')
		            ->condition($condition)
		            ->execute()->rowCount();
        	
        	if ($exists > 0){
        		// already exists a task with the same id, ignore
				die(0);
        	}	

        	$fields = array(
					  'tsk_id' => $tsk_id,
					  'tsk_name' => $tsk_name,
					  'tsk_description' => $tsk_description,
					  'tsk_last_updated' => date('Y-m-d H:i:s'),
					  'tsk_minute' => $tsk_minute,
					  'tsk_srv_id' => $tsk_srv_id,
					);

			if (is_numeric($tsk_hour)){
    			$fields += array('tsk_hour' => $tsk_hour);
    		}
    		if (is_numeric($tsk_dom)){
    			$fields += array('tsk_dom' => $tsk_dom);
    		}
    		if (is_numeric($tsk_dow)){
    			$fields += array('tsk_dow' => $tsk_dow);
    		}
    		if (is_numeric($tsk_month)){
    			$fields += array('tsk_month' => $tsk_month);
    		}

    		$cmd = get_cmd(1, $bts_id, $jbs_id, $id);

			$task_old = get_cron_str($bts_id, $jbs_id, $id, $cmd); 

			db_update('tasks_tsk')
	            ->condition('tsk_bts_id', $bts_id)
	            ->condition('tsk_jbs_id', $jbs_id)
	            ->condition('tsk_id', $id) // note this is the origin job id
				->fields($fields)
				->execute();

			// update cron
			$task_new = get_cron_str($bts_id, $jbs_id, $id, $cmd);

			$output = shell_exec('crontab -l');
			// use new schedule to replace the old one
			$output = str_replace($task_old, $task_new, $output); 

			echo $task_old . ' ' .  $task_new;

			file_put_contents('/tmp/crontab.txt', $output); 
			exec('crontab /tmp/crontab.txt');

			break;

		case 'del':
			$tid = $_REQUEST['id'];
			$bts_id = $_REQUEST['bid'];
			$jbs_id = $_REQUEST['jid'];

			db_delete('events_vnt')
			  ->condition('vnt_bts_id', $bts_id)
			  ->condition('vnt_jbs_id', $jbs_id)
			  ->condition('vnt_tsk_id', $tid)
			  ->execute();

		  	db_delete('runs_rns')
			  ->condition('rns_bts_id', $bts_id)
			  ->condition('rns_jbs_id', $jbs_id)
			  ->condition('rns_tsk_id', $tid)
			  ->execute();

			// before item deleted, collect cron schedule
    		$cmd = get_cmd(1, $bts_id, $jbs_id, $tid);
			$task_old = get_cron_str($bts_id, $jbs_id, $tid, $cmd); 

			db_delete('tasks_tsk')
			  ->condition('tsk_bts_id', $bts_id)
			  ->condition('tsk_jbs_id', $jbs_id)
			  ->condition('tsk_id', $tid)
			  ->execute();

			$output = shell_exec('crontab -l');
			// use new schedule to replace the old one
			$output = str_replace($task_old . PHP_EOL, '', $output); 

			file_put_contents('/tmp/crontab.txt', $output);
			exec('crontab /tmp/crontab.txt');

			break;
		case 'enable':

			$tid = $_REQUEST['id'];
			$bid = $_REQUEST['bid'];
			$jid = $_REQUEST['jid'];

			$enabled = db_select('tasks_tsk', 'tsk')
			            ->fields('tsk', array('tsk_enabled'))
			            ->condition('tsk_id', $tid)
			            ->condition('tsk_jbs_id', $jid)
			            ->condition('tsk_bts_id', $bid)
			            ->execute()
			            ->fetchField();

        	$fields = array('tsk_enabled' => abs($enabled - 1), );
        	if ($enabled == 1){
//				$fields += array('jbs_disabled' => date('Y-m-d H:i:s'),);        		
        	}

			db_update('tasks_tsk')
	            ->condition('tsk_id', $tid)
	            ->condition('tsk_jbs_id', $jid)
	            ->condition('tsk_bts_id', $bid)
				->fields($fields)
				->execute();
			break;

		case 'clearevent':
			$tid = $_REQUEST['id'];
			$bid = $_REQUEST['bid'];
			$jid = $_REQUEST['jid'];

			// Remove all events related to the bot
			clear_events_by_tid($bid, $jid, $tid);
		 	echo 'events of the task cleared';
			break;

		case 'clearrun':
			$tid = $_REQUEST['id'];
			$bid = $_REQUEST['bid'];
			$jid = $_REQUEST['jid'];

			clear_runs_by_tid($bid, $jid, $tid);

		 	echo 'runs of the task cleared';
			break;

		case 'start':
			// get the next min jobid for the bot
			$tid = $_REQUEST['id'];
			$bid = $_REQUEST['bid'];
			$jid = $_REQUEST['jid'];

			$tsk_condition = db_and()
					->condition('tsk_bts_id', $bid)
		            ->condition('tsk_jbs_id', $jid)
		            ->condition('tsk_id', $tid);

			$exists = db_select('tasks_tsk', 'tsk')
		            ->fields('tsk')
		            ->condition($tsk_condition)
		            ->execute()->rowCount();

			$now = date('Y-m-d H:i:s');
			if ($exists > 0){
				$running = db_select('tasks_tsk', 'tsk')
			            ->fields('tsk', array('tsk_running'))
			            ->condition($tsk_condition)
			            ->condition('tsk_running', 1)
			            ->execute()
			            ->fetchField();

            	if($running == 1){
            		die("The task is already running!");
            	}
			} else {
				die("No such task!");
			}
			// the serverid should be selected in a proper method
			// we're using 1 for now 
			$cmd = get_cmd(1, $bid, $jid, $tid);
			shell_exec($cmd);
			echo 'cmd:' . $cmd;
			break;


		case 'taskid':
			// get the next min jobid for the bot
			$bid = $_REQUEST['bid'];
			$jid = $_REQUEST['jid'];
			$results = db_select('tasks_tsk', 'tsk')
		            ->fields('tsk', array('tsk_id'))
		            ->condition('tsk_bts_id', $bid)
		            ->condition('tsk_jbs_id', $jid)
		            ->orderBy('tsk_id', 'ASC')
		            ->execute();
		    $taskids = array();
		    foreach ($results as $data) {
		    	if ($data->tsk_id != -1)
					$taskids[] = $data->tsk_id;
			}

			echo getMinGapId($taskids);
		default:
			# code...
			break;
	}

	db_set_active();
}

function edit_job_data(){
	$op = $_REQUEST['oper'];
	
	db_set_active('bot_central');

	switch ($op) {
		case 'add':
			$bts_id = $_REQUEST['bts_id'];
			$jbs_description = $_REQUEST['jbs_description'];
			$jbs_id = $_REQUEST['jbs_id'];
			$jbs_name = $_REQUEST['jbs_name'];
			//$jbs_path = $_REQUEST['jbs_path'];
			$jbs_file = $_REQUEST['jbs_file'];
			$jbs_filename = $_REQUEST['jbs_filename'];
			$jbs_filename = str_replace('C:\\fakepath\\', '', $jbs_filename);

			if (empty($jbs_file)){
				$jbs_file = '';
			} else {
				$jbs_file = str_replace('C:\\fakepath\\', '', $jbs_file);
			}

			$exists = db_select('jobs_jbs', 'jbs')
		            ->fields('jbs')
		            ->condition('jbs_bts_id', $bts_id)
		            ->condition('jbs_id', $jbs_id)
		            ->execute()->rowCount();

			if ($exists == 0){
				$now = date('Y-m-d H:i:s');

				$jbs_path = db_select('bots_bts', 'bts')
		            ->fields('bts', array('bts_path'))
		            ->condition('bts_id', $bts_id)
		            ->execute()->fetchField();
	        	
	        	$fields = array(
						  'jbs_bts_id' => $bts_id,
						  'jbs_id' => $jbs_id,
						  'jbs_name' => $jbs_name,
						  'jbs_description' => $jbs_description,
						  'jbs_enabled' => 1,
						  'jbs_created' => $now,
						  'jbs_last_updated' => $now,
						  'jbs_path' => $jbs_path . 'config/',
						  'jbs_file' => $jbs_filename,
						);

	        	db_insert('jobs_jbs')
					->fields($fields)
					->execute();

				$configName = str_replace(".xml", "", $jbs_filename);
				$xsd = $jbs_path . 'config/' . $configName . '.xsd';

				if (!file_exists($xsd)) {
					$xsdSample = getXSD($jbs_path . 'config/');

					if (!empty($xsdSample)) {
						copy($xsdSample, $xsd);	
					} else {
						// error no xsd found 
					}
				} 

				$base = variable_get('bots_data_path', '/var/www/data/');
				// crate the data directory
				$dataPath = $base . "$bts_id/$jbs_id/";
				if (!file_exists($dataPath)){
					$old_umask = umask(0); 
					mkdir($dataPath, 0755, true);
					umask($old_umask);
				}

				echo '{"id":' . $bts_id. ', "path":"'. $jbs_path . 'config/"}';
			} else {
				echo '{"id":-1, "path":""}'; // means no bot was added	
			}

			break;
		case 'edit':
			$id = $_REQUEST['id']; // $id is the origin jobid
			$bts_id = $_REQUEST['bid'];
			$jbs_id = $_REQUEST['jbs_id']; // this id could be changed
			$jbs_description = $_REQUEST['jbs_description'];
			$jbs_name = $_REQUEST['jbs_name'];
			$jbs_file = $_REQUEST['jbs_file'];
			$jbs_filename = $_REQUEST['jbs_filename'];
			$jbs_filename = str_replace('C:\\fakepath\\', '', $jbs_filename);

			if (empty($jbs_file)){
				$jbs_file = '';
			} else {
				$jbs_file = str_replace('C:\\fakepath\\', '', $jbs_file);
			}

			$jbs_path = db_select('bots_bts', 'bts')
	            ->fields('bts', array('bts_path'))
	            ->condition('bts_id', $bts_id)
	            ->execute()->fetchField();

			$fields = array(
						//'jbs_id' => $jbs_id, // we don't edit a job's id
						'jbs_name' => $jbs_name,
						'jbs_description' => $jbs_description,
						'jbs_last_updated' => date('Y-m-d H:i:s'),
						'jbs_path' => $jbs_path . 'config/',);
			if (!empty($jbs_filename)){
				$fields += array('jbs_file' => $jbs_filename);
			}

			if ($jbs_id != $id){
				$exists = db_select('jobs_jbs', 'jbs')
			            ->fields('jbs')
						->condition('jbs_bts_id', $bts_id)
						->condition('jbs_id', $jbs_id)
			            ->execute()->rowCount();
	        	
	        	if ($exists > 0){
					die(0);
	        	}	
	        }

			db_update('jobs_jbs')
			  ->condition('jbs_bts_id', $bts_id)
			  ->condition('jbs_id', $id) // note this is the origin job id
			  ->fields($fields)
			  ->execute();

		  	$configName = str_replace(".xml", "", $jbs_filename);
			$xsd = $jbs_path . 'config/' . $configName . '.xsd';

			if (!file_exists($xsd)){
				$xsdSample = getXSD($jbs_path . 'config/');

				if (!empty($xsdSample)){
					copy($xsdSample, $xsd);	
				} else {
					// error 
				}
			}

			echo '{"id":' . $bts_id. ', "path":"'. $jbs_path . 'config/"}';

			break;
		case 'enable':
			$jid = $_REQUEST['id'];
			$bid = $_REQUEST['bid'];

			$enabled = db_select('jobs_jbs', 'jbs')
			            ->fields('jbs', array('jbs_enabled'))
			            ->condition('jbs_id', $jid)
			            ->condition('jbs_bts_id', $bid)
			            ->execute()
			            ->fetchField();

        	$fields = array('jbs_enabled' => abs($enabled - 1), );
        	if ($enabled == 1){
//				$fields += array('jbs_disabled' => date('Y-m-d H:i:s'),);        		
        	}

			db_update('jobs_jbs')
				->condition('jbs_id', $jid)
				->condition('jbs_bts_id', $bid)
				->fields($fields)
				->execute();
			break;

		case 'clearevent':
			$jid = $_REQUEST['id'];
			$bid = $_REQUEST['bid'];
			// Remove all events related to the bot
			clear_events_by_jid($bid, $jid);
		 	echo 'events of the job cleared';
			break;

		case 'clearrun':
			$jid = $_REQUEST['id'];
			$bid = $_REQUEST['bid'];

			clear_runs_by_jid($bid, $jid);

		 	echo 'runs of the job cleared';
			break;

		case 'cleartask':
			$jid = $_REQUEST['id'];
			$bid = $_REQUEST['bid'];

			clear_tasks_by_jid($bid, $jid);

	 		echo 'tasks of the job cleared';
			break;

		case 'del':
			$jid = $_REQUEST['id'];
			$bid = $_REQUEST['bid'];

			db_delete('events_vnt')
			  ->condition('vnt_bts_id', $bid)
			  ->condition('vnt_jbs_id', $jid)
			  ->execute();

		  	db_delete('runs_rns')
			  ->condition('rns_bts_id', $bid)
			  ->condition('rns_jbs_id', $jid)
			  ->execute();

		  	db_delete('tasks_tsk')
			  ->condition('tsk_bts_id', $bid)
			  ->condition('tsk_jbs_id', $jid)
			  ->execute();

			// delete the config file
			$result = db_select('jobs_jbs', 'jbs')
		        ->fields('jbs', array('jbs_path', 'jbs_file'))
				->condition('jbs_bts_id', $bid)
				->condition('jbs_id', $jid)
		        ->execute()
		        ->fetchAssoc();

			//$path = str_replace(' ', '\\ ', $result['jbs_path']);
	        $path = $result['jbs_path'];
			$file = $result['jbs_file'];

			db_delete('jobs_jbs')
			  ->condition('jbs_bts_id', $bid)
			  ->condition('jbs_id', $jid)
			  ->execute();

			unlink($path . $file);

			// remove the data directory
			$dataPath = "/var/www/data/$bid/$jid/";
			if (!file_exists($dataPath)){
				shell_exec("rm -r $dataPath");
			}

			break;

		case 'jobid':
			// get the next min jobid for the bot
			$bid = $_REQUEST['bid'];
			$results = db_select('jobs_jbs', 'jbs')
		            ->fields('jbs', array('jbs_id'))
		            ->condition('jbs_bts_id', $bid)
		            ->orderBy('jbs_id', 'ASC')
		            ->execute();
		    $jobids = array();
		    foreach ($results as $data) {
				$jobids[] = $data->jbs_id;
			}
			
			echo getMinGapId($jobids);
			
			break;
		case 'start':
			// get the next min jobid for the bot
			$jid = $_REQUEST['id'];
			$bid = $_REQUEST['bid'];

			$exists = db_select('tasks_tsk', 'tsk')
		            ->fields('tsk')
		            ->condition('tsk_bts_id', $bid)
		            ->condition('tsk_jbs_id', $jid)
		            ->condition('tsk_id', -1)
		            ->execute()->rowCount();

			$now = date('Y-m-d H:i:s');
			if ($exists == 0){
	        	$fields = array(
						  'tsk_bts_id' => $bid,
						  'tsk_jbs_id' => $jid,
						  'tsk_id' => -1,
						  'tsk_name' => 'Manual task',
						  'tsk_description' => 'Manually started',
						  'tsk_enabled' => 1,
						  'tsk_created' => $now,
						  'tsk_last_updated' => $now,
						  //'tsk_minute' => $tsk_minute,
						  //'tsk_srv_id' => $tsk_srv_id,
						);

				db_insert('tasks_tsk')
						->fields($fields)
						->execute();
			} else {
	        	$fields = array( 'tsk_last_updated' => $now, );

				db_update('tasks_tsk')
						->fields($fields)
						->condition('tsk_bts_id', $bid)
						->condition('tsk_jbs_id', $jid)
						->condition('tsk_id', -1)
						->execute();

				//echo "start an existing manual task for $bid - $jid";
			}

			// the serverid should be selected in a proper method
			// we're using 1 for now 
			$cmd = get_cmd(1, $bid, $jid, -1);
			shell_exec($cmd);
			echo $cmd;
			
			break;
		case 'export':
			// get the next min jobid for the bot
			$jid = $_REQUEST['jid'];
			$bid = $_REQUEST['bid'];

			//where to unzip/unrar the uploaded scripts
			$path = db_select('jobs_jbs', 'jbs')
		            ->fields('jbs', array('jbs_path'))
		            ->condition('jbs_bts_id', $bid)
		            ->condition('jbs_id', $jid)
		            ->execute()
		            ->fetchField();
		    
		    $script = $path . 'export.sh';
		    if (file_exists($script)){
			    $cmd = 'sh ' . $script;
	        	shell_exec($cmd);
	        	echo $cmd;
		    } else {
				echo $script . ' Not Found!';
		    }
		    //echo $cmd;
    
			break;

		case 'file': 
			$id = $_REQUEST['id'];
			$path = $_REQUEST['path'];

			$filename = $_FILES['jbs_file']['name'];
			$tempFile = $_FILES['jbs_file']['tmp_name'];
			$targetFile = rtrim($path,'/') . '/' . $filename;

			if (!file_exists($path)) {
				// need to validate the path with extra security methods
				$old_umask = umask(0); 
				mkdir($path, 0777, true); 
				umask($old_umask);
			}

			move_uploaded_file($tempFile, $targetFile);
			uncompress($targetFile, $path, true);

			echo 'deployed';

			break;

		case 'check':
			$jid = $_REQUEST['jid'];
			$bid = $_REQUEST['bid'];

			//$path = dirname(__FILE__);
			//$path = substr($path, 0, strripos($path, '/') + 1) . 'data/';
			$path = "/var/www/data/$bid/$jid/";

			$files = scandir($path);

			foreach ($files as $key => $value) {
				if($value == '.' || $value == '..'){
					unset($files[$key]);
				}
			}
			arsort($files, SORT_STRING);
			
			drupal_json_output(
				array(
					'files' => $files,
					'path' => drupal_get_path('module', 'bots') . '/data/'));

			break;
	} 
	db_set_active();
}

function getXSD($path){
	$files = array();
	$target = glob($path . "*.xsd");

	foreach ($target as $filename) {
    	$files[] = $filename;
	}
	return count($files) > 0 ? $files[0] : NULL;
}

function get_cmd($sid, $bid, $jid, $tid, $resume = FALSE){

	$tid = ($tid == -1 ? '': $tid);

	// turn into
	if($resume) {
		$resume = 'TRUE';
	} else {
		$resume = '';
	}

	$bot = db_select('bots_bts', 'bts')
        ->fields('bts', array('bts_path', 'bts_file'))
        ->condition('bts_id', $bid)
        ->execute()
        ->fetchAssoc();

    $config = db_select('jobs_jbs', 'jbs')
        ->fields('jbs', array('jbs_file'))
        ->condition('jbs_bts_id', $bid)
        ->condition('jbs_id', $jid)
        ->execute()
        ->fetchField();

	$path = $bot['bts_path'];
	$file = $bot['bts_file'];
	
	//$sid = 1; // choose a free server randomly here
	return 'nohup java -cp ' . $path . 'lib/ -jar ' . $path . $file . " $sid $bid $jid $tid $resume config/" .  str_replace('.xml', '', $config) . '&> /dev/null';
}

function edit_run_data(){

	$op = $_REQUEST['oper'];
	
	db_set_active('bot_central');

	if ($op == 'del'){
		$rid = $_REQUEST['id'];
		$bid = $_REQUEST['bid'];
		$jid = $_REQUEST['jid'];
		$tid = $_REQUEST['tid'];

		if (is_numeric($rid) && is_numeric($bid) 
				&& is_numeric($jid) && is_numeric($tid)){ 
			$and = db_and()->condition('rns_bts_id', $bid) 
						->condition('rns_jbs_id', $jid)
						->condition('rns_tsk_id', $tid)
						->condition('rns_id', $rid);	
			db_delete('runs_rns')->condition($and)->execute();   
		}
	} 
	db_set_active();
}

function edit_server_data(){
	$op = $_REQUEST['oper'];

	$ip = $_REQUEST['srv_ip'];
	$name = $_REQUEST['srv_name'];
	
	db_set_active('bot_central');

	switch ($op) {
		case 'add':
			$exists = db_select('servers_srv', 'srv')
		            ->fields('srv')
		            ->condition('srv_ip', $ip)
		            ->execute()->rowCount();

			if ($exists == 0){
				$id = $_REQUEST['srv_id'];

	        	$fields = array(
						  'srv_name' => $name,
						  'srv_ip' => $ip,
						  'srv_enabled' => '1',
						  'srv_cpu' => '0.00',
						  'srv_memory' => '0.00',
						  'srv_disk' => '0.0000',
						  'srv_receiving' => '0.000',
						  'srv_sending' => '0.000',
						  'srv_created' => date('Y-m-d H:i:s'),
						);
	        	if (isset($id) && $id > 0){
					$fields += array('srv_id' => $id,);
	        	}
				
				db_insert('servers_srv')
					->fields($fields)
					->execute();
			}
			break;
		case 'edit':
			$id = $_REQUEST['id'];
			$sid = $_REQUEST['srv_id'];

			$exists = db_select('servers_srv', 'srv')
		            ->fields('srv')
		            ->condition('srv_ip', $ip)
		            ->execute()->rowCount();

			if ($exists <= 1){
				db_update('servers_srv')
					->condition('srv_id', $id)
					->fields(array(
						'srv_name' => $name,
						'srv_ip' => $ip,
						'srv_id' => $sid,
						))
					->execute();
			}
			break;
		case 'enable':
			$id = $_REQUEST['id'];

			$enabled = db_select('servers_srv', 'srv')
			            ->fields('srv', array('srv_enabled'))
			            ->condition('srv_id', $id)
			            ->execute()
			            ->fetchField();

        	$fields = array('srv_enabled' => abs($enabled - 1), );
        	if ($enabled == 1){
				$fields += array('srv_disabled' => date('Y-m-d H:i:s'),);        		
        	}

			db_update('servers_srv')
				->condition('srv_id', $id)
				->fields($fields)
				->execute();
			break;
		case 'status':
			# code...
			$id = $_REQUEST['id'];
			break;

		case 'list':
			$results = db_select('servers_srv', 'srv')
			            ->fields('srv')
			            ->condition('srv_enabled', 1)
			            ->orderBy('srv_id', 'ASC')
			            ->execute();
		    $servers = array();
		    foreach ($results as $data) {
				$servers[] = array(
					'id' => $data->srv_id, 
					'server' => $data->srv_name . ' - ' . $data->srv_ip);
			}

			drupal_json_output($servers);
			break;
		case 'serverid':
			// get the next min jobid for the bot
			$results = db_select('servers_srv', 'srv')
		            ->fields('srv', array('srv_id'))
		            ->orderBy('srv_id', 'ASC')
		            ->execute();
		    $serverids = array();
		    foreach ($results as $data) {
				$serverids[] = $data->srv_id;
			}
			
			echo getMinGapId($serverids);
			
			break;
		
		default:
			# code...
			$id = $_REQUEST['id'];
			break;
		}

	db_set_active();
}

function edit_bot_data(){

	$op = $_REQUEST['oper'];

	db_set_active('bot_central');

	switch ($op) {
		case 'add':
			$bts_name = $_REQUEST['bts_name'];
			$bts_id = intval($_REQUEST['bts_id']);
			$bts_company = $_REQUEST['bts_company'];
			$bts_description = $_REQUEST['bts_description'];
			$bts_sector1 = $_REQUEST['bts_sector1'];
			$bts_sector2 = $_REQUEST['bts_sector2'];
			$bts_sector3 = $_REQUEST['bts_sector3'];
			$bts_version = $_REQUEST['bts_version'];
			$bts_path = $_REQUEST['bts_path'];
			$bts_file = $_REQUEST['bts_file'];
			$bts_filename = $_REQUEST['bts_filename'];
			if (empty($bts_file)){
				$bts_file = '';
			} else {
				$bts_file = str_replace('C:\\fakepath\\', '', $bts_file);
			}

			$or = db_or()
				->condition('bts_id', $bts_id)
				->condition('bts_name', strtolower($bts_name));

			$exists = db_select('bots_bts', 'bts')
		            ->fields('bts')
		            ->condition($or)
		            ->execute()->rowCount();

			if ($exists == 0){
				$time = date('Y-m-d H:i:s');
				$outId = db_insert('bots_bts')
					->fields(array(
					  'bts_id' => $bts_id,
					  'bts_name' => $bts_name,
					  'bts_company' => $bts_company,
					  'bts_sector1' => $bts_sector1,
					  'bts_sector2' => $bts_sector2,
					  'bts_sector3' => $bts_sector3,
					  'bts_version' => $bts_version,
					  'bts_description' => $bts_description,
					  'bts_created' => $time,
					  'bts_last_updated' => $time,
					  'bts_path' => $bts_path,
					  'bts_file' => $bts_filename,
					))
					->execute();

				$dataPath = '/var/www/data/' . $bts_id;
				if (!file_exists($dataPath)){
					$old_umask = umask(0); 
					mkdir($dataPath, 0755, true);
					umask($old_umask);
				}

				echo '{"id":' . $outId. ', "path":"'. $bts_path .'"}';
			} else {
				echo '{"id":-1, "path":""}'; // means no bot was added	
			}
			
			break;

		case 'edit':
			$id = $_REQUEST['id'];

			$bts_name = $_REQUEST['bts_name'];
			$bts_id = intval($_REQUEST['bts_id']);
			$bts_company = $_REQUEST['bts_company'];
			$bts_description = $_REQUEST['bts_description'];
			$bts_sector1 = $_REQUEST['bts_sector1'];
			$bts_sector2 = $_REQUEST['bts_sector2'];
			$bts_sector3 = $_REQUEST['bts_sector3'];
			$bts_version = $_REQUEST['bts_version'];
			$bts_path = $_REQUEST['bts_path'];
			$bts_file = $_REQUEST['bts_file'];
			$bts_filename = $_REQUEST['bts_filename'];
			if (empty($bts_file)){
				$bts_file = '';
			} else {
				$bts_file = str_replace('C:\\fakepath\\', '', $bts_file);
			}

			$fields = array(
			    //'bts_id' => $bts_id, // we don't edit a bot's id 
			    'bts_name' => $bts_name,
			    'bts_company' => $bts_company,
			    'bts_sector1' => $bts_sector1,
			    'bts_sector2' => $bts_sector2,
			    'bts_sector3' => $bts_sector3,
			    'bts_version' => $bts_version,
			    'bts_description' => $bts_description,
			    'bts_path' => $bts_path,
			    'bts_last_updated' => date('Y-m-d H:i:s'),
			  );

			if (!empty($bts_file)){
				$fields += array('bts_file' => $bts_filename,);
			}

			// before update, check if the path has changed, 
			// if path changed, move the bot scripts to new path
			$old_bts_path = db_select('bots_bts', 'bts')
		            ->fields('bts', array('bts_path'))
		            ->condition('bts_id', $id)
		            ->execute()
		            ->fetchField();

		    if ($old_bts_path != $bts_path){
				if (!file_exists($bts_path)) {
					// need to validate the path with extra security methods
					$old_umask = umask(0); 
					mkdir($bts_path, 0777, true); 
					umask($old_umask);
				}

		    	//echo "cp $old_bts_path* $bts_path";
	    		// move the scripts of the bot from the old path to the new one 
	    		shell_exec("cp -r $old_bts_path* $bts_path");
	    		shell_exec("rm -r $old_bts_path");
		    } 

			db_update('bots_bts')
			  ->condition('bts_id', $id)
			  ->fields($fields)
			  ->execute();

			echo '{"id":' . $id. ', "path":"'. $bts_path .'"}';
			
			break;

		case 'file': 
			$id = $_REQUEST['id'];
			$path = $_REQUEST['path'];

			$filename = $_FILES['bts_file']['name'];
			$tempFile = $_FILES['bts_file']['tmp_name'];
			$targetFile = rtrim($path,'/') . '/' . $filename;

			if (!file_exists($path)) {
				// need to validate the path with extra security methods
				$old_umask = umask(0); 
				mkdir($path, 0777, true); 
				umask($old_umask);
			}
			move_uploaded_file($tempFile, $targetFile);
			uncompress($targetFile, $path, true);

			echo 'bot deployed';

			break;
		case 'enable':
			$id = $_REQUEST['id'];

			$enabled = db_select('bots_bts', 'bts')
			            ->fields('bts', array('bts_enabled'))
			            ->condition('bts_id', $id)
			            ->execute()
			            ->fetchField();

			db_update('bots_bts')
			  ->condition('bts_id', $id)
			  ->fields(array(
			    'bts_enabled' => abs($enabled - 1),
			  ))
			  ->execute();
			break;

		case 'del':
			$id = $_REQUEST['id'];
			// Remove all jobs of the bot
			clear_jobs_by_bid($id);	

			// remove the bot realated files on disk
			$result = db_select('bots_bts', 'bts')
		        ->fields('bts', array('bts_path', 'bts_file'))
				->condition('bts_id', $id)
		        ->execute()
		        ->fetchAssoc();

			$path = $result['bts_path'];

			// this is extreamly dangerous if the $path has been set to other directories
			echo shell_exec("rm -r '$path'");

			// remove all the exported data for the bot 
			$dataPath = '/var/www/data/' . $id;
			if (file_exists($dataPath)){
				shell_exec("rm -r $dataPath");	
			}

			// Remove the bot
			db_delete('bots_bts')
				->condition('bts_id', $id)
				->execute();
			break;

		case 'clearevent': 
			$id = $_REQUEST['id'];
			// Remove all events related to the bot
			clear_events( db_and()->condition('vnt_bts_id', $id) );

		 	echo 'events of the bot cleared';
			break;

		case 'clearallevent':
			db_truncate('events_vnt')->execute();
			echo 'all events cleared';
			break;

		case 'clearrun':
			
			$id = $_REQUEST['id'];
			clear_runs_by_bid($id);

		 	echo 'runs of the bot cleared';
			break;

		case 'clearallrun':
			// Remove all runs & events
		 	db_truncate('events_vnt')->execute();
		 	db_truncate('runs_rns')->execute();

		 	echo 'all runs cleared';
			break;

		case 'cleartask':
			$id = $_REQUEST['id'];
			clear_tasks_by_bid($id);

	 		echo 'tasks of the bot cleared';
			break;

		case 'clearjob':
			$id = $_REQUEST['id'];
			clear_jobs_by_bid($id);

			echo 'jobs of the bot cleared';
			break;
		case 'botid':
			// get the next min botid
			$results = db_select('bots_bts', 'bts')
		            ->fields('bts', array('bts_id'))
		            ->orderBy('bts_id', 'ASC')
		            ->execute();
		    $botids = array();
		    foreach ($results as $data) {
				$botids[] = $data->bts_id;
			}
			
			echo getMinGapId($botids);
			break;
		case 'upload':

			$botid = $_REQUEST['botid'];
			$jobid = $_REQUEST['jobid'];
			//where to unzip/unrar the uploaded scripts
			$targetPath = db_select('jobs_jbs', 'jbs')
			            ->fields('jbs', array('jbs_path'))
			            ->condition('jbs_bts_id', $botid)
			            ->condition('jbs_id', $jobid)
			            ->execute()
			            ->fetchField();

			if(!file_exists($targetPath)){
				$old_umask = umask(0); 
				mkdir($targetPath, 0777, true); 
				umask($old_umask); 
			}

			$tempFile = $_FILES['Filedata']['tmp_name'];
			$filename = iconv('utf-8','gb2312',$_FILES['Filedata']['name']);
			$targetFile = rtrim($targetPath,'/') . '/' . $filename;

			$fileTypes = array('rar','zip'); // File extensions
			$fileParts = pathinfo($_FILES['Filedata']['name']);

			if (in_array(strtolower($fileParts['extension']), $fileTypes)) {
				move_uploaded_file($tempFile, $targetFile);
				// make the file writable 
				chmod($targetFile, 0777);

				uncompress($targetFile, $targetPath, true);
			}
			break;
	}
	db_set_active();
}

function uncompress($targetFile, $targetPath, $unlink = FALSE){
	if (strpos(strrev($targetFile), 'piz.') === 0){
		$zip = new ZipArchive;
		$res = $zip->open($targetFile);
		if ($res === TRUE) {
			$zip->extractTo($targetPath);
		} else {
			die("Cannot open $targetFile");
		}
		$zip->close();
		if($unlink){
			unlink($targetFile);
		}
	} else if (strpos(strrev($targetFile), 'rar.') === 0){
		$rar = RarArchive::open($targetFile);
		if ($rar === FALSE) {
			die("Cannot open $targetFile");
		}
		$entries = $rar->getEntries();
		if ($entries === FALSE){
			die("Cannot retrieve entries");
		}
		foreach ($entries as $entry) {
		    $entry->extract($targetPath);
		}
		$rar->close();
		if($unlink){
			unlink($targetFile);	
		}
	}

}

function clear_events($condition){
	db_delete('events_vnt')
		->condition($condition)
		->execute();
}

function clear_events_by_bid($id){
	db_delete('events_vnt')
		->condition('vnt_bts_id', $id)
		->execute();
}

function clear_events_by_jid($bid, $jid){
	db_delete('events_vnt')
		->condition('vnt_bts_id', $bid)
		->condition('vnt_jbs_id', $jid)
		->execute();
}

function clear_runs_by_bid($id){
	clear_events_by_bid($id);
	//clear_events($condition);

	db_delete('runs_rns')
		->condition('rns_bts_id', $id)
		->execute();
}

function clear_runs_by_jid($bid, $jid){
	clear_events_by_jid($bid, $jid);

	db_delete('runs_rns')
		->condition('rns_bts_id', $bid)
		->condition('rns_jbs_id', $jid)
		->execute();
}

function clear_tasks_by_bid($id){
	clear_runs_by_bid($id);

	db_delete('tasks_tsk')
		->condition('tsk_bts_id', $id)
		->execute();
}

function clear_tasks_by_jid($bid, $jid){
	clear_events_by_jid($bid, $jid);
	clear_runs_by_jid($bid, $jid);

	db_delete('tasks_tsk')
		->condition('tsk_bts_id', $bid)
		->condition('tsk_jbs_id', $jid)
		->execute();
}

function clear_jobs_by_bid($id){
	clear_tasks_by_bid($id);

	db_delete('jobs_jbs')
		->condition('jbs_bts_id', $id)
		->execute();
}

function get_data_edit(){

	switch ($_REQUEST['type']) {
		case 'server':
			edit_server_data();
			break;
		case 'bot':
			edit_bot_data();
			break;
		case 'job':
			edit_job_data();
			break;
		case 'task':
			edit_task_data();
			break;
		case 'run':
			edit_run_data();
			break;
		case 'company':
			edit_company_data();
			break;
		default:
			//die(0);
			break;
	}
	// return the formated data
	//echo json_encode($responce);
}