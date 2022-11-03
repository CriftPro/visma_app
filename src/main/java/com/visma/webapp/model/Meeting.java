package com.visma.webapp.model;


import java.util.*;

// MAIN TEMPLATE CLASS FOR THIS PROGRAM, AS HERE WE HAVE THE TEMPLATE FOR MEETING ROOMS

public class Meeting {
    private String meeting_id;
    private String name;

    private Person responsiblePerson;
    private String description;

    private String type;

    private String category;

    private MeetingDate startMeetingDate;
    private MeetingDate endMeetingDate;

    private List<String> attendees = new ArrayList<>();

    public List<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<String> attendees) {
        this.attendees = attendees;
    }

    public void addAttendees(String user_id){
        this.attendees.add(user_id);
    }

    public void removeAttendees(String user_id){
        this.attendees.remove(user_id);
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MeetingDate getStartDate() {
        return startMeetingDate;
    }

    public void setStartDate(MeetingDate startMeetingDate) {
        this.startMeetingDate = startMeetingDate;
    }


    public MeetingDate getEndDate() {
        return endMeetingDate;
    }

    public void setEndDate(MeetingDate endMeetingDate) {
        this.endMeetingDate = endMeetingDate;
    }




    public Person getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String id, String name) {
        this.responsiblePerson = new Person();

        responsiblePerson.setPerson_id(id);
        responsiblePerson.setPerson_name(name);

    }


    public String getMeeting_id() {
        return meeting_id;
    }
    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }







}
