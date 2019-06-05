package com.example.messenger.system;

import java.util.HashMap;

public class CommunicationHandler
{

    HashMap<String, Conversation> conversations;

    CommunicationHandler()
    {
        conversations = new HashMap<>();
    }

    /**
     * Checks if conversation is known
     * @param id conversation id
     * @return boolean
     */
    public boolean conversationExists(String id)
    {
        return this.conversations.containsKey(id);
    }


    /**
     * Get conversation object
     * @param id conversation id
     * @return conversation object
     */
    public Conversation getConversation(String id)
    {
        return this.conversations.get(id);
    }


    /**
     * Put conversation object in list
     * @param convo conversation
     */
    public void putConversation(Conversation convo)
    {
        String id = convo.getConversationId();
        this.conversations.put(id, convo);
    }


    /**
     * Get list of conversations
     * @param limit number of conversations to pull from list
     * @return HashMap of conversations
     */
    public HashMap<String, Conversation> getConversations(int limit)
    {
        return this.conversations;
    }

}
