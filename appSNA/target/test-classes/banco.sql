SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Usuario` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Usuario` (
  `id_usuario` BIGINT NOT NULL ,
  `nome` VARCHAR(45) NOT NULL ,
  `screen_name` VARCHAR(20) NOT NULL ,
  `biografia` TEXT NULL ,
  `localizacao` VARCHAR(30) NULL ,
  `total_following` INT NULL ,
  `total_followers` INT NULL ,
  `total_tweets` INT NULL ,
  `URL` TEXT NULL ,
  `timezone` VARCHAR(20) NULL ,
  `linguagem` VARCHAR(20) NULL ,
  `data_criacao` DATE NULL ,
  `url_imagem` TEXT NULL ,
  PRIMARY KEY (`id_usuario`) ,
  UNIQUE INDEX `screen_name_UNIQUE` (`screen_name` ASC) )
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `mydb`.`Relacionamento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Relacionamento` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Relacionamento` (
  `id_souce` INT NOT NULL ,
  `id_target` INT NOT NULL ,
  PRIMARY KEY (`id_souce`, `id_target`) ,
  INDEX `fk_Relacionamento_Usuario2` (`id_target` ASC) ,
  CONSTRAINT `fk_Relacionamento_Usuario1`
    FOREIGN KEY (`id_souce` )
    REFERENCES `mydb`.`Usuario` (`id_usuario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Relacionamento_Usuario2`
    FOREIGN KEY (`id_target` )
    REFERENCES `mydb`.`Usuario` (`id_usuario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `mydb`.`Status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Status` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`Status` (
  `id_status` INT NOT NULL AUTO_INCREMENT ,
  `id_usuario` BIGINT NOT NULL ,
  `data_criacao` DATE NULL ,
  `texto` VARCHAR(150) NOT NULL ,
  `latitude` VARCHAR(20) NULL ,
  `longitude` VARCHAR(20) NULL ,
  `total_retweet` INT NULL ,
  `is_retweeted` INT NULL ,
  PRIMARY KEY (`id_status`, `id_usuario`) ,
  INDEX `fk_status_usuario` (`id_usuario` ASC) ,
  CONSTRAINT `fk_status_usuario`
    FOREIGN KEY (`id_usuario` )
    REFERENCES `mydb`.`Usuario` (`id_usuario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `mydb`.`URLMention`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`URLMention` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`URLMention` (
  `id_usuario` BIGINT NOT NULL ,
  `id_status` INT NOT NULL ,
  `id_urlmention` INT NOT NULL AUTO_INCREMENT ,
  `url` TEXT NULL ,
  PRIMARY KEY (`id_usuario`, `id_status`, `id_urlmention`) ,
  INDEX `fk_URLMention_Status1` (`id_status` ASC, `id_usuario` ASC) ,
  CONSTRAINT `fk_URLMention_Status1`
    FOREIGN KEY (`id_status` , `id_usuario` )
    REFERENCES `mydb`.`Status` (`id_status` , `id_usuario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `mydb`.`UserMention`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`UserMention` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`UserMention` (
  `id_usuario` BIGINT NOT NULL ,
  `id_status` INT NOT NULL ,
  `id_usermention` INT NOT NULL AUTO_INCREMENT ,
  `usuario` VARCHAR(45) NULL ,
  PRIMARY KEY (`id_usuario`, `id_status`, `id_usermention`) ,
  INDEX `fk_UserMention_Status1` (`id_status` ASC, `id_usuario` ASC) ,
  CONSTRAINT `fk_UserMention_Status1`
    FOREIGN KEY (`id_status` , `id_usuario` )
    REFERENCES `mydb`.`Status` (`id_status` , `id_usuario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
