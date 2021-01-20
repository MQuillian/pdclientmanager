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
