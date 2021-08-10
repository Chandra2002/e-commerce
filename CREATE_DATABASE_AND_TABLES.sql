CREATE DATABASE IF NOT EXISTS `E_COMMERCE`;
CREATE TABLE IF NOT EXISTS `E_COMMERCE`.`USER_DETAILS` (
		user_id bigint not null auto_increment,
        `name` varchar(255) default null,
        `email` varchar(255) default null,
        `mobile_no` varchar(10) default null,
        `balance` double default 0.0,
        `user_address` json default null,
        primary key (user_id));
CREATE TABLE IF NOT EXISTS `E_COMMERCE`.`USER_LOGIN_DETAILS` (
		user_id bigint not null auto_increment,
        user_name varchar(255) default null,
        password varchar(255) default null,
        foreign key (user_id) references USER_DETAILS(user_id));
CREATE TABLE IF NOT EXISTS `E_COMMERCE`.`PRODUCT_TYPE_DETAILS` (
		product_type_id bigint not null auto_increment,
        product_type_name varchar(255) default null,
        gender enum("MALE", "FEMALE", "UNISEX"), 
        primary key (product_type_id));
CREATE TABLE IF NOT EXISTS `E_COMMERCE`.`PRODUCT` (
		product_id bigint not null auto_increment,
        product_name varchar(255) default null,
        cost double default 0.0,
        quantity integer default 0,
        available_delivery_list json default null,
        discount integer default 0, 
        primary key (product_id));
CREATE TABLE IF NOT EXISTS `E_COMMERCE`.`PRODUCT_CATEGORY` (
		product_id bigint not null,
        product_type_id bigint not null,
        product_category enum("<10000", "<100000", ">100000"),
        foreign key (product_id) references PRODUCT(product_id),
        foreign key (product_type_id) references PRODUCT_TYPE_DETAILS(product_type_id));
CREATE TABLE IF NOT EXISTS `E_COMMERCE`.`CART` (
		product_id bigint not null,
        user_id bigint not null,
        foreign key (product_id) references PRODUCT(product_id),
        foreign key (user_id) references USER_DETAILS(user_id));
CREATE TABLE IF NOT EXISTS `E_COMMERCE`.`WISHLIST` (
		product_id bigint not null,
        user_id bigint not null,
        foreign key (product_id) references PRODUCT(product_id),
        foreign key (user_id) references USER_DETAILS(user_id));
CREATE TABLE IF NOT EXISTS `E_COMMERCE`.`ORDER` (
		order_id bigint not null auto_increment,
		product_id bigint not null,
        user_id bigint not null,
        last_tracked_location varchar(255) default null,
        primary key (order_id),
        foreign key (product_id) references PRODUCT(product_id),
        foreign key (user_id) references USER_DETAILS(user_id));
CREATE TABLE IF NOT EXISTS `E_COMMERCE`.`TRENDING_PRODUCTS` (
		product_id bigint not null,
        no_of_times_product_is_sold integer default 0,
        foreign key (product_id) references PRODUCT(product_id));
CREATE TABLE IF NOT EXISTS `E_COMMERCE`.`PAYMENT_DETAILS` (
		payment_type varchar(255) default null,
        card_upi_number decimal(16) not null,
        user_id bigint not null,
        primary key(card_upi_number),
        foreign key (user_id) references USER_DETAILS(user_id));
desc `E_COMMERCE`.`USER_LOGIN_DETAILS`;
desc `E_COMMERCE`.`USER_DETAILS`;
desc `E_COMMERCE`.`PRODUCT_TYPE_DETAILS`;
desc `E_COMMERCE`.`PRODUCT`;
desc `E_COMMERCE`.`PRODUCT_CATEGORY`;
desc `E_COMMERCE`.`CART`;
desc `E_COMMERCE`.`WISHLIST`;
desc `E_COMMERCE`.`ORDER`;
desc `E_COMMERCE`.`TRENDING_PRODUCTS`;
desc `E_COMMERCE`.`PAYMENT_DETAILS`;