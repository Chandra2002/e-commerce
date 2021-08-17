package com.clone.ecommerce.contoller;

import com.clone.ecommerce.service.AdminService;
import com.clone.ecommerce.util.Product;
import com.clone.ecommerce.util.ProductType;
import com.clone.ecommerce.util.User;
import com.clone.ecommerce.util.UserLoginDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @PutMapping(value = "/add/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addUser(@RequestBody User user) throws SQLException {
        return adminService.addUserToUserDetailsTable(user);
    }

    @PutMapping(value = "/add/userlogin/{user_id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addUserLoginDetails(@PathVariable("user_id") BigInteger user_id, @RequestBody UserLoginDetails userLoginDetails) throws SQLException {
        return adminService.addUserToUserLoginTable(user_id, userLoginDetails);
    }

    @PutMapping(value = "/add/completeUser", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addCompleteUserDetails(@RequestBody String userDetails) throws SQLException, NoSuchAlgorithmException {
        return adminService.addUserWithLoginDetails(userDetails);
    }

    @PutMapping(value = "/add/productType", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addProductType(@RequestBody ProductType productType) throws SQLException {
        return adminService.addProductTypeToProductTypeTable(productType);
    }

    @PutMapping(value = "/add/product", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addProduct(@RequestBody Product product) throws SQLException {
        return adminService.addProductToProductTable(product);
    }
}
