-- mysql -u root -p bot_central

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- --------------------------------------------------------
--
-- Table structure for table `servers_srv`
--
CREATE TABLE IF NOT EXISTS `servers_srv` (
  `srv_id` int(4) NOT NULL auto_increment COMMENT 'Server Id',
  `srv_name` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Server name',
  `srv_ip` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Server ip address',
  `srv_enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'If server is enabled for running bots.  If server is disabled, no more bots can be run on the server',
  `srv_cpu` decimal(4,2) NOT NULL DEFAULT '0.00' COMMENT 'The cpu usage in percentage, 40.34%',
  `srv_memory` decimal(4,2) NOT NULL DEFAULT '0.00' COMMENT 'The memory usage in percentage, 29.30%',
  `srv_disk` decimal(9,4) NOT NULL DEFAULT '0.00' COMMENT 'The disk available space in GB',
  `srv_receiving` decimal(8,3) NOT NULL DEFAULT '0.00' COMMENT 'The network receiving speed in MBps',
  `srv_sending` decimal(8,3) NOT NULL DEFAULT '0.00' COMMENT 'The network sending speed in MBps',
  PRIMARY KEY  (`srv_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Servers table' AUTO_INCREMENT=1;


-- --------------------------------------------------------
--
-- Table structure for table `bots_bts`
--
CREATE TABLE IF NOT EXISTS `bots_bts` (
  `bts_id` int(4) NOT NULL auto_increment COMMENT 'Bot id',
  `bts_name` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Bot name',
  `bts_version` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Bot version',
  `bts_description` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Bot description',
  `bts_enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'If bot is enabled',
  `bts_created` datetime NOT NULL COMMENT 'The datetime when the bot was created',
  `bts_last_updated` datetime NOT NULL COMMENT 'The datetime when the bot was last updated',
  `bts_path` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Path where Jar file stored',
  `bts_file` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Jar file name',
  PRIMARY KEY  (`bts_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Bots table' AUTO_INCREMENT=1;


-- --------------------------------------------------------
--
-- Table structure for table `jobs_jbs`
--
CREATE TABLE IF NOT EXISTS `jobs_jbs` (
  `jbs_bts_id` int(4) NOT NULL COMMENT 'Bot Id, primary key (jbs_bts_id, jbs_id)',
  `jbs_id` int(4) NOT NULL COMMENT 'Job id – unique and identity for each Bot Id',
  `jbs_name` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Job name',
  `jbs_description` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Job description',
  `jbs_enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'If job is enabled',
  `jbs_created` datetime NOT NULL COMMENT 'The datetime when the job was created',
  `jbs_last_updated` datetime NOT NULL COMMENT 'The datetime when the job was last updated',
  `jbs_path` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Path where configuration file stored',
  `jbs_file` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Configuration file name',
  PRIMARY KEY  (`jbs_bts_id`, `jbs_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Jobs table';


-- --------------------------------------------------------
--
-- Table structure for table `tasks_tsk`
--
CREATE TABLE IF NOT EXISTS `tasks_tsk` (
  `tsk_bts_id` int(4) NOT NULL COMMENT 'Bot Id, primary key (tsk_bts_id, tsk_jbs_id, tsk_id)',
  `tsk_jbs_id` int(4) NOT NULL COMMENT 'Job id – unique and identity for each Bot Id',
  `tsk_id` int(4) NOT NULL COMMENT 'Task id – unique and identity for each (Bot Id, Job Id) pair',
  `tsk_name` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Task name',
  `tsk_description` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Task description',
  `tsk_enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'If task is enabled',
  `tsk_created` datetime NOT NULL COMMENT 'The datetime when the task was created',
  `tsk_last_updated` datetime NOT NULL COMMENT 'The datetime when the task was last updated',
  `tsk_minute` tinyint(1) DEFAULT NULL COMMENT 'Minute (m): 0 - 59',
  `tsk_hour` tinyint(1) DEFAULT NULL COMMENT 'Hour (h): 0 - 23 ',
  `tsk_dom` tinyint(1) DEFAULT NULL COMMENT 'Day of Month (dom): 1 - 31',
  `tsk_month` tinyint(1) DEFAULT NULL COMMENT 'Month (mon): 1 - 12',
  `tsk_dow` tinyint(1) DEFAULT NULL COMMENT 'Day of Week (dow): 0 - 6',
  `tsk_running` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'If the job of the task is running',
  `tsk_srv_id` int(4) NOT NULL DEFAULT '-1' COMMENT 'Server id that the task is running on',
  PRIMARY KEY (`tsk_bts_id`,`tsk_jbs_id`,`tsk_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Tasks table';


-- --------------------------------------------------------
--
-- Table structure for table `runs_rns`
--
CREATE TABLE IF NOT EXISTS `runs_rns` (
  `rns_bts_id` int(4) NOT NULL COMMENT 'Bot Id, primary key(rns_bts_id, rns_jbs_id, rns_tsk_id, rns_id)',
  `rns_jbs_id` int(4) NOT NULL COMMENT 'Job id – unique and identity for each Bot Id',
  `rns_tsk_id` int(4) NOT NULL COMMENT 'Task id – unique and identity for each (Bot Id, Job Id) pair',
  `rns_id` int(4) NOT NULL COMMENT 'Run id – unique and identity for each (Bot Id, Job Id, Task Id) triplet',
  `rns_start` datetime NOT NULL COMMENT 'When the run starts',
  `rns_end` datetime DEFAULT NULL COMMENT 'When the run ends',
  `rns_duration` decimal(8,2) DEFAULT NULL COMMENT 'Duration in hours (e.g. 6.25) - NULL means running',
  `rns_result` tinyint(1) NOT NULL DEFAULT '2' COMMENT '0 failed, 1 resume failed, 2 running, 1 success, 2 resume success',
  `rns_srv_id` int(4) NOT NULL DEFAULT '-1' COMMENT 'Server id that the task was running on',
  PRIMARY KEY (`rns_bts_id`,`rns_jbs_id`,`rns_tsk_id`, `rns_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Runs table';


-- --------------------------------------------------------
--
-- Table structure for table `events_vnt`
--
CREATE TABLE IF NOT EXISTS `events_vnt` (
  `vnt_bts_id` int(4) NOT NULL COMMENT 'Bot Id',
  `vnt_jbs_id` int(4) NOT NULL COMMENT 'Job id – unique and identity for each Bot Id',
  `vnt_tsk_id` int(4) NOT NULL COMMENT 'Task id – unique and identity for each (Bot Id, Job Id) pair',
  `vnt_rns_id` int(4) NOT NULL COMMENT 'Run id – unique and identity for each (Bot Id, Job Id, Task Id) triplet',
  `vnt_datetime` datetime NOT NULL COMMENT 'Event datetime',
  `vnt_subject` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT 'The subject of the event',
  `vnt_detail` varchar(8000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'The detail of the event',
  `vnt_severity` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Severity – 0 verbose, 1 information, 2 warning, 3 error, 4 fatal, 5 qa',
  PRIMARY KEY (`vnt_bts_id`,`vnt_jbs_id`,`vnt_tsk_id`,`vnt_rns_id`, `vnt_datetime`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Events table';


-- --------------------------------------------------------
--
-- Table structure for table `run_results_rnr`
--
CREATE TABLE IF NOT EXISTS `run_results_rnr` (
  `rnr_id` tinyint(1) NOT NULL auto_increment COMMENT 'Id of the run results, starting from 0',
  `rnr_name` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '0 failed, 1 resume failed, 2 running, 1 success, 2 resume success',
  PRIMARY KEY (`rnr_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Run results table' AUTO_INCREMENT=0;


-- --------------------------------------------------------
--
-- Table structure for table `event_severities_vntsvr`
--
CREATE TABLE IF NOT EXISTS `event_severities_vntsvr` (
  `vntsvr_id` tinyint(1) NOT NULL auto_increment COMMENT 'Id of the event severities results, starting from 0',
  `vntsvr_name` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '0 verbose, 1 information, 2 warning, 3 error, 4 fatal, 5 qa',
  PRIMARY KEY (`vntsvr_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Event severities table' AUTO_INCREMENT=0;


INSERT INTO `run_results_rnr` (`rnr_name`) VALUES
	('failed'),
	('resume failed'),
	('running'),
	('success'),
	('resume success');


INSERT INTO `event_severities_vntsvr` (`vntsvr_name`) VALUES
	('verbose'),
	('information'),
	('warning'),
	('error'),
    ('fatal'),
	('qa');
