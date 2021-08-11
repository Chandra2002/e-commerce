package com.clone.ecommerce.dbinitalization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class CreateInitialTables {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${ecommerce.dbname}")
    String dbName;

    private static String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS `";

    @PostConstruct
    public void createInitialTables(){
        logger.debug("Execution of script for creationg of all the tables");
        String queries[] = new String[11];
        queries[0] = "CREATE DATABASE IF NOT EXISTS `"+dbName+"`;";
        queries[1] = CREATE_TABLE_IF_NOT_EXISTS+dbName+"`.`USER_DETAILS` (" +
                "user_id bigint not null auto_increment," +
                "`name` varchar(255) default null," +
                "`email` varchar(255) default null," +
                "`mobile_no` varchar(10) default null," +
                "`balance` double default 0.0," +
                "`user_address` json default null," +
                "primary key (user_id));";
        queries[2] = CREATE_TABLE_IF_NOT_EXISTS+dbName+"`.`USER_LOGIN_DETAILS` (" +
                " user_id bigint not null auto_increment, " +
                "user_name varchar(255) default null, " +
                "password varchar(255) default null, " +
                "foreign key (user_id) references `"+dbName+"`.`USER_DETAILS`(user_id));";
        queries[3] = CREATE_TABLE_IF_NOT_EXISTS+dbName+"`.`PRODUCT_TYPE_DETAILS` (" +
                "product_type_id bigint not null auto_increment," +
                "product_type_name varchar(255) default null," +
                "gender enum(\"MALE\", \"FEMALE\", \"UNISEX\")," +
                "primary key (product_type_id));";
        queries[4] = CREATE_TABLE_IF_NOT_EXISTS+dbName+"`.`PRODUCT` (" +
                "product_id bigint not null auto_increment," +
                "product_name varchar(255) default null," +
                "cost double default 0.0," +
                "quantity integer default 0," +
                "available_delivery_list json default null," +
                "discount integer default 0," +
                "primary key (product_id));";
        queries[5] = CREATE_TABLE_IF_NOT_EXISTS+dbName+"`.`PRODUCT_CATEGORY` (" +
                "product_id bigint not null," +
                "product_type_id bigint not null," +
                "product_category enum(\"<10000\", \"<100000\", \">100000\")," +
                "foreign key (product_id) references `"+dbName+"`.`PRODUCT`(product_id)," +
                "foreign key (product_type_id) references `"+dbName+"`.`PRODUCT_TYPE_DETAILS`(product_type_id));";
        queries[6] = CREATE_TABLE_IF_NOT_EXISTS+dbName+"`.`CART` (" +
                "product_id bigint not null," +
                "user_id bigint not null," +
                "foreign key (product_id) references `"+dbName+"`.`PRODUCT`(product_id)," +
                "foreign key (user_id) references `"+dbName+"`.`USER_DETAILS`(user_id));";
        queries[7] = CREATE_TABLE_IF_NOT_EXISTS+dbName+"`.`WISHLIST` (" +
                "product_id bigint not null," +
                "user_id bigint not null," +
                "foreign key (product_id) references `"+dbName+"`.`PRODUCT`(product_id)," +
                "foreign key (user_id) references `"+dbName+"`.`USER_DETAILS`(user_id));";
        queries[8] = CREATE_TABLE_IF_NOT_EXISTS+dbName+"`.`ORDER` (" +
                "order_id bigint not null auto_increment," +
                "product_id bigint not null," +
                "user_id bigint not null," +
                "last_tracked_location varchar(255) default null," +
                "primary key (order_id)," +
                "foreign key (product_id) references `"+dbName+"`.`PRODUCT`(product_id)," +
                "foreign key (user_id) references `"+dbName+"`.`USER_DETAILS`(user_id));";
        queries[9] = CREATE_TABLE_IF_NOT_EXISTS+dbName+"`.`TRENDING_PRODUCTS` (" +
                "product_id bigint not null," +
                "no_of_times_product_is_sold integer default 0," +
                "foreign key (product_id) references PRODUCT(product_id));";
        queries[10] = CREATE_TABLE_IF_NOT_EXISTS+dbName+"`.`PAYMENT_DETAILS` (" +
                "payment_type varchar(255) default null," +
                "card_upi_number decimal(16) not null," +
                "user_id bigint not null," +
                "primary key(card_upi_number)," +
                "foreign key (user_id) references `"+dbName+"`.`USER_DETAILS`(user_id));";
        Arrays.stream(queries).forEach(jdbcTemplate::execute);
    }
}
