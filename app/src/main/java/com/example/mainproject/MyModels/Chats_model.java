package com.example.mainproject.MyModels;

public class Chats_model {

    private String username;
    private String reply;
    private String date;
    private String time;
    private String mail;


    public Chats_model() {
    }

    public Chats_model(String username, String reply, String date, String time, String mail) {
        this.username = username;
        this.reply = reply;
        this.date = date;
        this.time = time;
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
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
