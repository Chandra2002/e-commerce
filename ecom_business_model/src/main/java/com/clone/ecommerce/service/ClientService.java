package com.clone.ecommerce.service;

import com.clone.ecommerce.util.UserLoginDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

public interface ClientService {
    public ResponseEntity<?> validateUserCredentials(UserLoginDetails userLoginDetails) throws SQLException;
    public ResponseEntity<?> showAvailableProductTypes() throws SQLException, JsonProcessingException;
}
