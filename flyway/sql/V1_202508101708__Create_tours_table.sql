CREATE TABLE `georgia_and_tours`.`tours` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` TINYTEXT NOT NULL,
  `description` LONGTEXT NOT NULL,
  `requirements` LONGTEXT NOT NULL,
  `price` VARCHAR(45) NOT NULL,
  `duration` VARCHAR(45) NOT NULL,
  `direction` MEDIUMTEXT NOT NULL,
  `imageUrl` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`id`));
