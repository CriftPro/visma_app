package com.visma.webapp.service;

import com.visma.webapp.model.AddRequest;
import com.visma.webapp.model.FilterRequest;
import com.visma.webapp.model.Meeting;
import com.visma.webapp.model.User;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;


// PUBLIC FUNCTION INTEFACE

public interface MeetingManagementService {

     String createMeeting(Meeting meeting, String user_id, User user) throws IOException;
     String addToMeeting(String meeting_id, AddRequest addUser) throws ParseException;
     String deleteMeeting(String meeting_id, String user_id);
     String removeFromMeeting(String meeting_ID, AddRequest addUser);
     Collection<Meeting> showMeetings();

     Collection<User> showUsers();

     Collection<Meeting> filterByDescription(FilterRequest filter);

    Collection<Meeting> filterByResponsiblePerson(FilterRequest filter);

     Collection<Meeting> filterByCategory(FilterRequest filter);

     Collection<Meeting> filterByType(FilterRequest filter);

     Collection<Meeting> filterByDatesAfter(FilterRequest filter);

     Collection<Meeting> filterByAttendees(FilterRequest filter);







}
