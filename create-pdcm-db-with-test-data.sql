SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `walton_public_defender` DEFAULT CHARACTER SET latin1 ;
USE `walton_public_defender` ;

# Drop/create Attorneys table

DROP TABLE IF EXISTS `attorneys` ;

CREATE TABLE IF NOT EXISTS `attorneys` (
  `attorney_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `employment_status` INT(4) NOT NULL DEFAULT '0',
  `FK_investigator` INT(4) NOT NULL,
  PRIMARY KEY (`attorney_id`),
  INDEX `FK_investigator` (`FK_investigator` ASC),
  CONSTRAINT `attorneys_ibfk_1`
    FOREIGN KEY (`FK_investigator`)
    REFERENCES `investigators`(`investigator_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;

DROP TABLE IF EXISTS `clients` ;

# Drop/create Clients table

CREATE TABLE IF NOT EXISTS `clients` (
  `client_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `custody_status` INT(4) NULL DEFAULT '0',
  PRIMARY KEY (`client_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create Judges table

DROP TABLE IF EXISTS `judges` ;

CREATE TABLE IF NOT EXISTS `judges` (
  `judge_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`judge_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

DROP TABLE IF EXISTS `cases` ;

# Drop/create Cases table

CREATE TABLE IF NOT EXISTS `cases` (
  `case_id` INT(11) NOT NULL AUTO_INCREMENT,
  `case_number` VARCHAR(45) UNIQUE NOT NULL,
  `FK_client` INT(11) NOT NULL,
  `FK_judge` INT(11) NOT NULL,
  `FK_attorney` INT(11) NOT NULL,
  PRIMARY KEY (`case_id`),
  INDEX `FK_client` (`FK_client` ASC),
  INDEX `FK_judge` (`FK_judge` ASC),
  INDEX `FK_attorney` (`FK_attorney` ASC),
  CONSTRAINT `cases_ibfk_1`
    FOREIGN KEY (`FK_client`)
    REFERENCES `clients` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cases_ibfk_2`
    FOREIGN KEY (`FK_judge`)
    REFERENCES `judges` (`judge_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cases_ibfk_3`
    FOREIGN KEY (`FK_attorney`)
    REFERENCES `attorneys` (`attorney_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create Charges table

DROP TABLE IF EXISTS `charges` ;

CREATE TABLE IF NOT EXISTS `charges` (
  `charge_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `statute` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`charge_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create Cases <-> Charges relationship table

DROP TABLE IF EXISTS `charged_counts` ;

CREATE TABLE IF NOT EXISTS `charged_counts` (
  `FK_case` INT(11) NOT NULL,
  `FK_charge` INT(11) NOT NULL,
  PRIMARY KEY (`FK_case`, `FK_charge`),
  INDEX `FK_charge` (`FK_charge` ASC),
  CONSTRAINT `charged_counts_ibfk_1`
    FOREIGN KEY (`FK_case`)
    REFERENCES `cases` (`case_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `charged_counts_ibfk_2`
    FOREIGN KEY (`FK_charge`)
    REFERENCES `charges` (`charge_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create Investigators table

DROP TABLE IF EXISTS `investigators` ;

CREATE TABLE IF NOT EXISTS `investigators` (
  `investigator_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`investigator_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Insert investigators

INSERT INTO investigators (name)
	VALUES
    ('Tim Loodle'),
    ('Sandra Sanderson'),
    ('INACTIVE');

# Insert attorneys

INSERT INTO attorneys (name, employment_status, FK_investigator)
	VALUES
    ('Matt Quillian', 0,
		(SELECT investigator_id FROM investigators WHERE name = 'Tim Loodle')),
    ('John Doe', 0,
		(SELECT investigator_id FROM investigators WHERE name = 'Sandra Sanderson')),
    ('Jane Smith', 1,
		(SELECT investigator_id FROM investigators WHERE name = 'INACTIVE')),
    ('Matt Schneider', 1,
		(SELECT investigator_id FROM investigators WHERE name = 'INACTIVE'));

# Insert clients

INSERT INTO clients (name, custody_status)
	VALUES
    ('Eric Hoefle', 0),
    ('Jason Baddorf', 0),
    ('Jamie Jameson', 1),
    ('Marky Mark', 1),
    ('Phteven McButton', 0),
    ('Atticus Finch', 1),
    ('Erica Erickson', 0),
    ('Fblthp NLN', 0),
    ('Jimmy Fallon', 0),
    ('Hamilton Holmes', 1);

# Insert judges
	
INSERT INTO judges (name)
	VALUES
    ('Horace Johnson'),
    ('John Mott');
    
# Insert cases

INSERT INTO cases (case_number, FK_client, FK_judge, FK_attorney)
	VALUES
    ('18J161450', 
		(SELECT client_id FROM clients WHERE name = 'Eric Hoefle'),
        (SELECT judge_id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT attorney_id FROM attorneys WHERE name = 'Matt Quillian')
	),
    ('18J141738', 
		(SELECT client_id FROM clients WHERE name = 'Jason Baddorf'),
        (SELECT judge_id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT attorney_id FROM attorneys WHERE name = 'John Doe')
	),
    ('18J171429', 
		(SELECT client_id FROM clients WHERE name = 'Jamie Jameson'),
        (SELECT judge_id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT attorney_id FROM attorneys WHERE name = 'Matt Quillian')
	),
    ('17J283757', 
		(SELECT client_id FROM clients WHERE name = 'Marky Mark'),
        (SELECT judge_id FROM judges WHERE name = 'John Mott'),
        (SELECT attorney_id FROM attorneys WHERE name = 'John Doe')
	),
    ('17J172365', 
		(SELECT client_id FROM clients WHERE name = 'Phteven McButton'),
        (SELECT judge_id FROM judges WHERE name = 'John Mott'),
        (SELECT attorney_id FROM attorneys WHERE name = 'John Doe')
	),
    ('18J179254', 
		(SELECT client_id FROM clients WHERE name = 'Atticus Finch'),
        (SELECT judge_id FROM judges WHERE name = 'John Mott'),
        (SELECT attorney_id FROM attorneys WHERE name = 'Matt Quillian')
	),
    ('18J172979', 
		(SELECT client_id FROM clients WHERE name = 'Jamie Jameson'),
        (SELECT judge_id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT attorney_id FROM attorneys WHERE name = 'Matt Quillian')
	),
    ('17J281737', 
		(SELECT client_id FROM clients WHERE name = 'Erica Erickson'),
        (SELECT judge_id FROM judges WHERE name = 'John Mott'),
        (SELECT attorney_id FROM attorneys WHERE name = 'John Doe')
	),
    ('18J218436', 
		(SELECT client_id FROM clients WHERE name = 'Erica Erickson'),
        (SELECT judge_id FROM judges WHERE name = 'John Mott'),
        (SELECT attorney_id FROM attorneys WHERE name = 'John Doe')
	),
    ('18J476421', 
		(SELECT client_id FROM clients WHERE name = 'Fblthp NLN'),
        (SELECT judge_id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT attorney_id FROM attorneys WHERE name = 'Matt Quillian')
	),
    ('17J742874', 
		(SELECT client_id FROM clients WHERE name = 'Jimmy Fallon'),
        (SELECT judge_id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT attorney_id FROM attorneys WHERE name = 'John Doe')
	),
    ('18J851936', 
		(SELECT client_id FROM clients WHERE name = 'Hamilton Holmes'),
        (SELECT judge_id FROM judges WHERE name = 'John Mott'),
        (SELECT attorney_id FROM attorneys WHERE name = 'Matt Quillian')
	);
    

# Insert charges

INSERT INTO charges (name, statute)
	VALUES
    ('Simple battery', '16-5-23.1'),
    ('Battery', '16-5-23.1'),
    ('Theft by taking', '16-8-2'),
    ('Driving while license suspended', '40-5-21'),
    ('Possession of controlled substance', '16-13-30');

# Insert charged counts

INSERT INTO charged_counts (FK_case, FK_charge)
	VALUES
    ((SELECT case_id FROM cases WHERE case_number = '18J161450'),
		(SELECT charge_id FROM charges WHERE name = 'Battery')
	),
    ((SELECT case_id FROM cases WHERE case_number = '18J161450'),
		(SELECT charge_id FROM charges WHERE name = 'Simple battery')
	),
    ((SELECT case_id FROM cases WHERE case_number = '18J171429'),
		(SELECT charge_id FROM charges WHERE name = 'Theft by taking')
	),
    ((SELECT case_id FROM cases WHERE case_number = '17J283757'),
		(SELECT charge_id FROM charges WHERE name = 'Driving while license suspended')
	),
    ((SELECT case_id FROM cases WHERE case_number = '17J283757'),
		(SELECT charge_id FROM charges WHERE name = 'Possession of controlled substance')
	),
    ((SELECT case_id FROM cases WHERE case_number = '17J172365'),
		(SELECT charge_id FROM charges WHERE name = 'Theft by taking')
	),
    ((SELECT case_id FROM cases WHERE case_number = '18J179254'),
		(SELECT charge_id FROM charges WHERE name = 'Battery')
	),
    ((SELECT case_id FROM cases WHERE case_number = '18J172979'),
		(SELECT charge_id FROM charges WHERE name = 'Driving while license suspended')
	),
    ((SELECT case_id FROM cases WHERE case_number = '17J281737'),
		(SELECT charge_id FROM charges WHERE name = 'Simple battery')
	),
    ((SELECT case_id FROM cases WHERE case_number = '18J218436'),
		(SELECT charge_id FROM charges WHERE name = 'Possession of controlled substance')
	),
    ((SELECT case_id FROM cases WHERE case_number = '18J476421'),
		(SELECT charge_id FROM charges WHERE name = 'Battery')
	),
    ((SELECT case_id FROM cases WHERE case_number = '17J742874'),
		(SELECT charge_id FROM charges WHERE name = 'Simple battery')
	),
    ((SELECT case_id FROM cases WHERE case_number = '18J851936'),
		(SELECT charge_id FROM charges WHERE name = 'Theft by taking')
	);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
