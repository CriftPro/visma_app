package com.visma.webapp.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.visma.webapp.model.*;
import com.visma.webapp.utils.Json_manager;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Collectors;



// IN THIS CLASS WE IMPLEMENT FUNCTIONS FROM INTERFACE


@Service
public class MMS_implementation implements MeetingManagementService {


    // OUR TEMPORARILY ARRAY TO SAVE DATA DURING PROGRAM EXECUTION

    private static Map<String, Meeting> meetingRepo = new HashMap<>(); // FOR MEETING
    private static Map<String, User> userRepo = new HashMap<>(); // FOR USERS - AUTOMATICALLY ADDS ONE WHEN NEW USER
                                                                    // ADD TO THE MEETINGS




    Json_manager jsonmanager = new Json_manager();

    String path = "src\\main\\resources\\storage.json";


    // FUNCTION TO WRITE OUR DATA TO THE JSON FILE

    public void overWrite() throws IOException {
        try {
            jsonmanager.Writer(path,meetingRepo,userRepo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // FUNCTION TO READ OUR DATA FROM THE JSON FILE
    public void initMap(){


        try {
            jsonmanager.Reader(path,meetingRepo,userRepo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String createMeeting(Meeting meeting, String user_id, User user) throws IOException {

        initMap();

        ArrayList<String> arrayCategory = new ArrayList<>();
        arrayCategory.add("CodeMonkey");
        arrayCategory.add("Hub");
        arrayCategory.add("Short");
        arrayCategory.add("TeamBuilding");

        ArrayList<String> arrayType = new ArrayList<>();
        arrayType.add("Live");
        arrayType.add("InPerson");


        if(!arrayCategory.contains(meeting.getCategory())){
            return "Incorrect category form, try again";
        }

        if(!arrayType.contains(meeting.getType())){
            return "Incorrect type form, try again";
        }


        List<String> attend = new ArrayList<>();
        attend.add(user_id);

        meeting.setAttendees(attend);

        int c = meetingRepo.values().size();
        c++;
        String current_id = Integer.toString(c);
        meeting.setMeeting_id(current_id);


        meetingRepo.put(meeting.getMeeting_id(), meeting);
        user.add_to_List(current_id);
        userRepo.put(user_id,user);

        overWrite();

        return "Meeting created successfully";


    }

    @Override
    public String addToMeeting(String meeting_ID, AddRequest addUser) throws ParseException {

        initMap();


        String response = "User added to meeting succsefully";

        String user_id = addUser.getUser_id();
        String user_name = addUser.getUser_name();

        if(meetingRepo.get(meeting_ID).getAttendees().contains(user_id)){
            return "User is already added to the meeting";
        }else {
            User user = new User();
            user.setName(user_name);
            user.setUser_id(addUser.getUser_id());
            user.add_to_List(meeting_ID);
            userRepo.put(user_id,user);
        }

        String meeting_day = meetingRepo.get(meeting_ID).getStartDate().getDay();
        String meeting_time_start = meetingRepo.get(meeting_ID).getStartDate().getTime();
        String meeting_time_end = meetingRepo.get(meeting_ID).getEndDate().getTime();

        String user_busy_day;
        String user_busy_time_begin;
        String user_busy_time_end;

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(meeting_day);
        Date time_begin = new SimpleDateFormat("HH:mm").parse(meeting_time_start);
        Date time_end = new SimpleDateFormat("HH:mm").parse(meeting_time_end);

        Date date_busy;
        Date time_busy_begin;
        Date time_busy_end;

        for(int i = 0; i < userRepo.get(user_id).getMeeting_list().size();i++){
            user_busy_day = meetingRepo.get(userRepo.get(user_id).getMeeting_list().get(i)).getStartDate().getDay();
            user_busy_time_begin = meetingRepo.get(userRepo.get(user_id).getMeeting_list().get(i)).getStartDate().getTime();
            user_busy_time_end = meetingRepo.get(userRepo.get(user_id).getMeeting_list().get(i)).getEndDate().getTime();

            date_busy = new SimpleDateFormat("yyyy-MM-dd").parse(user_busy_day);
            time_busy_begin = new SimpleDateFormat("HH:mm").parse(user_busy_time_begin);
            time_busy_end = new SimpleDateFormat("HH:mm").parse(user_busy_time_end);;

            if(date_busy.equals(date)){
                if(time_busy_begin.after(time_begin) || time_busy_end.after(time_begin)){
                    response = "WARNING! User already participates at other meeting that time";
                }
            }
        }



        meetingRepo.get(meeting_ID).addAttendees(user_id);

        try {
            overWrite();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;


    }

    @Override
    public String deleteMeeting(String meeting_id, String user_id) {
        initMap();

        String response = "";
        if(!meetingRepo.containsKey(meeting_id)){
            return "Meeting already doesnt exist";
        }
        System.out.println("Init: " + meeting_id);
        String responsible_id = meetingRepo.get(meeting_id).getResponsiblePerson().getPerson_id();

        if(responsible_id.equals(user_id)){
            meetingRepo.remove(meeting_id);
            userRepo.get(user_id).remove_from_List(meeting_id);
            if(!meetingRepo.containsKey(user_id)){
                response = " Meeting has been deleted successfully";
            }
        }
        else {
            return  "WARNING! You cannot delete a meeting if you are not responsible for this meeting";
        }

        try {
            overWrite();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;

    }

    @Override
    public String removeFromMeeting(String meeting_ID, AddRequest addUser) {

        initMap();


        String response = "Successfully removed user from meeting";
        String user_id = addUser.getUser_id();

        if(user_id.equals(meetingRepo.get(meeting_ID).getResponsiblePerson().getPerson_id())){
            return "Cannot remove the user, he is the meeting room creator";
        }

        meetingRepo.get(meeting_ID).removeAttendees(user_id);

        try {
            overWrite();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;


    }

    @Override
    public Collection<Meeting> showMeetings() {
        initMap();
        return meetingRepo.values();
    }

    @Override
    public Collection<User> showUsers() {
        initMap();
        return userRepo.values();
    }

    @Override
    public Collection<Meeting> filterByDescription(FilterRequest filter) {
        initMap();

        var meetings = meetingRepo.values();;

        Predicate<Meeting> byDescription = meeting ->(meeting.getDescription()).contains(filter.getFilterParameter());

        return meetings.stream().filter(byDescription)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meeting> filterByResponsiblePerson(FilterRequest filter) {
        initMap();

        var meetings = meetingRepo.values();

        Predicate<Meeting> byPerson = meeting ->(meeting.getResponsiblePerson().getPerson_name()).contains(filter.getFilterParameter());

        return meetings.stream().filter(byPerson)
                .collect(Collectors.toList());
    }


    @Override
    public Collection<Meeting> filterByCategory(FilterRequest filter) {
        initMap();

        var meetings = meetingRepo.values();

        Predicate<Meeting> byCategory = meeting -> (meeting.getCategory().contains(filter.getFilterParameter()));

        return meetings.stream().filter(byCategory)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meeting> filterByType(FilterRequest filter) {
        initMap();

        var meetings = meetingRepo.values();;

        Predicate<Meeting> byType = meeting ->(meeting.getType().contains(filter.getFilterParameter()));

        return meetings.stream().filter(byType)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meeting> filterByDatesAfter(FilterRequest filter) {
        initMap();

        var meetings = meetingRepo.values();





        Predicate<Meeting> byDate = meeting -> {
            try {
                return (new SimpleDateFormat("yyyy-MM-dd").parse(meeting.getStartDate().getDay()).
                        after((new SimpleDateFormat("yyyy-MM-dd").parse(filter.getDate_begin().getDay()))));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };

        return meetings.stream().filter(byDate)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meeting> filterByAttendees(FilterRequest filter) {
        initMap();

        var meetings = meetingRepo.values();

        Predicate<Meeting> byAttendees = meeting ->( meeting.getAttendees().size() >  Integer.parseInt(filter.getFilterParameter()));

        Collection<Meeting> output = meetings.stream().filter(byAttendees)
                .collect(Collectors.toList());
        return output;



    }





}
