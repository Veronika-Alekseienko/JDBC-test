CREATE SCHEMA `manufacturers`;
CREATE TABLE `manufacturer_db`.`manufacturer`
(
    `id`         BIGINT(0) NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(45) NULL,
    `country`    VARCHAR(45) NULL,
    `is_deleted` TINYINT(1) NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

