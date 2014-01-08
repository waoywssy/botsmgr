-- mysql -u root -p bot_local

DELETE FROM `checklist_chc`;
DELETE FROM `bot_runs_btrns`;
DELETE FROM `web_statistic_wbs`;

DELETE FROM `qa_statistic_qst`;

ALTER TABLE bot_runs_btrns auto_increment = 1;