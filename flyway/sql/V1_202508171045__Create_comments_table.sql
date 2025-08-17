CREATE TABLE `georgia_and_tours`.`comments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tour_id` INT NOT NULL,
  `name` TINYTEXT NOT NULL,
  `date` VARCHAR(45) NOT NULL,
  `payload` LONGTEXT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `tourId_idx` (`tour_id` ASC) VISIBLE,
  CONSTRAINT `tourId`
    FOREIGN KEY (`tour_id`)
    REFERENCES `georgia_and_tours`.`tours` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
