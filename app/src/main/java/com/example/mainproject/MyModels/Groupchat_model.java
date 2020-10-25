package com.example.mainproject.MyModels;

public class Groupchat_model {

    private String mail;
    private String username;
    private String message;
    private String date;
    private String time;

    public Groupchat_model() {
    }

    public Groupchat_model(String mail,String username, String message, String date, String time) {
        this.mail= mail;
        this.username = username;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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
}
