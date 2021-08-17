package com.clone.ecommerce.service.impl;

import com.clone.ecommerce.service.AdminService;
import com.clone.ecommerce.util.Product;
import com.clone.ecommerce.util.ProductType;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
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

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ResponseEntity<?> addUserWithLoginDetails(String user) throws SQLException, NoSuchAlgorithmException {
        JSONObject completeUser = new JSONObject(user);
        User userObj = new User();
        Object address = completeUser.opt("address");
        if(address instanceof String)
            userObj.setAddress(address.toString());
        else
            userObj.setAddress((JSONObject) address);
        double bal = completeUser.optDouble("balance");
        if(!Double.isNaN(bal))
            userObj.setBalance(bal);
        userObj.setEmail(completeUser.optString("email"));
        userObj.setName(completeUser.optString("name"));
        userObj.setMobile(completeUser.optString("mobile"));
        ResponseEntity<?> responseEntity = addUserToUserDetailsTable(userObj);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            UserLoginDetails userLoginDetails = new UserLoginDetails();
            userLoginDetails.setUserName(completeUser.optString("userName"));
            userLoginDetails.setPassword(completeUser.optString("password"));
            JSONObject jsonResponse = new JSONObject(responseEntity.getBody().toString());
            BigInteger user_id = jsonResponse.getBigInteger("user_id");
            return addUserToUserLoginTable(user_id, userLoginDetails);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> addProductTypeToProductTypeTable(ProductType productType) throws SQLException {
        String query = "INSERT INTO `"+dbName+"`.`PRODUCT_TYPE_DETAILS` (product_type_name, gender) VALUES (?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        try{
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{ "product_type_id" });
                preparedStatement.setString(1, productType.getProductTypeName());
                preparedStatement.setString(2, productType.getGender());
                return preparedStatement;
            }, holder);
        }catch (DataAccessException ex){
            throw new SQLException("Error occured during inserting product type into the db: "+ ex.getMessage());
        }
        JSONObject responseBody = new JSONObject();
        responseBody.put("product_type_id", holder.getKey());
        return new ResponseEntity<>(responseBody.toString(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> addProductToProductTable(Product product) throws SQLException {
        String query = "INSERT INTO `"+dbName+"`.`PRODUCT` (product_name, cost, quantity, available_delivery_list, discount) VALUES (?, ?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        try{
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{ "product_id" });
                preparedStatement.setString(1, product.getProductName());
                preparedStatement.setDouble(2, product.getCost());
                preparedStatement.setInt(3, product.getQuantity());
                preparedStatement.setString(4, product.getAvailableDeliveryList().toString());
                preparedStatement.setInt(5, product.getDiscount());
                return preparedStatement;
            }, holder);
        }catch (DataAccessException ex){
            throw new SQLException("Error occured during inserting product into the db: "+ ex.getMessage());
        }
        //"INSERT INTO `"+dbName+"`.`PRODUCT_CATEGORY (product_id, product_type_id, product_category) VALUES ("+holder.getKey()+","++")"+
        String select_query = "SELECT `PRODUCT_TYPE_ID` FROM `"+dbName+"`.`PRODUCT_TYPE_DETAILS` WHERE `PRODUCT_TYPE_NAME`="+product.getProduct_type()+";";
        JSONObject responseBody = new JSONObject();
        responseBody.put("product_id", holder.getKey());
        return new ResponseEntity<>(responseBody.toString(), HttpStatus.CREATED);
    }
}
