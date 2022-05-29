DROP TABLE IF EXISTS yingshipinglun;
CREATE TABLE yingshipinglun (
                                id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                                title VARCHAR(1000) NOT NULL,
                                des TEXT(2000000),
                                `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);
