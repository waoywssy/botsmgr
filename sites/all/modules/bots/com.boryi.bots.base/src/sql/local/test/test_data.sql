-- mysql -u root -p bot_local

INSERT INTO `checklist_chc` (`chc_bts_id`, `chc_jbs_id`, `chc_tsk_id`, `chc_url`, `chc_stage`, `chc_id`, `chc_status`) VALUES 
	(1, 1, 1, 'http://www.boryi.com/index.html', 1, 1, 1),
	(1, 1, 1, 'http://www.boryi.com/company/1.html', 2, 2, -1),
	(1, 1, 1, 'http://www.boryi.com/company/2.html', 2, 3, -1),
	(1, 1, 1, 'http://www.boryi.com/company/3.html', 2, 4, -1),
	(1, 1, 1, 'http://www.boryi.com/job/1.html', 3, 5, -1),
	(1, 1, 1, 'http://www.boryi.com/job/2.html', 3, 6, -1),
	(1, 1, 1, 'http://www.boryi.com/job/3.html', 3, 7, -1),
	(1, 1, 1, 'http://www.boryi.com/contact/1.html', 2, 8, -1),
	(1, 1, 1, 'http://www.boryi.com/contact/2.html', 2, 9, -1),
	(1, 1, 1, 'http://www.boryi.com/contact/3.html', 2, 10, -1),
	(1, 1, 1, 'http://www.boryi.com/tool/1.html', 3, 11, 0),
	(1, 1, 1, 'http://www.boryi.com/tool/2.html', 3, 12, 0),
	(1, 2, 1, 'http://www.ebay.com/tv/', 1, 1, 1),
	(1, 2, 1, 'http://www.ebay.com/tv/us/', 2, 2, 1),
	(1, 2, 1, 'http://www.ebay.com/tv/uk/', 2, 3, -1),
	(1, 2, 1, 'http://www.ebay.com/tv/de/', 2, 4, -1),
	(1, 2, 1, 'http://www.ebay.com/tv/cn/', 2, 5, -1),
	(1, 2, 1, 'http://www.ebay.com/tv/fr/', 2, 6, -1),
	(1, 2, 1, 'http://www.ebay.com/tv/us/ny/', 3, 7, 0),
	(1, 2, 1, 'http://www.ebay.com/tv/us/pa/', 3, 8, 0),
	(1, 2, 1, 'http://www.ebay.com/tv/us/fl/', 3, 9, -1),
	(1, 2, 1, 'http://www.ebay.com/tv/us/ca/', 3, 10, -1),
	(1, 2, 1, 'http://www.ebay.com/tv/us/ny/nyc/', 4, 11, -1),
	(1, 2, 1, 'http://www.ebay.com/tv/us/pa/philadelphia/', 4, 12, -1);

INSERT INTO `bot_runs_btrns` (`btrns_bts_id`, `btrns_jbs_id`, `btrns_tsk_id`, `btrns_rns_id`, `btrns_rns_start`) VALUES
    (1, 1, 1, 1, '2009-09-01 10:00:30.003'),
    (1, 1, 1, 2, '2009-09-01 11:00:30.093'),
    (1, 1, 1, 3, '2009-09-01 12:30:30.073'),
    (1, 1, 1, 4, '2009-09-01 13:50:30.013'),
    (1, 1, 1, 5, '2009-09-01 14:00:00.033'),
    (1, 1, 1, 6, '2009-09-01 10:00:30.003'),
    (1, 1, 2, 1, '2009-09-02 10:00:30.003'),
    (1, 1, 2, 2, '2009-09-02 10:00:30.003'),
    (1, 1, 2, 3, '2009-09-02 10:00:30.003'),
    (1, 1, 2, 4, '2009-09-02 10:00:30.003'),
    (2, 1, 1, 1, '2009-09-03 10:00:30.003'),
    (2, 1, 2, 1, '2009-09-03 22:00:30.003'),
    (2, 1, 1, 2, '2009-09-04 10:00:30.003'),
    (2, 1, 2, 2, '2009-09-04 22:00:30.003');

INSERT INTO `web_statistic_wbs` (`wbs_btrns_id`, `wbs_is_resume`, `wbs_start`, `wbs_end`, `wbs_duration`, `wbs_actual_duration`, `wbs_page_hits_total`, `wbs_kb_downloaded_total`, `wbs_kb_uploaded_total`, `wbs_b_request_header_total`, `wbs_b_request_content_total`, `wbs_b_response_header_total`, `wbs_b_response_content_total`, `wbs_max_page_hits_per_hour`, `wbs_max_kb_per_hour`, `wbs_threads`) VALUES
    (1, 0, '2009-09-01 10:01:23.234', '2009-09-01 21:34:32.943', 11.52, 10.34, 21313, 44325, 345, 23421, 345, 23454, 42345456, 3000, 4000, 1),
    (2, 0, '2009-09-01 11:01:23.234', '2009-09-01 22:34:32.943', 11.52, 10.34, 21313, 44325, 345, 23421, 345, 23454, 42345456, 3000, 4000, 1),
    (3, 0, '2009-09-02 11:01:23.234', '2009-09-02 13:34:32.943', 3.92, 3.14, 21313, 44325, 345, 23421, 345, 23454, 42345456, 3000, 4000, 1),
    (3, 1, '2009-09-03 11:01:23.234', '2009-09-01 20:34:32.943', 7.82, 7.24, 21313, 44325, 345, 23421, 345, 23454, 42345456, 3000, 4000, 1);

INSERT INTO `qa_statistic_qst` (`qst_btrns_id`, `qst_is_valid`, `qst_total`, 
    `qst_total_distinct`, `qst_total_nulls`, `qst_average`, `qst_min`, `qst_max`) VALUES
    (1, 1, 23454, 23424, 0, 324.21, 234.23, 6423.00),
    (2, 1, 23454, 23424, 1, 324.21, 234.23, 6423.00),
    (3, 1, 23454, 23424, 1, 324.21, 234.23, 6423.00),
    (4, 1, 23454, 23424, 0, 324.21, 234.23, 6423.00),
    (5, 0, 23454, 23424, 3, 324.21, 234.23, 6423.00),
    (6, 1, 23454, 23424, 0, 324.21, 234.23, 6423.00),
    (7, 0, 23454, 23424, 23, 324.21, 234.23, 6423.00),
    (8, 1, 23454, 23424, 0, 324.21, 234.23, 6423.00);
