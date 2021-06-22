SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `pdcm_sec` DEFAULT CHARACTER SET latin1 ;
USE `pdcm_sec` ;

# Drop/create users table

DROP TABLE IF EXISTS `users` ;

CREATE TABLE IF NOT EXISTS `users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `enabled` BOOLEAN NOT NULL,
  `account_non_expired` BOOLEAN NOT NULL,
  `account_non_locked` BOOLEAN NOT NULL,
  `credentials_non_expired` BOOLEAN NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_unique` (`username`),
  UNIQUE KEY `full_name_unique` (`full_name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create user_authorities table

DROP TABLE IF EXISTS `authorities` ;

CREATE TABLE IF NOT EXISTS `authorities` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `authority` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`), 
  CONSTRAINT `fk_authorities`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

DELIMITER $$
DROP PROCEDURE IF EXISTS RESET_SECURITY_DATA $$
CREATE PROCEDURE `RESET_SECURITY_DATA`()
begin
	SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE users;
INSERT INTO users VALUES (1, 'Tim Loodle', 'investigator', 'user@default.com', '$2a$10$W1gqalOZZ/MtPR9faFvJdebkXho2G1.dO/6r1bHKck9ukJceLb9ci', true, true, true, true), (2, 'Matt Quillian', 'MQuillian', 'admin@default.com', '$2a$10$Ivk0/PYY57ifhm63Q7jBmuMk5g489JFS5Sm0HwojLU/kAYAG/A55a', true, true, true, true), (3, 'Michelle Wright', 'admin', 'admin@default.com', '$2a$10$d8Z.WyaqSmiXhn5MXGGqM.7S8gkoEoli6OjA4iiCDpTuyRZ29lc2W', true, true, true, true);
TRUNCATE TABLE authorities;
INSERT INTO authorities VALUES (1, 1, 'ROLE_USER'), (2, 1, 'ROLE_INVESTIGATOR'), (3, 2, 'ROLE_ADMIN'), (4, 2, 'ROLE_ATTORNEY'), (5, 3, 'ROLE_ADMIN');
SET FOREIGN_KEY_CHECKS = 1;
end
$$
DELIMITER ;