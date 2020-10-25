package com.example.mainproject.MyModels;

public class FarmerRegistermodel {
    public String email;
    public String username;

    public FarmerRegistermodel() {
    }

    public FarmerRegistermodel(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
