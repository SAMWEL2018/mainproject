package com.example.mainproject.MyModels;

public class uploadinfo {

    public uploadinfo() {
    }
    public String id;
    public String imagename;
    public String imageURL;
    public String price;
    public String supplier_contact;
    public String email;

    public uploadinfo(String imagename, String imageURL, String price, String supplier_contact, String email,String id) {
        this.imagename = imagename;
        this.imageURL = imageURL;
        this.price = price;
        this.supplier_contact = supplier_contact;
        this.email = email;
        this.id=id;
    }

    public String getImagename() {
        return imagename;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPrice() {
        return price;
    }

    public String getSupplier_contact() {
        return supplier_contact;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
