package com.example.mainproject.MyModels;

import android.widget.TextView;

public class event_model {

    public event_model(String e_name, String e_description, String e_location, String e_contact, String display_date, String display_time) {
    }

    private String event_name;
    private String event_desc;
    private String location;
    private String venue;
    private String contact;
    private String event_date;
    private String event_time;

    public event_model(String event_name, String event_desc, String location, String venue, String contact, String event_date, String event_time) {
        this.event_name = event_name;
        this.event_desc = event_desc;
        this.location = location;
        this.venue = venue;
        this.contact = contact;
        this.event_date = event_date;
        this.event_time = event_time;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public String getLocation() {
        return location;
    }

    public String getVenue() {
        return venue;
    }

    public String getContact() {
        return contact;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_time() {
        return event_time;
    }
}
