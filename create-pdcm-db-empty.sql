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

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;