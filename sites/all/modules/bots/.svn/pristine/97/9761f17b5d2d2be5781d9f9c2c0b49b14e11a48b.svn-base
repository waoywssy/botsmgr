-- mysql -u root -p bot_local

CREATE USER 'botmaster_local'@'localhost' IDENTIFIED BY 'p@ssw0rd';
CREATE USER 'botuser_local'@'localhost' IDENTIFIED BY 'pass#word1';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON bot_local.* TO 'botmaster_local'@'localhost' IDENTIFIED BY 'p@ssw0rd';
GRANT SELECT, EXECUTE ON bot_local.* TO 'botuser_local'@'localhost' IDENTIFIED BY 'pass#word1';
