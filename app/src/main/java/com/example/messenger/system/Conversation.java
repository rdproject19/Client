package com.example.messenger.system;

import java.util.HashMap;
import java.util.LinkedList;


public class Conversation
{
    private int conversationId;
    private LinkedList participants;


    private HashMap<String, Message> messages;

    Conversation (int id)
    {
        this.conversationId = id;
    }

    public void putMessage(Message msg)
    {
        this.messages.put(msg.getMessageID(), msg);
    }







}
