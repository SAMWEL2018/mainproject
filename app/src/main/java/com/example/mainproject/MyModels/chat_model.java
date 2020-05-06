package com.example.mainproject.MyModels;

public class chat_model {
    private String complaint;
    private String date;
    private String time;
    private String mail;

    public chat_model() {
    }

    public chat_model(String complaint, String date, String time, String mail) {
        this.complaint = complaint;
        this.date = date;
        this.time = time;
        this.mail = mail;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
