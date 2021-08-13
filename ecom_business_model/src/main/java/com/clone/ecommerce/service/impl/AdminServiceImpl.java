package com.clone.ecommerce.service.impl;

import com.clone.ecommerce.service.AdminService;
import com.clone.ecommerce.util.User;
import com.clone.ecommerce.util.UserLoginDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${ecommerce.dbname}")
    public String dbName;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public ResponseEntity<?> addUserToUserDetailsTable(User user) throws SQLException {
        String query = "INSERT INTO `"+dbName+"`.`USER_DETAILS` (name, email, mobile_no, balance, user_address) VALUES (?, ?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        try{
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{ "user_id" });
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getMobile());
                preparedStatement.setDouble(4, user.getBalance());
                preparedStatement.setString(5, user.getAddress().toString());
                return preparedStatement;
            }, holder);
        }catch (DataAccessException ex){
            throw new SQLException("Error occured during inserting user into the db: "+ ex.getMessage());
        }
        JSONObject responseBody = new JSONObject();
        responseBody.put("user_id", holder.getKey());
        return new ResponseEntity<>(responseBody.toString(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> addUserToUserLoginTable(BigInteger user_id, UserLoginDetails userLoginDetails) throws SQLException {
        String query = "INSERT INTO `"+dbName+"`.`USER_LOGIN_DETAILS` (user_id, user_name, password) VALUES (?, ?, ?)";
        try{
            jdbcTemplate.update(query, user_id, userLoginDetails.getUserName(), userLoginDetails.getPassword());
        }catch (DataAccessException ex){
            throw new SQLException("Error occured during inserting user login details into the db: "+ ex.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
