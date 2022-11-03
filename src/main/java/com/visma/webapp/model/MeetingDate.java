package com.visma.webapp.model;

public class MeetingDate {


    public String getDay() {
        return day;
    }

    public void setDay(String date) {
        this.day = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time; // 14:30

    private String day; // 2022-10-01

}
