SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `pdcm_db` DEFAULT CHARACTER SET latin1 ;
USE `pdcm_db` ;

# Drop/create Investigators table

DROP TABLE IF EXISTS `investigators` ;

CREATE TABLE IF NOT EXISTS `investigators` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `working_status` INT(4) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create Attorneys table

DROP TABLE IF EXISTS `attorneys` ;

CREATE TABLE IF NOT EXISTS `attorneys` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `working_status` INT(4) NOT NULL DEFAULT '0',
  `investigator` INT(11),
  PRIMARY KEY (`id`),
  INDEX `investigator` (`investigator` ASC),
  CONSTRAINT `attorneys_ibfk_1`
    FOREIGN KEY (`investigator`)
    REFERENCES `investigators` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;

# Drop/create Clients table

DROP TABLE IF EXISTS `clients` ;

CREATE TABLE IF NOT EXISTS `clients` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `custody_status` INT(4) NULL DEFAULT '0',
  `incarceration_date` DATE NOT NULL,
  `release_date` DATE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create Judges table

DROP TABLE IF EXISTS `judges` ;

CREATE TABLE IF NOT EXISTS `judges` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `working_status` INT(4) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create Cases table

DROP TABLE IF EXISTS `cases` ;

CREATE TABLE IF NOT EXISTS `cases` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `case_number` VARCHAR(45) UNIQUE NOT NULL,
  `client` INT(11) NOT NULL,
  `date_opened` DATE NOT NULL,
  `date_closed` DATE,
  `judge` INT(11) NOT NULL,
  `attorney` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `client` (`client` ASC),
  INDEX `judge` (`judge` ASC),
  INDEX `attorney` (`attorney` ASC),
  CONSTRAINT `cases_ibfk_1`
    FOREIGN KEY (`client`)
    REFERENCES `clients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cases_ibfk_2`
    FOREIGN KEY (`judge`)
    REFERENCES `judges` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cases_ibfk_3`
    FOREIGN KEY (`attorney`)
    REFERENCES `attorneys` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create Charges table

DROP TABLE IF EXISTS `charges` ;

CREATE TABLE IF NOT EXISTS `charges` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `statute` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create Cases <-> Charges relationship table

DROP TABLE IF EXISTS `charged_counts` ;

CREATE TABLE IF NOT EXISTS `charged_counts` (
  `count_number` INT(11) NOT NULL,
  `charge` INT(11) NOT NULL,
  `court_case` INT(11) NOT NULL,
  INDEX `court_case` (`court_case` ASC),
  PRIMARY KEY (`count_number`, `court_case`),
  CONSTRAINT `counts_ibfk_1`
    FOREIGN KEY (`charge`)
    REFERENCES `charges` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `counts_ibfk_2`
	FOREIGN KEY (`court_case`)
    REFERENCES `cases` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

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

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

DELIMITER $$

DROP PROCEDURE IF EXISTS RESET_DEMO_DATA $$
CREATE PROCEDURE `RESET_DEMO_DATA`()
begin
	SET FOREIGN_KEY_CHECKS = 0;
	TRUNCATE TABLE investigators;
	INSERT INTO investigators VALUES (1, 'Tim Loodle', 0), (2, 'Sandra Sanderson', 0), (3, 'Debbie Downes', 1);
	TRUNCATE TABLE attorneys;
	INSERT INTO attorneys VALUES (1, 'Matt Quillian', 0, 1), (2, 'John Doe', 0, 2), (3, 'Jane Smith', 1, NULL), (4, 'Matt Schneider', 1, NULL);
	TRUNCATE TABLE clients;
	INSERT INTO clients VALUES (1, 'Eric Hoefle', 0, '2018-07-16', NULL), (2, 'Jason Baddorf', 0, '2018-05-14', NULL), (3, 'Jamie Jameson', 1, '2018-08-23', '2018-08-25'), (4, 'Marky Mark', 1, '2017-10-17', '2017-11-17'), (5, 'Steven McButton', 0, '2017-08-16', NULL), (6, 'Atticus Finch', 1, '2018-06-07', '2018-06-09'), (7, 'Erica Erickson', 0, '2018-07-16', NULL), (8, 'Franklin Fish', 0, '2018-03-29', NULL), (9, 'James Fallon', 0, '2017-07-20', NULL), (10,'Hamilton Holmes', 1, '2018-11-05', '2018-11-20');
	TRUNCATE TABLE judges;
	INSERT INTO judges VALUES (1, 'Horace Johnson', 0), (2, 'John Mott', 0);
	TRUNCATE TABLE cases;
	INSERT INTO cases VALUES (1, '21J1614', 1, '2021-7-16', NULL, 1, 1), (2, '20J1417', 2, '2020-5-14', NULL, 2, 2), (3, '21J1714', 3, '2021-8-23', NULL, 1, 1), (4, '20J2837', 4, '2020-10-17', NULL, 2, 2), (5, '20J1723', 5, '2020-8-16', NULL, 2, 2), (6, '21J1792', 6, '2021-8-20', '2021-9-17', 1, 1), (7, '21J1729', 6, '2021-6-7', '2021-10-20', 1, 1), (8, '20J2817', 7, '2020-7-16', '2021-1-8', 2, 2), (9, '21J2184', 7, '2021-7-16', '2022-2-5', 2, 1), (10, '18J4761', 8, '2018-3-29', NULL, 1, 2), (11, '19J7424', 9, '2019-7-20', NULL, 2, 2), (12, '18J8516', 10, '2018-11-5', NULL, 1, 1), (13, '22J0021', 1, '2022-01-11', NULL, 1, 1);
	TRUNCATE TABLE charges;
	INSERT INTO charges VALUES (1, 'Simple battery', '16-5-23'), (2, 'Battery', '16-5-23.1'), (3, 'Theft by taking', '16-8-2'),(4, 'Driving while license suspended', '40-5-21'), (5, 'Possession of controlled substance', '16-13-30');
	TRUNCATE TABLE charged_counts;
	INSERT INTO charged_counts VALUES (1, 1, 1), (1, 3, 2), (1, 4, 3), (2, 5, 3), (1, 3, 4), (1, 2, 5), (1, 4, 6), (1, 1, 7), (1, 5, 8), (1, 2, 9), (1, 1, 10), (1, 3, 11), (1, 5, 12), (1, 4, 13);
	SET FOREIGN_KEY_CHECKS = 1;
end
$$

DROP PROCEDURE IF EXISTS CALC_OFFICE_STATS $$
CREATE PROCEDURE `CALC_OFFICE_STATS`(
	OUT totalCases int,
    OUT inCustody int,
    OUT totalAvg double,
    OUT inCustodyAvg double)
begin
	DROP TABLE IF EXISTS `open_cases`;
	CREATE TEMPORARY TABLE open_cases
	SELECT date_opened
	FROM cases
	WHERE date_closed IS NULL;

	DROP TABLE IF EXISTS `open_incustody_cases`;
	CREATE TEMPORARY TABLE open_incustody_cases
	SELECT date_opened
	FROM cases INNER JOIN clients
	ON cases.client = clients.id
	WHERE date_closed IS NULL AND clients.custody_status = 0;


	SELECT COUNT(*) INTO totalCases FROM open_cases;

	SELECT COUNT(*) INTO inCustody FROM open_incustody_cases;

	SELECT AVG(DATEDIFF(CURRENT_DATE(),date_opened)) INTO totalAvg
	FROM open_cases;

	SELECT AVG(DATEDIFF(CURRENT_DATE(),date_opened)) INTO inCustodyAvg
	FROM open_incustody_cases;
end
$$

DROP PROCEDURE IF EXISTS RESET_SECURITY_DATA $$
CREATE PROCEDURE `RESET_SECURITY_DATA`()
begin
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE users;
INSERT INTO users VALUES (1, 'Tim Loodle', 'investigator', 'user@default.com', '$2a$10$W1gqalOZZ/MtPR9faFvJdebkXho2G1.dO/6r1bHKck9ukJceLb9ci', true, true, true, true), (2, 'Matt Quillian', 'attorney', 'atty@default.com', '$2a$10$HB50Wr0UCWhv2Vm0d/sv7eJ0TOGTU.ABSjVyWMULVFX0d9OuDIxUK', true, true, true, true), (3, 'Michelle Wright', 'admin', 'admin@default.com', '$2a$10$d8Z.WyaqSmiXhn5MXGGqM.7S8gkoEoli6OjA4iiCDpTuyRZ29lc2W', true, true, true, true);
TRUNCATE TABLE authorities;
INSERT INTO authorities VALUES (1, 1, 'ROLE_USER'), (2, 1, 'ROLE_INVESTIGATOR'), (3, 2, 'ROLE_ADMIN'), (4, 2, 'ROLE_ATTORNEY'), (5, 3, 'ROLE_ADMIN');
SET FOREIGN_KEY_CHECKS = 1;
end
$$
DELIMITER ;

CALL RESET_DEMO_DATA;
CALL RESET_SECURITY_DATA;

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON *.* TO '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD';
GRANT SELECT ON mysql.proc TO '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD';
