package com.example.messenger.system;

import java.util.HashMap;
import java.util.LinkedList;

public class CommunicationHandler
{

    HashMap<Integer, Conversation> conversations;

    CommunicationHandler()
    {
        this.conversations = new HashMap<>();
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
     * @param convo conversation
     */
    public void putConversation(Conversation convo)
    {
        int id = convo.getID();
        this.conversations.put(id, convo);
    }


    /**
     * Get list of conversations
     * @param limit number of conversations to pull from list
     * @return HashMap of conversations
     */
    public HashMap<Integer, Conversation> getConversations(int limit)
    {
        return this.conversations;
    }


}
