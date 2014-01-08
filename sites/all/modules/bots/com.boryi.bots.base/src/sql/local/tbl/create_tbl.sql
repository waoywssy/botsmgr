-- mysql -u root -p bot_local

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- --------------------------------------------------------
--
-- Table structure for table `checklist_chc`
--
CREATE TABLE IF NOT EXISTS `checklist_chc` (
  `chc_bts_id` int(4) NOT NULL COMMENT 'Id of the bot',
  `chc_jbs_id` int(4) NOT NULL COMMENT 'Id of the job of the bot',
  `chc_tsk_id` int(4) NOT NULL COMMENT 'Id of the task of the job of the bot. -1 means no task',
  `chc_url` varchar(328) NOT NULL COMMENT 'URL to be crawled by the run of the job of the bot',
  `chc_stage` int(4) NOT NULL DEFAULT '0' COMMENT 'Crawling stage of the URL, e.g. top category stage, sub-category stage, product stage, product detail stage ',
  `chc_id` int(4) NOT NULL COMMENT 'Id related to the URL, e.g. product Id, category Id',
  `chc_status` tinyint(1) NOT NULL DEFAULT '-1' COMMENT '-1: not checked, 0: checking, 1: checked',
  `chc_level` int(4) NOT NULL default '0',
  PRIMARY KEY (`chc_bts_id`, `chc_jbs_id`, `chc_tsk_id`, `chc_url`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Checklist table';


-- --------------------------------------------------------
--
-- Table structure for table `bot_runs_btrns`
--
CREATE TABLE IF NOT EXISTS `bot_runs_btrns` (
  `btrns_id` int(4) NOT NULL auto_increment COMMENT 'Id of the run in the bot database',
  `btrns_bts_id` int(4) NOT NULL COMMENT 'Id of the bot',
  `btrns_jbs_id` int(4) NOT NULL COMMENT 'Id of the job of the bot',
  `btrns_tsk_id` int(4) NOT NULL COMMENT 'Id of the task of the job of the bot. -1 means no task',
  `btrns_rns_id` int(4) NOT NULL COMMENT 'Id of the run of the task of the job of the bot.',
  `btrns_rns_start` datetime NOT NULL COMMENT 'Run date and time',
  PRIMARY KEY (`btrns_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Bot runs table' AUTO_INCREMENT=1;


-- --------------------------------------------------------
--
-- Table structure for table `web_statistic_wbs`
--

CREATE TABLE IF NOT EXISTS `web_statistic_wbs` (
  `wbs_btrns_id` int(4) NOT NULL COMMENT 'Id of the run in the bot database',
  `wbs_is_resume` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Whether the run is resumed',
  `wbs_start` datetime NOT NULL COMMENT 'Start time of web crawling',
  `wbs_end` datetime NOT NULL COMMENT 'End time of web crawling',
  `wbs_duration` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Duration of web crawling in hours',
  `wbs_actual_duration` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT 'Actual web traffic time in hours',
  `wbs_page_hits_total` int(8) NOT NULL DEFAULT '0' COMMENT 'Total page hits on the web site',
  `wbs_kb_downloaded_total` int(8) NOT NULL DEFAULT '0' COMMENT 'Total kilobytes downloaded from the web site, including headers and contents',
  `wbs_kb_uploaded_total` int(8) NOT NULL DEFAULT '0' COMMENT 'Total kilobytes uploaded to the web site, including headers and contents',
  `wbs_b_request_header_total` int(8) NOT NULL DEFAULT '0' COMMENT 'Total bytes of request headers',
  `wbs_b_request_content_total` int(8) NOT NULL DEFAULT '0' COMMENT 'Total bytes of request contents',
  `wbs_b_response_header_total` int(8) NOT NULL DEFAULT '0' COMMENT 'Total bytes of response headers',
  `wbs_b_response_content_total` int(8) NOT NULL DEFAULT '0' COMMENT 'Total bytes of response contents',
  `wbs_max_page_hits_per_hour` int(4) NOT NULL DEFAULT '1' COMMENT 'Maximum page hits per hour allowed to the web site',
  `wbs_max_kb_per_hour` int(4) NOT NULL DEFAULT '1' COMMENT 'Maximum kilobytes downloaded per hour allowed from the web site',
  `wbs_threads` int(4) NOT NULL COMMENT 'Number of web crawling threads used',
  PRIMARY KEY (`wbs_btrns_id`, `wbs_start`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Web statistic table';


-- --------------------------------------------------------
--
-- Table structure for table `qa_statistic_qst`
--

CREATE TABLE IF NOT EXISTS `qa_statistic_qst` (
  `qst_btrns_id` int(4) NOT NULL COMMENT 'Id of the run in the bot database',
  `qst_is_valid` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Whether qa statistic is valid by comparing to the previous row.  It means whether data of the run is valid',
  `qst_total` int(4) NOT NULL DEFAULT '0' COMMENT 'Total number of a property, e.g. products, categories',
  `qst_total_distinct` int(4) NOT NULL DEFAULT '0' COMMENT 'Number of distinct values of a property',
  `qst_total_nulls` int(4) NOT NULL DEFAULT '0' COMMENT 'Number of null values of a property',
  `qst_average` decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT 'Average of a property, e.g. products, categories, prices, sale prices, length, levels, posting time',
  `qst_min` decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT 'Minimum of a property',
  `qst_max` decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT 'Maximum of a property',
  PRIMARY KEY (`qst_btrns_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='QA statistic table';
