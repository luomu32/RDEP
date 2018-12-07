CREATE TABLE `rdep_project`.`project` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL COMMENT '名称',
  `ciUrl` VARCHAR(45) NULL COMMENT '持续集成的URL',
  PRIMARY KEY (`id`));
