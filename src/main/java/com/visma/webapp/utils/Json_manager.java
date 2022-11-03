package com.visma.webapp.utils;

import com.google.gson.*;
import com.visma.webapp.model.Meeting;
import com.visma.webapp.model.MeetingDate;
import com.visma.webapp.model.User;

import java.io.*;
import java.util.Map;

// CLASS THAT CONTAINS FUNCTIONS TO MANAGE JSON FILE, PRIMARALY THE WRITER AND READER.
public class Json_manager {


    public void Reader(String path, Map<String, Meeting> meetingMap, Map<String, User> userMap) throws IOException {

        BufferedReader bufferedReader;


        try {
            bufferedReader = new BufferedReader(
                    new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        StringBuilder stringBuilder = new StringBuilder();


        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String jsonString = stringBuilder.toString();

        if(jsonString.length()<= 0){
            return;
        }

        System.out.println("Read: " + jsonString);

        JsonObject rootNode = JsonParser.parseString(jsonString).getAsJsonObject();


        if (rootNode.isJsonObject()) {

            JsonArray users = rootNode.getAsJsonArray("users");

            if (users.isJsonArray()) {
                for (int i = 0; i < users.size(); i++) {

                    User user = new User();


                    JsonObject userNode = users.get(i).getAsJsonObject();
                    JsonElement idNode = userNode.get("user_id");
                    JsonElement nameNode = userNode.get("name");

                    JsonArray user_meetings = userNode.getAsJsonArray("meeting_list");

                    for (int k = 0; k < user_meetings.size(); k++) {
                        //JsonObject user_list_element = user_meetings.get(k).getAsJsonObject();
                        JsonElement user_meeting_id = user_meetings.get(k);

                        user.add_to_List(user_meeting_id.getAsString());

                    }


                    user.setUser_id(idNode.getAsString());
                    user.setName(nameNode.getAsString());

                    userMap.put(user.getUser_id(), user);
                }
            }
        }
        JsonArray meetings = rootNode.getAsJsonArray("meetings");

        for (int i = 0; i < meetings.size(); i++) {
            JsonObject userNode = meetings.get(i).getAsJsonObject();
            JsonElement idNode = userNode.get("meeting_id");
            JsonElement nameNode = userNode.get("name");

            JsonObject personNode = userNode.getAsJsonObject("responsiblePerson");
            JsonElement person_idNode = personNode.get("person_id");
            JsonElement person_nameNode = personNode.get("person_name");

            JsonElement descriptionNode = userNode.get("description");
            JsonElement categoryNode = userNode.get("category");
            JsonElement typeNode = userNode.get("type");

            JsonObject date_startNode = userNode.getAsJsonObject("startMeetingDate");
            JsonElement date_startDayNode = date_startNode.get("day");
            JsonElement date_startTimeNode = date_startNode.get("time");

            JsonObject date_endNode = userNode.getAsJsonObject("startMeetingDate");
            JsonElement date_endDayNode = date_endNode.get("day");
            JsonElement date_endTimeNode = date_endNode.get("time");


            Meeting meeting = new Meeting();

            meeting.setMeeting_id(idNode.getAsString());
            meeting.setName(nameNode.getAsString());
            meeting.setResponsiblePerson(person_idNode.getAsString(), person_nameNode.getAsString());
            meeting.setDescription(descriptionNode.getAsString());
            meeting.setCategory(categoryNode.getAsString());
            meeting.setType(typeNode.getAsString());

            MeetingDate date_start = new MeetingDate();
            MeetingDate date_end = new MeetingDate();

            date_start.setDay(date_startDayNode.getAsString());
            date_start.setTime(date_startTimeNode.getAsString());

            date_end.setDay(date_endDayNode.getAsString());
            date_end.setTime(date_endTimeNode.getAsString());

            meeting.setStartDate(date_start);
            meeting.setEndDate(date_end);

            JsonArray attendees = userNode.getAsJsonArray("attendees");

            for (int k = 0; k < attendees.size(); k++) {

                JsonElement attend_id_Node = attendees.get(k);

                meeting.addAttendees(attend_id_Node.getAsString());

            }

            meetingMap.put(meeting.getMeeting_id(), meeting);


        }

    }


    public void Writer(String path, Map<String, Meeting> meetingMap, Map<String, User> userMap) throws IOException {
        FileWriter writer = new FileWriter(path);


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput_users = gson.toJson(userMap.values());
        String jsonOutput_meetings = gson.toJson(meetingMap.values());
        writer.write(" { \"users\" :  " + jsonOutput_users + "  , ");
        writer.write("  \"meetings\" :   " + jsonOutput_meetings + "  }");

        System.out.println("Write: " + jsonOutput_users);
        System.out.println("Write: " + jsonOutput_meetings);

        writer.close();
    }
}










