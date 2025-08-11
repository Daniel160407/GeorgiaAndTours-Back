CREATE TABLE `georgia_and_tours`.`messages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sender_email` TINYTEXT NOT NULL,
  `receiver_email` TINYTEXT NOT NULL,
  `sender` TINYTEXT NOT NULL,
  `receiver` TINYTEXT NOT NULL,
  `payload` LONGTEXT NOT NULL,
  PRIMARY KEY (`id`));
