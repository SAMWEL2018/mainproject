package com.example.mainproject.MyModels;

public class FarmerComplaintmodel {

    public String category;
    public String complaint;
    public String date;
    public  String time;

    public FarmerComplaintmodel() {
    }

    public FarmerComplaintmodel(String category, String complaint, String date, String time) {
        this.category = category;
        this.complaint = complaint;
        this.date = date;
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
