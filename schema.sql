DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test`
(
    `id`   int(40)     NOT NULL DEFAULT 0 PRIMARY KEY,
    `name` VARCHAR(11) NOT NULL
) ENGINE = innodb
  DEFAULT CHARSET = utf8;