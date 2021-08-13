package com.clone.ecommerce.util;

import java.security.NoSuchAlgorithmException;

public class UserLoginDetails {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = Encrypter.toHexString(Encrypter.getSHA(password));
    }
}
