package com.example.messenger.system;

import java.util.HashMap;
import java.util.LinkedList;

public class CommunicationHandler
{

    HashMap<Integer, Conversation> conversations;

    CommunicationHandler()
    {

    }

    /**
     * Checks if conversation is known
     * @param id conversation id
     * @return boolean
     */
    public boolean conversationExists(int id)
    {
        return this.conversations.containsKey(id);
    }


    /**
     * Get conversation object
     * @param id conversation id
     * @return conversation object
     */
    public Conversation getConversation(int id)
    {
        return this.conversations.get(id);
    }


    /**
     * Put conversation object in list
     * @param id conversation id
     */
    public void putConversation(int id)
    {
        Conversation conv = new Conversation(id);
        this.conversations.put(id, conv);
    }




}
