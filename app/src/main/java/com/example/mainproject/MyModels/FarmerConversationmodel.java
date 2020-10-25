package com.example.mainproject.MyModels;

public class FarmerConversationmodel {
    public String complaint;
    public String reply;
    public String username;
    public String time;
    public String email;
    public String date;

    public FarmerConversationmodel() {
    }

    public FarmerConversationmodel(String complaint, String reply, String username, String time, String email, String date) {
        this.complaint = complaint;
        this.reply = reply;
        this.username = username;
        this.time = time;
        this.email = email;
        this.date = date;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getReply() {
        return reply;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }
}
