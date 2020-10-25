package com.example.mainproject.MyModels;

public class Supplier_Register_model {
    public String name;
    public String password;
    public String contact;

    public Supplier_Register_model() {
    }

    public Supplier_Register_model(String name, String password, String contact) {
        this.name = name;
        this.password = password;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getContact() {
        return contact;
    }
}
