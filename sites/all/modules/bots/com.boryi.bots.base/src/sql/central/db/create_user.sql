-- mysql -u root -p bot_central

CREATE USER 'botmaster'@'localhost' IDENTIFIED BY 'p@ssw0rd';
CREATE USER 'botuser'@'localhost' IDENTIFIED BY 'pass#word1';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON bot_central.* TO 'botmaster'@'localhost' IDENTIFIED BY 'p@ssw0rd';
GRANT SELECT, EXECUTE ON bot_central.* TO 'botuser'@'localhost' IDENTIFIED BY 'pass#word1';
