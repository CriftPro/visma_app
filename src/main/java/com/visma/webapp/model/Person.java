package com.visma.webapp.model;


// CREATED TO BE USED BY MEETING CLASS TO FORM THE PERSON PARAMETER

public class Person {
    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {

        this.person_name = person_name;
    }

    private String person_id;
    private String person_name;
}
