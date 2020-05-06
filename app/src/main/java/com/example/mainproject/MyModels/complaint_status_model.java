package com.example.mainproject.MyModels;

public class complaint_status_model {
    private  String date;
    private  String time;
    private String category;
    private  String message;

    public complaint_status_model() {
    }

    public complaint_status_model(String date, String time, String category, String message) {
        this.date = date;
        this.time = time;
        this.category = category;
        this.message = message;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
