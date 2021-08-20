package com.clone.ecommerce.service.clientimpl;

import com.clone.ecommerce.service.ClientService;
import com.clone.ecommerce.util.ProductType;
import com.clone.ecommerce.util.UserLoginDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${ecommerce.dbname}")
    public String dbName;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public ResponseEntity<?> validateUserCredentials(UserLoginDetails userLoginDetails) throws SQLException {
        String select_query = "SELECT `USER_ID` FROM `"+dbName+"`.`USER_LOGIN_DETAILS` where user_name=? and password=?";
        int user_id = -1;
        try{
            user_id = jdbcTemplate.queryForObject(select_query, Integer.class, new Object[]{userLoginDetails.getUserName(), userLoginDetails.getPassword()});
        }catch (DataAccessException da){
            throw new SQLException("Error occured during searching user in the db: "+ da.getMessage());
        }
        JSONObject responseBody = new JSONObject();
        responseBody.put("user_id", user_id);
        return new ResponseEntity<>(responseBody.toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> showAvailableProductTypes() throws SQLException, JsonProcessingException {
        String select_product_types = "SELECT * FROM `"+dbName+"`.`PRODUCT_TYPE_DETAILS`";
        List<ProductType> productTypeList = new ArrayList<>();
        try{
            jdbcTemplate.query(select_product_types, (rs) -> {
                ProductType productType = new ProductType();
                productType.setProductTypeName(rs.getString("product_type_name"));
                productType.setGender(rs.getString("gender"));
                productTypeList.add(productType);
            });
        }catch (DataAccessException da) {
            throw new SQLException("Error occured during searching product types in the db: " + da.getMessage());
        }
        return new ResponseEntity<>(mapper.writeValueAsString(productTypeList), HttpStatus.OK);
    }
}
