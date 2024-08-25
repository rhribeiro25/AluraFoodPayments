CREATE TABLE payments (
 id bigserial constraint pk_id_payment PRIMARY KEY,
 pay_value decimal(19,2) NOT NULL,
 pay_name varchar(100) DEFAULT NULL,
 pay_number varchar(19) DEFAULT NULL,
 pay_expiration varchar(7) DEFAULT NULL,
 pay_code varchar(3) DEFAULT NULL,
 pay_status varchar(255) NOT NULL,
 payment_method_id bigint NOT NULL,
 order_id bigint NOT NULL
);