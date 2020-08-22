-- create databases
CREATE DATABASE IF NOT EXISTS testdb;

-- create user and grant rights
CREATE USER 'testuser'@'%' IDENTIFIED BY 'testpass';
GRANT ALL ON testdb.* TO 'testuser'@'%';
