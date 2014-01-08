-- mysql -u root -p bot_local

-- --------------------------------------------------------
--
-- Stored procedures for `checklist_chc`
--

DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `insert_checkitem`(bid int, jid int, tid int, url varchar(512), stage int, id int, level int)
BEGIN
    IF NOT EXISTS (SELECT * FROM checklist_chc WHERE chc_bts_id = bid AND chc_jbs_id = jid AND chc_tsk_id = tid AND chc_url = BINARY url) THEN
        INSERT `checklist_chc` (`chc_bts_id`, `chc_jbs_id`, `chc_tsk_id`, `chc_url`, `chc_stage`, `chc_id`, `chc_status`, `chc_level`) VALUES (bid, jid, tid, url, stage, id, -1, level);
    END IF;
END;//


DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `update_checkingitem`(bid int, jid int, tid int, url varchar(512))
BEGIN
    UPDATE `checklist_chc` SET chc_status = 0 WHERE chc_bts_id = bid AND chc_jbs_id = jid AND chc_tsk_id = tid AND chc_url = BINARY url;
END;//


DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `update_checkeditem`(bid int, jid int, tid int, url varchar(512))
BEGIN
    UPDATE `checklist_chc` SET chc_status = 1 WHERE chc_bts_id = bid AND chc_jbs_id = jid AND chc_tsk_id = tid AND chc_url = BINARY url;
END;//


DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `get_checklist`(bid int, jid int, tid int)
BEGIN
    SELECT chc_url AS url, chc_stage AS stage, chc_id AS id, chc_level as level FROM `checklist_chc` WHERE chc_bts_id = bid AND chc_jbs_id = jid AND chc_tsk_id = tid AND chc_status = -1;
END;//


DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `get_checkinglist`(bid int, jid int, tid int)
BEGIN
    SELECT chc_url AS url, chc_stage AS stage, chc_id AS id, chc_level as level FROM `checklist_chc` WHERE chc_bts_id = bid AND chc_jbs_id = jid AND chc_tsk_id = tid AND chc_status = 0;
END;//


DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `clear_checklist`(bid int, jid int, tid int)
BEGIN
    DELETE FROM `checklist_chc` WHERE chc_bts_id = bid AND chc_jbs_id = jid AND chc_tsk_id = tid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `update_checklist_status`(id int, status int)
BEGIN
	UPDATE checklist_chc SET chc_status = status WHERE chc_id = id; 
END;//


DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `get_resumed_run`(bid int, jid int, tid int)
BEGIN
    IF EXISTS (SELECT * FROM `checklist_chc` WHERE chc_bts_id = bid AND chc_jbs_id = jid AND chc_tsk_id = tid AND chc_status <= 0) THEN
        SELECT MAX(btrns_id) INTO @brid FROM bot_runs_btrns WHERE btrns_bts_id = bid AND btrns_jbs_id = jid AND btrns_tsk_id = tid;
        SELECT @brid AS brid, btrns_rns_id AS rid, btrns_rns_start AS rstart FROM bot_runs_btrns WHERE btrns_id = @brid;
    ELSE
        SELECT 0 AS brid, 0 AS rid;
    END IF;
END;//


DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `update_decheckingitems`(bid int, jid int, tid int)
BEGIN
    UPDATE `checklist_chc` SET chc_status = -1 WHERE chc_bts_id = bid AND chc_jbs_id = jid AND chc_tsk_id = tid AND chc_status = 0;
END;//


-- --------------------------------------------------------
--
-- Stored procedures for `bot_runs_btrns`
--

DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `insert_bot_run`(bid int, jid int, tid int, rid int, rstart datetime)
BEGIN
    IF NOT EXISTS (SELECT * FROM bot_runs_btrns WHERE btrns_bts_id = bid AND btrns_jbs_id = jid AND btrns_tsk_id = tid AND btrns_rns_id = rid) THEN
        INSERT `bot_runs_btrns` (`btrns_bts_id`, `btrns_jbs_id`, `btrns_tsk_id`, `btrns_rns_id`, `btrns_rns_start`) VALUES (bid, jid, tid, rid, rstart);
    END IF;
    SELECT btrns_id AS id FROM bot_runs_btrns WHERE btrns_bts_id = bid AND btrns_jbs_id = jid AND btrns_tsk_id = tid AND btrns_rns_id = rid;
END;//


-- --------------------------------------------------------
--
-- Stored procedures for `web_statistic_wbs`
--

DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `insert_web_statistic`(brid int, resume tinyint(1), wstart datetime, wend datetime, duration decimal(10,2), actual_duration decimal(10,2), page_hits_total int(8), kb_downloaded_total int(8), kb_uploaded_total int(8), b_request_header_total int(8), b_request_content_total int(8), b_response_header_total int(8), b_response_content_total int(8), max_page_hits_per_hour int(4), max_kb_per_hour int(4), threads int(4))
BEGIN
    IF NOT EXISTS (SELECT * FROM web_statistic_wbs WHERE wbs_btrns_id = brid AND wbs_start = wstart) THEN
        INSERT `web_statistic_wbs` (`wbs_btrns_id`, `wbs_is_resume`, `wbs_start`, `wbs_end`, `wbs_duration`, `wbs_actual_duration`, `wbs_page_hits_total`, `wbs_kb_downloaded_total`, `wbs_kb_uploaded_total`, `wbs_b_request_header_total`, `wbs_b_request_content_total`, `wbs_b_response_header_total`, `wbs_b_response_content_total`, `wbs_max_page_hits_per_hour`, `wbs_max_kb_per_hour`, `wbs_threads`) VALUES (brid, resume, wstart, wend, duration, actual_duration, page_hits_total, kb_downloaded_total, kb_uploaded_total, b_request_header_total, b_request_content_total, b_response_header_total, b_response_content_total, max_page_hits_per_hour, max_kb_per_hour, threads);
    END IF;
END;//


-- --------------------------------------------------------
--
-- Stored procedures for `qa_statistic_qst`
--

DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `insert_qa_statistic`(brid int, total int, total_distinct int, total_nulls int, average decimal(18,4), qmin decimal(18,4), qmax decimal(18,4))
BEGIN
    IF NOT EXISTS (SELECT * FROM qa_statistic_qst WHERE qst_btrns_id = brid) THEN
        INSERT `qa_statistic_qst` (`qst_btrns_id`, `qst_is_valid`, `qst_total`, `qst_total_distinct`, `qst_total_nulls`, `qst_average`, `qst_min`, `qst_max`) VALUES (brid, 0, total, total_distinct, total_nulls, average, qmin, qmax);
    END IF;
END;//


DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `update_qa_statistic`(brid int, valid tinyint(1))
BEGIN
    UPDATE `qa_statistic_qst` SET qst_is_valid = valid WHERE qst_btrns_id = brid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `delete_qa_statistic`(brid int)
BEGIN
    DELETE FROM `qa_statistic_qst` WHERE qst_btrns_id = brid;
END;//



-- --------------------------------------------------------
--
-- Stored procedures for BOT or WEBCRAWLER: INSERT and UPDATE data, DELETE half finished data to resume
--


-- --------------------------------------------------------
--
-- Stored procedures for BOT automaitc qa analysis: compute qa statistic, and analyze qa statistic
--
-- Compute qa statistic: 
--   1) find the latest btrns_id (bot run id) which has not been computed for qa statistic
--   2) compute the qa statistic for each btrns_id (such as: total, total distinct, total null, average, minimum, maximum, standard deviation, etc.)
--
-- Analyze qa statistic: 
--   1) find the earlest qa statistic which has not been analyzed (valid = -1)
--   2) compare it the its most recent valid qa statistic (valid = 1) of the same kind.  For example, the same job id; or the same job id and task id; or the same bot id and job id and task id; or the same task id.  Depends on the real cases
--   3) mark the qa statistic to be valid (1) or invalid (0)
--   4) go to the next un-analyzed qa statistic
--   5) return the report
-- 

DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `qa_compute_statistic`()
BEGIN
END;//



DELIMITER //
CREATE DEFINER=`botmaster_local`@`localhost` PROCEDURE `qa_analyze`()
BEGIN
END;//

