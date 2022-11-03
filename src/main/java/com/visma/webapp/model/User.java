package com.visma.webapp.model;

import java.util.ArrayList;
import java.util.List;


// OUR MODEL TEMPLATE CLASS USER, WHICH REPRESENT THE USER/PARTICIPANT/ATTENDEE OF MEETINGS.

public class User {

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<String> getMeeting_list() {
        return meeting_list;
    }

    public void setMeeting_list(List<String> meeting_list) {
        this.meeting_list = meeting_list;
    }

    public void add_to_List(String meeting_id) {
        this.meeting_list.add(meeting_id);

    }

    public void remove_from_List(String meeting_id) {
        this.meeting_list.remove(meeting_id);

    }

    // CLASS PARAMETERS

    private String name;
    private String user_id;

    private List<String> meeting_list = new ArrayList<>(); // THIS LIST SAVES ONLY MEETINGS IDS, WHERE USER PARTICIPATES

}
