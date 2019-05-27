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

    public void putMessage(Message message) {

    }

}
