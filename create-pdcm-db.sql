SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `walton_public_defender` DEFAULT CHARACTER SET latin1 ;
USE `walton_public_defender` ;

DROP TABLE IF EXISTS `attorneys` ;

CREATE TABLE IF NOT EXISTS `attorneys` (
  `attorney_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `employment_status` INT(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`attorney_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;

DROP TABLE IF EXISTS `clients` ;

CREATE TABLE IF NOT EXISTS `clients` (
  `client_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `in_custody` INT(4) NULL DEFAULT '0',
  `FK_attorney` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`),
  INDEX `FK_attorney` (`FK_attorney` ASC),
  CONSTRAINT `clients_ibfk_1`
    FOREIGN KEY (`FK_attorney`)
    REFERENCES `attorneys` (`attorney_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

DROP TABLE IF EXISTS `judges` ;

CREATE TABLE IF NOT EXISTS `judges` (
  `judge_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`judge_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

DROP TABLE IF EXISTS `cases` ;

CREATE TABLE IF NOT EXISTS `cases` (
  `case_id` INT(11) NOT NULL AUTO_INCREMENT,
  `case_number` VARCHAR(45) NOT NULL,
  `FK_client` INT(11) NULL DEFAULT NULL,
  `FK_judge` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`case_id`),
  INDEX `FK_client` (`FK_client` ASC),
  INDEX `FK_judge` (`FK_judge` ASC),
  CONSTRAINT `cases_ibfk_1`
    FOREIGN KEY (`FK_client`)
    REFERENCES `clients` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cases_ibfk_2`
    FOREIGN KEY (`FK_judge`)
    REFERENCES `judges` (`judge_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

DROP TABLE IF EXISTS `charges` ;

CREATE TABLE IF NOT EXISTS `charges` (
  `charge_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `statute` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`charge_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

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

DROP TABLE IF EXISTS `investigators` ;

CREATE TABLE IF NOT EXISTS `investigators` (
  `investigator_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `FK_attorney` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`investigator_id`),
  INDEX `FK_attorney` (`FK_attorney` ASC),
  CONSTRAINT `investigators_ibfk_1`
    FOREIGN KEY (`FK_attorney`)
    REFERENCES `attorneys` (`attorney_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
