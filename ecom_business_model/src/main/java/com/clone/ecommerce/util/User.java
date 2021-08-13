package com.clone.ecommerce.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.JSONObject;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonIgnoreProperties(ignoreUnknown = false)
public class User {

    private String name;
    private String mobile;
    private JSONObject address;
    private String email;
    private double balance = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public JSONObject getAddress() {
        return address;
    }

    public void setAddress(JSONObject address) {
        this.address = address;
    }

    public void setAddress(String address) {
        String[] arr = address.split("#");
        JSONObject jsonObject = new JSONObject();
        for(int i = 0;i<arr.length;i++){
            jsonObject.put(i+1+"", arr[i]);
        }
        this.address = jsonObject;
    }
}
