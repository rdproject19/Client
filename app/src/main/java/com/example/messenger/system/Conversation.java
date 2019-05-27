package com.example.messenger.system;

import java.util.HashMap;
import java.util.LinkedList;


public class Conversation
{
    private final int conversationId;
    private LinkedList participants;


    private HashMap<Integer, Message> messages;

    Conversation (int id)
    {
        this.conversationId = id;
    }

    public int getID() {
        return conversationId;
    }

    /**
     * Puts a message object in current conversation
     * @param msgId identifier for message object
     * @param msg message object
     */
    public void putMessage(Integer msgId, Message msg)
    {
        this.messages.put(msgId, msg);
    }

    public int HashCode()
    {
        return 
    }
}
