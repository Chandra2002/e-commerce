package com.clone.ecommerce.service;

import com.clone.ecommerce.util.User;
import com.clone.ecommerce.util.UserLoginDetails;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.sql.SQLException;

public interface AdminService {
    public ResponseEntity<?> addUserToUserDetailsTable(User user) throws SQLException;
    public ResponseEntity<?> addUserToUserLoginTable(BigInteger user_id, UserLoginDetails userLoginDetails) throws SQLException;
}
