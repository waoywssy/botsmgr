-- mysql -u root -p bot_local

CALL insert_checkitem(2, 1, 1, 'https://www.amazon.com/', 1, 1, 1);
CALL insert_checkitem(2, 1, 1, 'https://www.amazon.com/books/', 2, 2, 1);
CALL insert_checkitem(2, 1, 1, 'https://www.amazon.com/musics/', 2, 3, 1);
CALL insert_checkitem(2, 1, 1, 'https://www.amazon.com/laptops/', 2, 4, 1);
CALL insert_checkitem(2, 1, 1, 'https://www.amazon.com/mobiles/', 2, 5, 1);
CALL update_checkingitem(2, 1, 1, 'https://www.amazon.com/');
CALL update_checkingitem(2, 1, 1, 'https://www.amazon.com/books/');
CALL update_checkeditem(2, 1, 1, 'https://www.amazon.com/');
CALL get_checklist(2, 1, 1, 1);
CALL get_checkinglist(2, 1, 1, 1);
CALL clear_checklist(2, 1, 1, 1);
CALL update_checklist_status(1, -1);

CALL get_resumed_run(2, 1, 1);
CALL update_decheckingitems(2, 1, 1);

CALL insert_bot_run(8, 1, 1, 1, '2009-09-01 01:03:00.000');
CALL insert_bot_run(8, 1, 1, 2, '2009-09-02 01:03:00.000');
CALL insert_bot_run(8, 1, 2, 1, '2009-09-01 01:03:00.000');
CALL insert_bot_run(8, 2, 1, 1, '2009-09-01 01:03:00.000');
CALL insert_bot_run(8, 2, 2, 1, '2009-09-01 01:03:00.000');

CALL insert_web_statistic(4, 0, '2009-09-05 01:01:23.234', '2009-09-06 20:34:32.943', 7.82, 7.24, 21313, 44325, 345, 23421, 345, 23454, 42345456, 3000, 4000, 1);
CALL insert_web_statistic(5, 0, '2009-09-05 11:01:23.234', '2009-09-07 08:34:32.943', 7.82, 7.24, 21313, 44325, 345, 23421, 345, 23454, 42345456, 3000, 4000, 1);

CALL insert_qa_statistic(10, 23454, 23424, 0, 324.21, 234.23, 6423.00);
CALL insert_qa_statistic(11, 23454, 23424, 0, 324.21, 234.23, 6423.00);
CALL update_qa_statistic(10, 1);
CALL update_qa_statistic(11, 0);
CALL delete_qa_statistic(11);
