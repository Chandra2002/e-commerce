package com.clone.ecommerce.contoller;

import com.clone.ecommerce.service.ClientService;
import com.clone.ecommerce.util.UserLoginDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping(value = "/user/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> validateUserCredentials(@RequestBody UserLoginDetails userLoginDetails) throws SQLException {
        return clientService.validateUserCredentials(userLoginDetails);
    }

    @GetMapping(value = "/product/types", produces = "application/json")
    public ResponseEntity<?> getProductList() throws SQLException, JsonProcessingException {
        return clientService.showAvailableProductTypes();
    }
}
