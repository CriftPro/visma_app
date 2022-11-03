package com.visma.webapp.model;

// MODEL FOR ADDING/REMOVING USERS TO MEETING

public class AddRequest {
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public MeetingDate getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(MeetingDate startingTime) {
        this.startingTime = startingTime;
    }

    private String user_id;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    private String user_name;

    private MeetingDate startingTime;
}
