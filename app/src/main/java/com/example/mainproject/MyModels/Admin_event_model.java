package com.example.mainproject.MyModels;

public class Admin_event_model {
    public String name;
    public String location;
    public String contact;
    public String description;
    public String venue;
    public String date;
    public String time;

    public Admin_event_model() {
    }

    public Admin_event_model(String name, String location, String contact, String description, String venue, String date, String time) {
        this.name = name;
        this.location = location;
        this.contact = contact;
        this.description = description;
        this.venue = venue;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getDescription() {
        return description;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
