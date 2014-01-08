-- mysql -u root -p bot_central

CALL insert_server('TS001', '10.30.0.1', 1);
CALL update_server(1, 'BS001b', '10.10.0.201', 0);
CALL update_server_status(1, 20.32, 50.23, 10.2932, 1.234, 4.239);
CALL get_servers(0, 10, @total);
CALL get_server(1);
CALL get_server_status(1);

CALL insert_bot('PriceLine', '2.0.1', 'PriceLine hotel check', 1, '/com/boryi/bots/PriceLine/', 'priceline.jar');
CALL update_bot(1, 'PriceLine1.0', '2.1.1', 'PriceLine 1.0 hotel check', 1, '/com/boryi/bots/PriceLine1.0/', 'priceline1.jar');
CALL get_bots(0, 10, 'bts_name', 'DESC', @total);
CALL get_bot(1);
CALL delete_bots('6,7', 0);
CALL delete_bots('5', 1);
CALL delete_bots('4', 2);
CALL delete_bots('3', 3);
CALL delete_bots('1', 4);

CALL insert_job(1, 'eBay CN', 'eBay China site', 1, '/com/boryi/bots/eBay/config-cn/', 'ebay_cn.config.xml');
CALL update_job(1, 1, 'eBay CN BJ', 'eBay China BJ site', 1, '/com/boryi/bots/eBay/config-cn-bj/', 'ebay_cn_bj.config.xml');
CALL get_jobs(1, 0, 10, 'jbs_name', 'DESC', @total);
CALL get_job(1, 1);
CALL delete_jobs(1, '1', 0);
CALL delete_jobs(1, '2', 1);
CALL delete_jobs(1, '3', 2);
CALL delete_jobs(1, '3', 3);

CALL insert_task(1, 1, 'Daily morning', 'Check everyday morning', 1, 23, 12, null, null, 1);
CALL insert_task(1, 1, 'Daily morning 1', 'Check everyday morning', 1, 23, 12, null, null, 1);
CALL update_task(1, 1, 1, 'Daily morning 2', 'Check everyday morning 2', 1, 20, 2, 12, 4, null);
CALL get_tasks(1, 1, 0, 10, 'tsk_name', 'DESC', @total);
CALL get_task(1, 1, 1);
CALL delete_tasks(1, 1, '1,2', 0);
CALL delete_tasks(1, 1, '3', 1);
CALL delete_tasks(1, 2, '1', 2);
CALL update_task_status(2, 1, 1, 1, 1);
CALL update_task_status(2, 1, 1, 0, -1);
CALL get_task_status(2, 1, 1);

CALL insert_run(1, 1, 1, '2009-06-01 10:00:00.000', 1);
CALL update_run(1, 1, 1, 1, '2009-08-01 22:32:12.045', 100000, 3);
CALL get_runs(1, 1, 1, 0, 10, 'rns_start', 'DESC', @total);
CALL delete_runs(1, 1, 1, '1,2', 0);
CALL delete_runs(1, 1, 1, '3,4', 1);

CALL insert_event(1, 1, 1, 1, '2009-06-01 10:00:00.000', 'Verbose Info', 'Country XML is retrieved', 0);
CALL get_events(1, 1, 1, 1, 0, 10, 'vnt_datetime', 'DESC', @total);
CALL delete_events(1, 1, 1, 1, 2, 3, '2009-06-01', '2009-08-01');
CALL delete_events(1, 1, 1, 1, 1, 3, '2009-06-01', '2009-08-01');
CALL delete_events(1, 1, 1, 1, 0, 3, '2009-06-01', '2009-08-01');
