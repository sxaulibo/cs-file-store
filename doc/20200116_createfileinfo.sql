CREATE DATABASE fileInfo;

USE fileInfo;

CREATE TABLE IF NOT EXISTS `fileInfo_tbl`(
   `fileInfo_id` INT UNSIGNED AUTO_INCREMENT,
   `fileInfo_fileCode` VARCHAR(40) NOT NULL,
   `fileInfo_fileName` VARCHAR(40) NOT NULL,
   `fileInfo_path` VARCHAR(40) NOT NULL,
   PRIMARY KEY ( `fileInfo_id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;