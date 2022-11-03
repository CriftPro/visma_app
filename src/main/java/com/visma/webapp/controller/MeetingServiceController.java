package com.visma.webapp.controller;

import com.visma.webapp.model.AddRequest;
import com.visma.webapp.model.FilterRequest;
import com.visma.webapp.model.Meeting;
import com.visma.webapp.model.User;
import com.visma.webapp.service.MeetingManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;


// IN THIS CLASS WE MAKE END POINTS FOR OUR REST API
// CURRENT API END POINTS:
// CREATE MEETING -> url = /users/{user_id}/meeting ->  REQUEST JSON BODY =  meeting_creation_template;
// ADD MEMBER TO THE MEETING -> url = /users/{user_id}/meeting/add ->  REQUEST JSON BODY = meeting_adding_template;
// REMOVE MEMBER FROM THE MEETING -> url = /users/{user_id}/meeting/remove ->  REQUEST JSON BODY = meeting_adding_template;

// VIEW STORED MEETING -> url = /users/{user_id}/meeting ->   REQUEST NONE;

// VIEW FILTERED

@RestController
public class MeetingServiceController {
    @Autowired
    MeetingManagementService mms;
    User user;
    String response = "Default";


    // CREATE THE MEETING FUNCTION

    @RequestMapping(value = "/users/{user_id}/meeting", method = RequestMethod.POST)
    public ResponseEntity<Object> createMeeting(@RequestBody Meeting meeting, @PathVariable String user_id) throws IOException {

        user = new User();
        user.setUser_id(user_id);
        user.setName(meeting.getResponsiblePerson().getPerson_name());

        response = mms.createMeeting(meeting,user_id,user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    // ADD TO THE MEETING FUNCTION

    @RequestMapping(value =  "/users/{user_id}/meeting/{meeting_id}/add", method = RequestMethod.PUT)
    public ResponseEntity<Object>
    addToMeeting(@PathVariable("meeting_id") String meeting_id, @RequestBody AddRequest addUser) throws ParseException {

        response = mms.addToMeeting(meeting_id, addUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // REMOVE FROM MEETING FUNCTION

    @RequestMapping(value =  "/users/{user_id}/meeting/{meeting_id}/remove", method = RequestMethod.PUT)
    public ResponseEntity<Object>
    removeFromMeeting(@PathVariable("meeting_id") String meeting_id, @RequestBody AddRequest addUser) throws ParseException {

        response = mms.removeFromMeeting(meeting_id, addUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // DELETE MEETING FUNCTION

    @RequestMapping(value = "/users/{user_id}/meeting/{meeting_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("meeting_id") String id , @PathVariable("user_id") String user_id) {

        response = mms.deleteMeeting(id,user_id);
        return new ResponseEntity<>( response, HttpStatus.OK);
    }




    @RequestMapping(value = "/users/{user_id}/meeting")
    public ResponseEntity<Object> getMeeting() {
        return new ResponseEntity<>(mms.showMeetings(), HttpStatus.OK);
    }

    // SHOWING CURRENT USERS

    @RequestMapping(value = "/users")
    public ResponseEntity<Object> getUser() {
        return new ResponseEntity<>(mms.showUsers(), HttpStatus.OK);
    }

    // FILTER FUNCTIONS

    @RequestMapping(value = "/users/{user_id}/meeting/description")
    public ResponseEntity<Object> filterMeeting_desc(@RequestBody FilterRequest filter) {
        return new ResponseEntity<>(mms.filterByDescription(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{user_id}/meeting/resperson")
    public ResponseEntity<Object> filterMeeting_person(@RequestBody FilterRequest filter) {
        return new ResponseEntity<>(mms.filterByResponsiblePerson(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{user_id}/meeting/category")
    public ResponseEntity<Object> filterMeeting_category(@RequestBody FilterRequest filter) {
        return new ResponseEntity<>(mms.filterByCategory(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{user_id}/meeting/type")
    public ResponseEntity<Object> filterMeeting_type(@RequestBody FilterRequest filter) {
        return new ResponseEntity<>(mms.filterByType(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{user_id}/meeting/afterdate")
    public ResponseEntity<Object> filterMeeting_after(@RequestBody FilterRequest filter) {
        return new ResponseEntity<>(mms.filterByDatesAfter(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{user_id}/meeting/attends")
    public ResponseEntity<Object> filterMeeting_attends(@RequestBody FilterRequest filter) {
        return new ResponseEntity<>(mms.filterByAttendees(filter), HttpStatus.OK);
    }




}

