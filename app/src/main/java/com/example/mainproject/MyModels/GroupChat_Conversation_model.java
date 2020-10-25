package com.example.mainproject.MyModels;

public class GroupChat_Conversation_model {
    public GroupChat_Conversation_model() {
    }

    public String message;
    public String username;
    public String email;
    public String date;
    public String time;

    public GroupChat_Conversation_model(String message, String username, String email, String date, String time) {
        this.message = message;
        this.username = username;
        this.email = email;
        this.date = date;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
