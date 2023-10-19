package com.example.hotel.models;


import at.favre.lib.crypto.bcrypt.BCrypt;

public class User {

    private String userName;
    private String password;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public boolean authenticate(String hashedPassword){
        if (hashedPassword == null) {
            return false;
        }
        BCrypt.Result result = BCrypt.verifyer().verify(this.password.toCharArray(),
            hashedPassword);
        return result.verified;
    }
}
