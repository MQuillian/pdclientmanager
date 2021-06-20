SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `walton_public_defender` DEFAULT CHARACTER SET latin1 ;
USE `walton_public_defender` ;

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

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE investigators;
INSERT INTO investigators VALUES (1, 'Tim Loodle', 0), (2, 'Sandra Sanderson', 0), (3, 'Debbie Downer', 1);
TRUNCATE TABLE attorneys;
INSERT INTO attorneys VALUES (1, 'Matt Quillian', 0, 1), (2, 'John Doe', 0, 2), (3, 'Jane Smith', 1, NULL), (4, 'Matt Schneider', 1, NULL), (5, 'NO CASELOAD', 0, 1), (6, 'NO CASELOAD', 0, NULL);
TRUNCATE TABLE clients;
INSERT INTO clients VALUES (1, 'Eric Hoefle', 0, '2018-07-16', NULL), (2, 'Jason Baddorf', 0, '2018-05-14', NULL), (3, 'Jamie Jameson', 1, '2018-08-23', '2018-08-25'), (4, 'Marky Mark', 1, '2017-10-17', '2017-11-17'), (5, 'Phteven McButton', 0, '2017-08-16', NULL), (6, 'Atticus Finch', 1, '2018-06-07', '2018-06-09'), (7, 'Erica Erickson', 0, '2018-07-16', NULL), (8, 'Fblthp NLN', 0, '2018-03-29', NULL), (9, 'Jimmy Fallon', 0, '2017-07-20', NULL), (10,'Hamilton Holmes', 1, '2018-11-05', '2018-11-20');
TRUNCATE TABLE judges;
INSERT INTO judges VALUES (1, 'Horace Johnson', 0), (2, 'John Mott', 0), (3, 'Retired Judge', 1);
TRUNCATE TABLE cases;
INSERT INTO cases VALUES (1, '18J1614', 1, '2018-7-16', NULL, 1, 1), (2, '18J1417', 2, '2018-5-14', NULL, 2, 2), (3, '18J1714', 3, '2018-8-23', NULL, 1, 1), (4, '17J2837', 4, '2017-10-17', NULL, 2, 2), (5, '17J1723', 5, '2017-8-16', NULL, 2, 2), (6, '18J1792', 6, '2018-8-20', '2018-9-17', 1, 1), (7, '18J1729', 6, '2018-6-7', '2018-10-20', 1, 1), (8, '17J2817', 7, '2018-7-16', '2018-1-8', 2, 2), (9, '18J2184', 7, '2018-7-16', '2019-2-5', 2, 1), (10, '18J4761', 8, '2018-3-29', NULL, 1, 2), (11, '17J7424', 9, '2017-7-20', NULL, 2, 2), (12, '18J8516', 10, '2018-11-5', NULL, 1, 1);
TRUNCATE TABLE charges;
INSERT INTO charges VALUES (1, 'Simple battery', '16-5-23'), (2, 'Battery', '16-5-23.1'), (3, 'Theft by taking', '16-8-2'),(4, 'Driving while license suspended', '40-5-21'), (5, 'Possession of controlled substance', '16-13-30');
TRUNCATE TABLE charged_counts;
INSERT INTO charged_counts VALUES (1, 1, 1), (1, 3, 2), (1, 4, 3), (2, 5, 3), (1, 3, 4), (1, 2, 5), (1, 4, 6), (1, 1, 7), (1, 5, 8), (1, 2, 9), (1, 1, 10), (1, 3, 11), (1, 5, 12);
SET FOREIGN_KEY_CHECKS = 1;