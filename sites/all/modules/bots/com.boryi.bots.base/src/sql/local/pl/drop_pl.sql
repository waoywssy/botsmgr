-- mysql -u root -p bot_local

DROP PROCEDURE IF EXISTS `insert_checkitem`;
DROP PROCEDURE IF EXISTS `update_checkingitem`;
DROP PROCEDURE IF EXISTS `update_checkeditem`;
DROP PROCEDURE IF EXISTS `get_checklist`;
DROP PROCEDURE IF EXISTS `get_checkinglist`;
DROP PROCEDURE IF EXISTS `clear_checklist`;
DROP PROCEDURE IF EXISTS `update_checklist_status`;

DROP PROCEDURE IF EXISTS `get_resumed_run`;
DROP PROCEDURE IF EXISTS `update_decheckingitems`;

DROP PROCEDURE IF EXISTS `insert_bot_run`;

DROP PROCEDURE IF EXISTS `insert_web_statistic`;

DROP PROCEDURE IF EXISTS `insert_qa_statistic`;
DROP PROCEDURE IF EXISTS `update_qa_statistic`;
DROP PROCEDURE IF EXISTS `delete_qa_statistic`;

DROP PROCEDURE IF EXISTS `qa_compute_statistic`;
DROP PROCEDURE IF EXISTS `qa_analyze`;
