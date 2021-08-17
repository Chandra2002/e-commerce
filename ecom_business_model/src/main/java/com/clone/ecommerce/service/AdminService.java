package com.clone.ecommerce.service;

import com.clone.ecommerce.util.Product;
import com.clone.ecommerce.util.ProductType;
import com.clone.ecommerce.util.User;
import com.clone.ecommerce.util.UserLoginDetails;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface AdminService {
    public ResponseEntity<?> addUserToUserDetailsTable(User user) throws SQLException;
    public ResponseEntity<?> addUserToUserLoginTable(BigInteger user_id, UserLoginDetails userLoginDetails) throws SQLException;
    public ResponseEntity<?> addUserWithLoginDetails(String user) throws SQLException, NoSuchAlgorithmException;
    public ResponseEntity<?> addProductTypeToProductTypeTable(ProductType productType) throws SQLException;
    public ResponseEntity<?> addProductToProductTable(Product product) throws SQLException;
}
