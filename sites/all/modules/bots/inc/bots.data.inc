<?php
function get_data()
{
	// get the requested type
	$type = $_REQUEST['type'];
	// get the requested page
	$page = $_REQUEST['page'];
	// get how many rows we want to have into the grid
	// rowNum parameter in the grid
	$limit = $_REQUEST['rows'];
	// get index row - i.e. user click to sort
	// at first time sortname parameter - after that the index from colModel
	$sidx = $_REQUEST['sidx'];
	// sorting order - at first time sortorder
	$sord = $_REQUEST['sord']; 

	$bid = $_REQUEST['bid'];
	$jid = $_REQUEST['jid'];
	$tid = $_REQUEST['tid'];
	$rid = $_REQUEST['rid'];
	$sid = $_REQUEST['sid'];

	// if we not pass at first time index use the first column for the index
	if(!$sidx) $sidx =1;
	// connect to the MySQL database server
	db_set_active('bot_central');

	$sql = '';
	switch ($type) {
		case 'server':
			$sql = "SELECT COUNT(*) AS count FROM servers_srv";	
			break;
		case 'bot':
			$sql = "SELECT COUNT(*) AS count FROM bots_bts";
			break;
		case 'job':
			if (empty($bid)){
				$query = "SELECT bts_id FROM {bots_bts} ORDER BY bts_id LIMIT 0, 1";
				$bid = db_query($query)->fetchField();
				$bid = empty($bid) ? -1 : $bid;
			}
			$sql = "SELECT COUNT(*) AS count FROM jobs_jbs WHERE jbs_bts_id = $bid";
			break;
		case 'task':
			if (empty($bid)){
				$query = "SELECT bts_id FROM bots_bts ORDER BY bts_id LIMIT 0, 1";
				$bid = db_query($query)->fetchField();
				$bid = empty($bid) ? -1 : $bid;
			}
			if (empty($jid)){
				$query = "SELECT jbs_id FROM jobs_jbs WHERE jbs_bts_id = $bid ORDER BY jbs_id LIMIT 0, 1";
				$jid = db_query($query)->fetchField();
				$jid = empty($jid) ? -1 : $jid;
			}
			$sql = "SELECT COUNT(*) AS count FROM tasks_tsk where tsk_bts_id = $bid and tsk_jbs_id = $jid";
			break;
		case 'run':
			if (empty($bid)){
				$query = "SELECT bts_id FROM bots_bts ORDER BY bts_id LIMIT 0, 1";
				$bid = db_query($query)->fetchField();
				$bid = empty($bid) ? -1 : $bid;
			}
			if (empty($jid)){
				$query = "SELECT jbs_id FROM jobs_jbs WHERE jbs_bts_id = $bid ORDER BY jbs_id LIMIT 0, 1";
				$jid = db_query($query)->fetchField();
				$jid = empty($jid) ? -1 : $jid;
			}
			if (empty($tid)){
				$query = "SELECT tsk_id FROM tasks_tsk WHERE tsk_bts_id = $bid AND tsk_jbs_id = $jid ORDER BY tsk_id LIMIT 0, 1";
				$tid = db_query($query)->fetchField();
				$tid = empty($tid) ? -1 : $tid;
			}
			$sql = "SELECT COUNT(*) AS count FROM runs_rns where rns_bts_id = $bid and rns_jbs_id = $jid and rns_tsk_id = $tid";
			break;
		case 'event':
			if (empty($bid)){
				$query = "SELECT bts_id FROM bots_bts ORDER BY bts_id LIMIT 0, 1";
				$bid = db_query($query)->fetchField();
				$bid = empty($bid) ? -1 : $bid;
			}
			if (empty($jid)){
				$query = "SELECT jbs_id FROM jobs_jbs WHERE jbs_bts_id = $bid ORDER BY jbs_id LIMIT 0, 1";
				$jid = db_query($query)->fetchField();
				$jid = empty($jid) ? -1 : $jid;
			}
			if (empty($tid)){
				$query = "SELECT tsk_id FROM tasks_tsk WHERE tsk_bts_id = $bid AND tsk_jbs_id = $jid ORDER BY tsk_id LIMIT 0, 1";
				$tid = db_query($query)->fetchField();
				$tid = empty($tid) ? -1 : $tid;
			}
			if (empty($rid)){
				$query = "SELECT rns_id FROM runs_rns WHERE rns_bts_id = $bid AND rns_jbs_id = $jid AND rns_tsk_id = $tid ORDER BY rns_id DESC LIMIT 0, 1";
				$rid = db_query($query)->fetchField();
				$rid = empty($rid) ? -1 : $rid;
			}
			$sql = "SELECT COUNT(*) AS count FROM events_vnt where vnt_bts_id = $bid and vnt_jbs_id = $jid and vnt_tsk_id = $tid and vnt_rns_id = $rid";
			break;
		default:
			# code...
			break;
	}
	// calculate the number of rows for the query. We need this to paging the result
	//$result = db_query($sql);
	$count = db_query($sql)->fetchField();

	// calculation of total pages for the query
	if($count > 0) {
		$total_pages = ceil($count/$limit);
	} else {
		$total_pages = 0;
	}

	// if for some reasons the requested page is greater than the total
	// set the requested page to total page
	if ($page > $total_pages) $page = $total_pages;
	// calculate the starting position of the rows
	$start = $limit*$page - $limit; // do not put $limit*($page - 1)
	// if for some reasons start position is negative set it to 0
	// typical case is that the user type 0 for the requested page
	if($start < 0) $start = 0;

	$sql = '';

	switch ($type) {
		case 'server':
			$sql = "SELECT `srv_id`, `srv_name`,`srv_ip`, `srv_enabled`,`srv_cpu`,`srv_memory`, `srv_disk`,`srv_receiving`,`srv_sending`,`srv_created`,`srv_disabled`,`srv_description` FROM `servers_srv` ";
			break;
		case 'bot':
			$sql = "SELECT bts_id, bts_name, bts_company, cmp_name, bts_sector1, bts_sector2, bts_sector3, bts_version, bts_description, bts_enabled, bts_created, bts_last_updated, bts_path, bts_file FROM bots_bts 
				LEFT OUTER JOIN companies_cmp ON cmp_id = bts_company";
			break;
		case 'job':
			$sql  = "SELECT `jbs_id`, `jbs_name`, `jbs_description`, `jbs_enabled`, `jbs_created`, `jbs_last_updated`, `jbs_path`, `jbs_file`, '' as `jbs_dbname`, IFNULL(`count`,0) as count
				FROM jobs_jbs
				LEFT OUTER JOIN 
				(
					SELECT tsk_bts_id, tsk_jbs_id, count(*) as count 
					FROM `tasks_tsk`
					WHERE tsk_running =1
					GROUP BY tsk_bts_id, tsk_jbs_id
				)
				AS M ON jbs_bts_id = tsk_bts_id AND jbs_id = tsk_jbs_id
				where jbs_bts_id = $bid ";
			break;
		case 'task':
		/*
			$sql = "SELECT `tsk_bts_id`, `tsk_jbs_id`, `tsk_id`, `tsk_name`, `tsk_description`, `tsk_enabled`, `tsk_created`, `tsk_last_updated`, `tsk_minute`, `tsk_hour`, `tsk_dom`, `tsk_month`, `tsk_dow`, `tsk_running`, `tsk_srv_id` 
					FROM `tasks_tsk` where tsk_bts_id = $bid and tsk_jbs_id = $jid ";
					*/
			$sql = "SELECT `tsk_bts_id`, `tsk_jbs_id`, `tsk_id`, `tsk_name`, `tsk_description`, `tsk_enabled`, `tsk_created`, `tsk_last_updated`, `tsk_minute`, `tsk_hour`, `tsk_dom`, `tsk_month`, `tsk_dow`, `tsk_running`, `tsk_srv_id` , `srv_ip`, `srv_name`
					FROM `tasks_tsk` 
					LEFT OUTER JOIN `servers_srv`
					ON srv_id = tsk_srv_id 
					WHERE tsk_bts_id = $bid and tsk_jbs_id = $jid";
			break;
		case 'run':
			$sql = "SELECT `rns_id`, `rns_start`, `rns_end`, `rns_duration`, `rns_result`, `rns_srv_id` 
					FROM `runs_rns` where rns_bts_id = $bid and rns_jbs_id = $jid and rns_tsk_id = $tid ";
			break;
		case 'event':
			$sql = "SELECT `vnt_datetime`, `vnt_subject`, `vnt_detail`, `vnt_severity` 
					FROM `events_vnt` where vnt_bts_id = $bid and vnt_jbs_id = $jid and vnt_tsk_id = $tid and vnt_rns_id = $rid ";
			break;
		default:
			# code...
			break;
	}
	$sql .= " ORDER BY $sidx $sord LIMIT $start, $limit";

	$result = db_query($sql);

	// constructing a JSON
	$responce->page = $page;
	$responce->total = $total_pages;
	$responce->records = $count;

	if ($result != null) {
		switch ($type) {
			case 'server':
				foreach($result as $row) {
					$responce->rows[] = array(
						'id'	=>	$row->srv_id,
						'cell'	=>	array($row->srv_id,$row->srv_name,$row->srv_ip,($row->srv_enabled==1?'Y':'N'),$row->srv_cpu,$row->srv_memory,$row->srv_disk,$row->srv_receiving
						,$row->srv_sending, $row->srv_created,$row->srv_disabled, $row->srv_description));
				}
				break;

			case 'bot':
				foreach($result as $row) {
					$responce->rows[] = array(
						'id'	=>	$row->bts_id,
						'cell'	=>	array($row->bts_id,$row->bts_name, /*$row->bts_company,*/$row->cmp_name,$row->bts_sector1,$row->bts_sector2,$row->bts_sector3,$row->bts_version,$row->bts_description,($row->bts_enabled==1?'Y':'N'),$row->bts_created,$row->bts_last_updated,$row->bts_path,$row->bts_file));
				}
				break;

			case 'job':
				foreach($result as $row) {
					$responce->rows[] = array(
						'id'	=>	$row->jbs_id,
						'cell'	=>	array("", $row->jbs_id,$row->jbs_name,$row->jbs_description,($row->jbs_enabled==1?'Y':'N'),$row->jbs_created,$row->jbs_last_updated,$row->jbs_path,$row->jbs_file,$row->jbs_dbname,$row->count));
				}
				break;

			case 'task':
				$running = array('Suspended', 'Running');
				$weekdays = array('Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat');
				$months = array('', 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec');
				foreach($result as $row) {
					$responce->rows[] = array(
						'id'=>$row->tsk_id,
						'cell'=>array($row->tsk_id,$row->tsk_name,$row->tsk_description,($row->tsk_enabled==1?'Y':'N'),$row->tsk_created,$row->tsk_last_updated,$row->tsk_minute,
							empty($row->tsk_hour) ? '*' : $row->tsk_hour,
							empty($row->tsk_dom) ? '*' : $row->tsk_dom,
							empty($row->tsk_month) ? '*' : $months[$row->tsk_month],
							empty($row->tsk_dow) ? '*' : $weekdays[$row->tsk_dow],
							$running[$row->tsk_running],
							$row->srv_name . ' - ' . $row->srv_ip));
				}
				break;

			case 'run':
				$run_results = array('Failed', 'Resume Failed', 'Running', 'Success', 'Resume Success');
				foreach($result as $row) {
					$responce->rows[] = array(
						'id'	=>	$row->rns_id,
						'cell'=>array($row->rns_id,$row->rns_start,$row->rns_end,$row->rns_duration, $run_results[$row->rns_result],$row->rns_srv_id));
				}
				break;

			case 'event':
				$severities = array('', 'verbose', 'infomation', 'warning', 'error', 'fatal');
				foreach($result as $row) {
					$responce->rows[] = array(
						'cell'	=>	array($row->vnt_datetime,$row->vnt_subject,$row->vnt_detail,$severities[$row->vnt_severity])
					);
				}
				break;
			default:
				# code...
				break;
		}
	}

	db_set_active();
	// return the formated data
	echo json_encode($responce);
  }
?>
