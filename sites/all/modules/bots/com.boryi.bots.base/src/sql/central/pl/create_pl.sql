-- mysql -u root -p bot_central

-- --------------------------------------------------------
--
-- Stored procedures for `servers_srv`
--

DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `insert_server`(sname varchar(32), ip varchar(32), enabled int)
BEGIN
    IF NOT EXISTS (SELECT * FROM servers_srv WHERE srv_ip = ip) THEN
        INSERT `servers_srv` (`srv_name`, `srv_ip`, `srv_enabled`) VALUES (sname, ip, enabled);
    END IF;
    SELECT srv_id AS id FROM `servers_srv` WHERE srv_ip = ip;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `update_server`(sid int, sname varchar(32), ip varchar(32), enabled int)
BEGIN
    UPDATE `servers_srv` SET srv_name = sname, srv_ip = ip, srv_enabled = enabled WHERE srv_id = sid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `update_server_status`(sid int, cpu decimal(4,2), memory decimal(4,2), disk decimal(9,4), receiving decimal(8,3), sending decimal(8,3))
BEGIN
    UPDATE `servers_srv` SET srv_cpu = cpu, srv_memory = memory, srv_disk = disk, srv_receiving = receiving, srv_sending = sending WHERE srv_id = sid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_servers`(starts int, limits int, out total int)
BEGIN
    SELECT COUNT(*) INTO total FROM servers_srv;

    SET @s = starts;
    SET @l = limits;

    SET @sql = "SELECT srv_id AS id,
            srv_name AS name,
            srv_ip AS ip,
            srv_enabled AS enabled,
            srv_cpu AS cpu,
            srv_memory AS memory,
            srv_disk AS disk,
            srv_receiving AS receiving,
            srv_sending AS sending
        FROM servers_srv 
            ORDER BY srv_id
            LIMIT ?, ?;";

    PREPARE st FROM @sql;
    EXECUTE st USING @s, @l;
    DEALLOCATE PREPARE st;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_server`(sid int)
BEGIN
    SELECT srv_name AS name, srv_ip AS ip FROM servers_srv WHERE srv_id = sid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_server_status`(id int)
BEGIN
    SELECT srv_cpu AS cpu, srv_memory AS memory, srv_disk AS disk, 
            srv_receiving AS receiving, srv_sending AS sending 
        FROM servers_srv WHERE srv_id = id;
END;//


-- --------------------------------------------------------
--
-- Stored procedures for `bots_bts`
--

DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `insert_bot`(bname varchar(128), version varchar(20), description varchar(256), enabled int, bpath varchar(128), bfile varchar(64))
BEGIN
    IF NOT EXISTS (SELECT * FROM bots_bts WHERE bts_name = BINARY bname) THEN
        INSERT `bots_bts` (`bts_name`, `bts_version`, `bts_description`, `bts_enabled`, `bts_created`, `bts_last_updated`, `bts_path`, `bts_file`) VALUES (bname, version, description, enabled, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), bpath, bfile);
    END IF;
    SELECT bts_id AS id FROM `bots_bts` WHERE bts_name = bname;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `update_bot`(bid int, bname varchar(128), version varchar(20), description varchar(256), enabled int, bpath varchar(128), bfile varchar(64))
BEGIN
    UPDATE `bots_bts` SET bts_name = bname, bts_version = version, bts_description = description,
                bts_enabled = enabled, bts_last_updated = CURRENT_TIMESTAMP(), bts_path = bpath, bts_file = bfile
            WHERE bts_id = bid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_bots`(starts int, limits int, sorts varchar(20), dir char(4), out total int)
BEGIN
    SELECT COUNT(*) INTO total FROM bots_bts;

    SET @sql = 'SELECT * FROM bots_bts ORDER BY';
    SET @sql1 = CONCAT(@sql, ' ', sorts, ' ', dir, ' LIMIT ');
    SET @sql2 = CONCAT(@sql1, starts, ', ', limits);

    PREPARE stmt FROM @sql2;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_bot`(bid int)
BEGIN
    SELECT bts_name AS name, bts_version AS version FROM bots_bts WHERE bts_id = bid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `delete_bots`(ids varchar(200), code int)
BEGIN
    IF code >= 0 THEN
        IF code <= 4 THEN 
            SET @sql = CONCAT('DELETE FROM events_vnt WHERE vnt_bts_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;

        IF code <= 3 THEN 
            SET @sql = CONCAT('DELETE FROM runs_rns WHERE rns_bts_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;

        IF code <= 2 THEN 
            SET @sql = CONCAT('DELETE FROM tasks_tsk WHERE tsk_bts_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;

        IF code <= 1 THEN 
            SET @sql = CONCAT('DELETE FROM jobs_jbs WHERE jbs_bts_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;

        IF code = 0 THEN 
            SET @sql = CONCAT('DELETE FROM bots_bts WHERE bts_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;
    END IF;
END;//


-- --------------------------------------------------------
--
-- Stored procedures for `jobs_jbs`
--

DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `insert_job`(bid int, jname varchar(128), description varchar(256), enabled int, jpath varchar(128), jfile varchar(64))
BEGIN
    IF NOT EXISTS (SELECT * FROM jobs_jbs WHERE jbs_bts_id = bid AND jbs_name = BINARY jname) THEN
        SELECT IFNULL(MAX(jbs_id), 0) INTO @max_job_id FROM jobs_jbs WHERE jbs_bts_id = bid LIMIT 1;
        SET @max_job_id = @max_job_id + 1;
        INSERT INTO `jobs_jbs` (`jbs_bts_id`, `jbs_id`, `jbs_name`, `jbs_description`, `jbs_enabled`, `jbs_created`, `jbs_last_updated`, `jbs_path`, `jbs_file`) VALUES (bid, @max_job_id, jname, description, enabled, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), jpath, jfile);
        SELECT @max_job_id AS id;
    ELSE
        SELECT jbs_id AS id FROM jobs_jbs WHERE jbs_bts_id = bid AND jbs_name = jname;
    END IF;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `update_job`(bid int, jid int, jname varchar(128), description varchar(256), enabled int, jpath varchar(128), jfile varchar(64))
BEGIN
    UPDATE `jobs_jbs` SET jbs_name = jname, jbs_description = description,
                jbs_enabled = enabled, jbs_last_updated = CURRENT_TIMESTAMP(), jbs_path = jpath, jbs_file = jfile
            WHERE jbs_bts_id = bid AND jbs_id = jid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_jobs`(bid int, starts int, limits int, sorts varchar(20), dir char(4), out total int)
BEGIN
    SELECT COUNT(*) INTO total FROM jobs_jbs WHERE jbs_bts_id = bid;

    SET @sql = 'SELECT * FROM jobs_jbs WHERE jbs_bts_id = ';
    SET @sql1 = CONCAT(@sql, bid, ' ORDER BY ', sorts, ' ', dir, ' LIMIT ');
    SET @sql2 = CONCAT(@sql1, starts, ', ', limits);

    PREPARE stmt FROM @sql2;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_job`(bid int, jid int)
BEGIN
    SELECT jbs_name AS name FROM jobs_jbs WHERE jbs_bts_id = bid AND jbs_id = jid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `delete_jobs`(bid int, ids varchar(200), code int)
BEGIN
    IF code >= 0 THEN
        IF code <= 3 THEN 
            SET @sql = CONCAT('DELETE FROM events_vnt WHERE vnt_bts_id = ', bid, ' AND vnt_jbs_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;

        IF code <= 2 THEN 
            SET @sql = CONCAT('DELETE FROM runs_rns WHERE rns_bts_id = ', bid, ' AND rns_jbs_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;

        IF code <= 1 THEN 
            SET @sql = CONCAT('DELETE FROM tasks_tsk WHERE tsk_bts_id = ', bid, ' AND tsk_jbs_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;

        IF code <= 0 THEN 
            SET @sql = CONCAT('DELETE FROM jobs_jbs WHERE jbs_bts_id = ', bid, ' AND jbs_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;
    END IF;
END;//


-- --------------------------------------------------------
--
-- Stored procedures for `tasks_tsk`
--

DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `insert_task`(bid int, jid int, tname varchar(128), description varchar(256), enabled int, minutes int, hours int, dom int, months int, dow int)
BEGIN
    IF NOT EXISTS (SELECT * FROM tasks_tsk WHERE tsk_bts_id = bid AND tsk_jbs_id = jid AND tsk_name = BINARY tname) THEN
        SELECT IFNULL(MAX(tsk_id), 0) INTO @max_task_id FROM tasks_tsk WHERE tsk_bts_id = bid AND tsk_jbs_id = jid LIMIT 1;
        SET @max_task_id = @max_task_id + 1;
        INSERT INTO `tasks_tsk` (`tsk_bts_id`, `tsk_jbs_id`, `tsk_id`, `tsk_name`, `tsk_description`, `tsk_enabled`, `tsk_created`, `tsk_last_updated`, `tsk_minute`, `tsk_hour`, `tsk_dom`, `tsk_month`, `tsk_dow`) VALUES (bid, jid, @max_task_id, tname, description, enabled, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), minutes, hours, dom, months, dow);
        SELECT @max_task_id AS id;
    ELSE
        SELECT tsk_id AS id FROM `tasks_tsk` WHERE tsk_bts_id = bid AND tsk_jbs_id = jid AND tsk_name = tname;
    END IF;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `update_task`(bid int, jid int, tid int, tname varchar(128), description varchar(256), enabled int, minutes int, hours int, dom int, months int, dow int)
BEGIN
    UPDATE `tasks_tsk` SET tsk_name = tname, tsk_description = description,
                tsk_enabled = enabled, tsk_last_updated = CURRENT_TIMESTAMP(), 
                tsk_minute = minutes, tsk_hour = hours, tsk_dom = dom,
                tsk_month = months, tsk_dow = dow
            WHERE tsk_bts_id = bid AND tsk_jbs_id = jid AND tsk_id = tid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_tasks`(bid int, jid int, starts int, limits int, sorts varchar(20), dir char(4), out total int)
BEGIN
    SELECT COUNT(*) INTO total FROM tasks_tsk WHERE tsk_bts_id = bid AND tsk_jbs_id = jid;

    SET @sql = 'SELECT * FROM tasks_tsk WHERE tsk_bts_id = ';
    SET @sql1 = CONCAT(@sql, bid, ' AND tsk_jbs_id = ', jid, ' ORDER BY ', sorts, ' ', dir, ' LIMIT ');
    SET @sql2 = CONCAT(@sql1, starts, ', ', limits);

    PREPARE stmt FROM @sql2;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_task`(bid int, jid int, tid int)
BEGIN
    SELECT tsk_name AS name FROM tasks_tsk WHERE tsk_bts_id = bid AND tsk_jbs_id = jid AND tsk_id;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `delete_tasks`(bid int, jid int, ids varchar(200), code int)
BEGIN
    IF code >= 0 THEN
        IF code <= 2 THEN 
            SET @sql = CONCAT('DELETE FROM events_vnt WHERE vnt_bts_id = ', bid, ' AND vnt_jbs_id = ', jid, ' AND vnt_tsk_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;

        IF code <= 1 THEN 
            SET @sql = CONCAT('DELETE FROM runs_rns WHERE rns_bts_id = ', bid, ' AND rns_jbs_id = ', jid, ' AND rns_tsk_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;

        IF code <= 0 THEN 
            SET @sql = CONCAT('DELETE FROM tasks_tsk WHERE tsk_bts_id = ', bid, ' AND tsk_jbs_id = ', jid, ' AND tsk_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;
    END IF;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `update_task_status`(bid int, jid int, tid int, running int, sid int)
BEGIN
    UPDATE tasks_tsk SET tsk_running = running, tsk_srv_id = sid WHERE tsk_bts_id = bid AND tsk_jbs_id = jid AND tsk_id = tid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_task_status`(bid int, jid int, tid int)
BEGIN
    SELECT tsk_running AS running, tsk_srv_id AS sid FROM tasks_tsk WHERE tsk_bts_id = bid AND tsk_jbs_id = jid AND tsk_id = tid;
END;//


-- --------------------------------------------------------
--
-- Stored procedures for `runs_run`
--

DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `insert_run`(bid int, jid int, tid int, rstart datetime, sid int)
BEGIN
    SELECT IFNULL(MAX(rns_id), 0) INTO @max_run_id FROM runs_rns WHERE rns_bts_id = bid AND rns_jbs_id = jid AND rns_tsk_id = tid LIMIT 1;
    SET @max_run_id = @max_run_id + 1;
    INSERT INTO `runs_rns` (`rns_bts_id`, `rns_jbs_id`, `rns_tsk_id`, `rns_id`, `rns_start`, `rns_srv_id`) VALUES (bid, jid, tid, @max_run_id, rstart, sid);

    SELECT @max_run_id AS id;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `update_run`(bid int, jid int, tid int, rid int, rend datetime, duration decimal(8,2), rresult int)
BEGIN
    UPDATE `runs_rns` SET rns_end = rend, rns_duration = duration, rns_result = rresult
            WHERE rns_bts_id = bid AND rns_jbs_id = jid AND rns_tsk_id = tid AND rns_id = rid;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_runs`(bid int, jid int, tid int, starts int, limits int, sorts varchar(20), dir char(4), out total int)
BEGIN
    SELECT COUNT(*) INTO total FROM runs_rns WHERE rns_bts_id = bid AND rns_jbs_id = jid AND rns_tsk_id = tid;

    SET @sql = 'SELECT * FROM runs_rns WHERE rns_bts_id = ';
    SET @sql1 = CONCAT(@sql, bid, ' AND rns_jbs_id = ', jid, ' AND rns_tsk_id = ', tid, ' ORDER BY ', sorts, ' ', dir, ' LIMIT ');
    SET @sql2 = CONCAT(@sql1, starts, ', ', limits);

    PREPARE stmt FROM @sql2;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `delete_runs`(bid int, jid int, tid int, ids varchar(200), code int)
BEGIN
    IF code >= 0 THEN
        IF code <= 1 THEN 
            SET @sql = CONCAT('DELETE FROM events_vnt WHERE vnt_bts_id = ', bid, ' AND vnt_jbs_id = ', jid, ' AND vnt_tsk_id = ', tid, ' AND vnt_rns_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;

        IF code <= 0 THEN 
            SET @sql = CONCAT('DELETE FROM runs_rns WHERE rns_bts_id = ', bid, ' AND rns_jbs_id = ', jid, ' AND rns_tsk_id = ', tid, ' AND rns_id IN (', ids, ')');
            PREPARE stmt FROM @sql;
            EXECUTE stmt;
            DEALLOCATE PREPARE stmt;
        END IF;
    END IF;
END;//


-- --------------------------------------------------------
--
-- Stored procedures for `events_vnt`
--

DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `insert_event`(bid int, jid int, tid int, rid int, eventtime datetime, subject varchar(128), detail varchar(8000), severity int)
BEGIN
    INSERT INTO `events_vnt` (`vnt_bts_id`, `vnt_jbs_id`, `vnt_tsk_id`, `vnt_rns_id`, `vnt_datetime`, `vnt_subject`, `vnt_detail`, `vnt_severity`) VALUES (bid, jid, tid, rid, eventtime, subject, detail, severity);
END;//


DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `get_events`(bid int, jid int, tid int, rid int, starts int, limits int, sorts varchar(20), dir char(4), out total int)
BEGIN
    SELECT COUNT(*) INTO total FROM events_vnt WHERE vnt_bts_id = bid AND vnt_jbs_id = jid AND vnt_tsk_id = tid AND vnt_rns_id = rid;

    SET @sql = 'SELECT * FROM events_vnt WHERE vnt_bts_id = ';
    SET @sql1 = CONCAT(@sql, bid, ' AND vnt_jbs_id = ', jid, ' AND vnt_tsk_id = ', tid, ' AND vnt_rns_id = ', rid, ' ORDER BY ', sorts, ' ', dir, ' LIMIT ');
    SET @sql2 = CONCAT(@sql1, starts, ', ', limits);

    PREPARE stmt FROM @sql2;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END;//



DELIMITER //
CREATE DEFINER=`botmaster`@`localhost` PROCEDURE `delete_events`(bid int, jid int, tid int, rid int, code int, severity int, dfrom varchar(32), dto varchar(32))
BEGIN
    SET @sql = 'DELETE FROM events_vnt WHERE vnt_bts_id = ';
    SET @sql1 = CONCAT(@sql, bid, ' AND vnt_jbs_id = ', jid, ' AND vnt_tsk_id = ', tid, ' AND vnt_rns_id = ', rid);

    IF code = 2 THEN
        SET @sql3 = CONCAT(@sql1, ' AND vnt_severity = ', severity);

        IF (LENGTH(dfrom) > 0 AND LENGTH(dto) > 0) THEN 
            SET @sql2 := CONCAT(@sql3, ' AND vnt_datetime > ''', dfrom, ''' AND vnt_datetime < ''',  dto , '''' );
        ELSEIF LENGTH(dfrom) > 0 THEN 
            SET @sql2 := CONCAT(@sql3, ' AND vnt_datetime > ''', dfrom, '''');
        ELSEIF LENGTH(dto) > 0 THEN 
            SET @sql2 := CONCAT(@sql3, ' AND vnt_datetime <''',  dto , '''' );
        END IF;
    ELSEIF code = 1 THEN
        SET @sql2 = CONCAT(@sql1, ' AND vnt_severity = ', severity);
    ELSEIF code = 0 THEN
        IF (LENGTH(dfrom) > 0 AND LENGTH(dto) > 0) THEN 
            SET @sql2 := CONCAT(@sql1, ' AND vnt_datetime > ''', dfrom, ''' AND vnt_datetime < ''',  dto , '''' );
        ELSEIF LENGTH(dfrom) > 0 THEN 
            SET @sql2 := CONCAT(@sql1, ' AND vnt_datetime > ''', dfrom, '''');
        ELSEIF LENGTH(dto) > 0 THEN 
            SET @sql2 := CONCAT(@sql1, ' AND vnt_datetime <''',  dto , '''' );
        END IF;
    END IF;
    PREPARE stmt FROM @sql2;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END;//
