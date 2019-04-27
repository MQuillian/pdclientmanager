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
  `employment_status` INT(4) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create Attorneys table

DROP TABLE IF EXISTS `attorneys` ;

CREATE TABLE IF NOT EXISTS `attorneys` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `employment_status` INT(4) NOT NULL DEFAULT '0',
  `investigator_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `investigator_id` (`investigator_id` ASC),
  CONSTRAINT `attorneys_ibfk_1`
    FOREIGN KEY (`investigator_id`)
    REFERENCES `investigators` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;

DROP TABLE IF EXISTS `clients` ;

# Drop/create Clients table

CREATE TABLE IF NOT EXISTS `clients` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `custody_status` INT(4) NULL DEFAULT '0',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

# Drop/create Judges table

DROP TABLE IF EXISTS `judges` ;

CREATE TABLE IF NOT EXISTS `judges` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

DROP TABLE IF EXISTS `cases` ;

# Drop/create Cases table

CREATE TABLE IF NOT EXISTS `cases` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `case_number` VARCHAR(45) UNIQUE NOT NULL,
  `client_id` INT(11) NOT NULL,
  `judge_id` INT(11) NOT NULL,
  `attorney_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `client_id` (`client_id` ASC),
  INDEX `judge_id` (`judge_id` ASC),
  INDEX `attorney_id` (`attorney_id` ASC),
  CONSTRAINT `cases_ibfk_1`
    FOREIGN KEY (`client_id`)
    REFERENCES `clients` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cases_ibfk_2`
    FOREIGN KEY (`judge_id`)
    REFERENCES `judges` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cases_ibfk_3`
    FOREIGN KEY (`attorney_id`)
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
  `case_id` INT(11) NOT NULL,
  `charge_id` INT(11) NOT NULL,
  PRIMARY KEY (`case_id`, `charge_id`),
  INDEX `charge_id` (`charge_id` ASC),
  CONSTRAINT `charged_counts_ibfk_1`
    FOREIGN KEY (`case_id`)
    REFERENCES `cases` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `charged_counts_ibfk_2`
    FOREIGN KEY (`charge_id`)
    REFERENCES `charges` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

#-------------------------------------
#TEST DATA
#-------------------------------------

# Insert investigators

INSERT INTO investigators (name, employment_status)
	VALUES
    ('Tim Loodle', 0),
    ('Sandra Sanderson', 0);

# Insert attorneys

INSERT INTO attorneys (name, employment_status, investigator_id)
	VALUES
    ('Matt Quillian', 0,
		(SELECT id FROM investigators WHERE name = 'Tim Loodle')),
    ('John Doe', 0,
		(SELECT id FROM investigators WHERE name = 'Sandra Sanderson')),
    ('Jane Smith', 1,
		(SELECT id FROM investigators WHERE name = 'Tim Loodle')),
    ('Matt Schneider', 1,
		(SELECT id FROM investigators WHERE name = 'Sandra Sanderson'));

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

INSERT INTO cases (case_number, client_id, judge_id, attorney_id)
	VALUES
    ('18J161450', 
		(SELECT id FROM clients WHERE name = 'Eric Hoefle'),
        (SELECT id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT id FROM attorneys WHERE name = 'Matt Quillian')
	),
    ('18J141738', 
		(SELECT id FROM clients WHERE name = 'Jason Baddorf'),
        (SELECT id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT id FROM attorneys WHERE name = 'John Doe')
	),
    ('18J171429', 
		(SELECT id FROM clients WHERE name = 'Jamie Jameson'),
        (SELECT id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT id FROM attorneys WHERE name = 'Matt Quillian')
	),
    ('17J283757', 
		(SELECT id FROM clients WHERE name = 'Marky Mark'),
        (SELECT id FROM judges WHERE name = 'John Mott'),
        (SELECT id FROM attorneys WHERE name = 'John Doe')
	),
    ('17J172365', 
		(SELECT id FROM clients WHERE name = 'Phteven McButton'),
        (SELECT id FROM judges WHERE name = 'John Mott'),
        (SELECT id FROM attorneys WHERE name = 'John Doe')
	),
    ('18J179254', 
		(SELECT id FROM clients WHERE name = 'Atticus Finch'),
        (SELECT id FROM judges WHERE name = 'John Mott'),
        (SELECT id FROM attorneys WHERE name = 'Matt Quillian')
	),
    ('18J172979', 
		(SELECT id FROM clients WHERE name = 'Jamie Jameson'),
        (SELECT id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT id FROM attorneys WHERE name = 'Matt Quillian')
	),
    ('17J281737', 
		(SELECT id FROM clients WHERE name = 'Erica Erickson'),
        (SELECT id FROM judges WHERE name = 'John Mott'),
        (SELECT id FROM attorneys WHERE name = 'John Doe')
	),
    ('18J218436', 
		(SELECT id FROM clients WHERE name = 'Erica Erickson'),
        (SELECT id FROM judges WHERE name = 'John Mott'),
        (SELECT id FROM attorneys WHERE name = 'John Doe')
	),
    ('18J476421', 
		(SELECT id FROM clients WHERE name = 'Fblthp NLN'),
        (SELECT id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT id FROM attorneys WHERE name = 'Matt Quillian')
	),
    ('17J742874', 
		(SELECT id FROM clients WHERE name = 'Jimmy Fallon'),
        (SELECT id FROM judges WHERE name = 'Horace Johnson'),
        (SELECT id FROM attorneys WHERE name = 'John Doe')
	),
    ('18J851936', 
		(SELECT id FROM clients WHERE name = 'Hamilton Holmes'),
        (SELECT id FROM judges WHERE name = 'John Mott'),
        (SELECT id FROM attorneys WHERE name = 'Matt Quillian')
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

INSERT INTO charged_counts (case_id, charge_id)
	VALUES
    ((SELECT id FROM cases WHERE case_number = '18J161450'),
		(SELECT id FROM charges WHERE name = 'Battery')
	),
    ((SELECT id FROM cases WHERE case_number = '18J161450'),
		(SELECT id FROM charges WHERE name = 'Simple battery')
	),
    ((SELECT id FROM cases WHERE case_number = '18J171429'),
		(SELECT id FROM charges WHERE name = 'Theft by taking')
	),
    ((SELECT id FROM cases WHERE case_number = '17J283757'),
		(SELECT id FROM charges WHERE name = 'Driving while license suspended')
	),
    ((SELECT id FROM cases WHERE case_number = '17J283757'),
		(SELECT id FROM charges WHERE name = 'Possession of controlled substance')
	),
    ((SELECT id FROM cases WHERE case_number = '17J172365'),
		(SELECT id FROM charges WHERE name = 'Theft by taking')
	),
    ((SELECT id FROM cases WHERE case_number = '18J179254'),
		(SELECT id FROM charges WHERE name = 'Battery')
	),
    ((SELECT id FROM cases WHERE case_number = '18J172979'),
		(SELECT id FROM charges WHERE name = 'Driving while license suspended')
	),
    ((SELECT id FROM cases WHERE case_number = '17J281737'),
		(SELECT id FROM charges WHERE name = 'Simple battery')
	),
    ((SELECT id FROM cases WHERE case_number = '18J218436'),
		(SELECT id FROM charges WHERE name = 'Possession of controlled substance')
	),
    ((SELECT id FROM cases WHERE case_number = '18J476421'),
		(SELECT id FROM charges WHERE name = 'Battery')
	),
    ((SELECT id FROM cases WHERE case_number = '17J742874'),
		(SELECT id FROM charges WHERE name = 'Simple battery')
	),
    ((SELECT id FROM cases WHERE case_number = '18J851936'),
		(SELECT id FROM charges WHERE name = 'Theft by taking')
	);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
