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
  `investigator` INT(11) NOT NULL,
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

# Drop/create Cases table

DROP TABLE IF EXISTS `cases` ;

CREATE TABLE IF NOT EXISTS `cases` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `case_number` VARCHAR(45) UNIQUE NOT NULL,
  `client` INT(11) NOT NULL,
  `case_status` INT(4) NOT NULL DEFAULT '0',
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
  `case` INT(11) NOT NULL,
  `charge` INT(11) NOT NULL,
  PRIMARY KEY (`case`, `charge`),
  INDEX `charge` (`charge` ASC),
  CONSTRAINT `charged_counts_ibfk_1`
    FOREIGN KEY (`case`)
    REFERENCES `cases` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `charged_counts_ibfk_2`
    FOREIGN KEY (`charge`)
    REFERENCES `charges` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;