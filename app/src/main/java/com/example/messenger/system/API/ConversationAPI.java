package com.example.messenger.system.API;

import com.example.messenger.system.API.util.WebResource;
import com.example.messenger.system.GFG;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.lang.Exception;

public class ConversationAPI extends WebAPI
{
    /** Section of the API */
    private static final String SECTION = "conversation/";


    /**
     * Expected result: {@link ResponseEnum#OK} or {@link ResponseEnum#UNAUTHORIZED}
     * @param members username List of usernames that are part of this conversation
     * @param isGroup true if this instance of a conversation belongs to a group .
     * @return {@link ResponseEnum}
     * @throws Exception Probably because a member does not exists on the server or some illegal argument
     * was tried to be passed.
     */
    public String createConversation(List<String> members, boolean isGroup) throws Exception
    {
        HashMap<String, String> params = new HashMap<>();
        StringBuilder participantsList = new StringBuilder();
        for(String member : members)
        {
            final String H = member + ";";
            participantsList.append(H);
        }

        participantsList.deleteCharAt(participantsList.length()-1);


        params.putIfAbsent("members", participantsList.toString());
        if(isGroup)
            params.putIfAbsent("isGroup", "true");
        else
            params.putIfAbsent("isGroup", "false");

        this.sendPostRequest(SECTION, "create", params);
        if(this.getResponseCode() == 200)
            return this.getResponseBody();
        else
            throw new Exception("Could not successfully retrieve information from the central server: ERROR CODE " + WebAPI.Response(this.getResponseCode()) + ": " + WebAPI.Response(this.getResponseCode()).getDesc());
    }
}
