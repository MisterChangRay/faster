
---- 创建财务账户表
CREATE TABLE `faster_financial_account` (
  `id` char(32) NOT NULL,
  `user_id` char(32) DEFAULT NULL,
  `name` char(32) DEFAULT NULL,
  `phone` char(32) DEFAULT NULL,
  `balance` decimal(20,5) DEFAULT '0.00000',
  `freeze` decimal(20,5) DEFAULT '0.00000',
  `status` int DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time_status_index` (`create_time`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='财务账户表'



-- 财务账户变动详情
create table faster_financial_changes_record
(
    id                   char(32)       not null primary key,
    serial_number        char(32)       null,
    financial_account_Id char(32)       null,
    books_id             int            null,
    user_id              char(32)       null,
    name                 char(32)       null,
    phone                char(32)       null,
    amount               decimal(20, 5) null,
    after_balance        decimal(20, 5) null,
    after_freeze         decimal(20, 5) null,
    sourceId             char(32)       null,
    type                 int            null,
    status               int            null,
    remark               char(32)       null,
    create_time          datetime       not null,
    update_time          datetime       not null
) comment '财务账户变动详情';


