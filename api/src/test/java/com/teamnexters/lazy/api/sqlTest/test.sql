CREATE DATABASE lazy_19th;

SHOW variables LIKE 'c%';

use lazy_19th;

CREATE TABLE test (
                      id bigint(20) NOT NULL AUTO_INCREMENT,
                      content varchar(255) DEFAULT NULL,
                      PRIMARY KEY (id)
) ENGINE = InnoDB;

# 한글 저장 테스트
INSERT INTO test(content) values('테스트');

SELECT * FROM test;