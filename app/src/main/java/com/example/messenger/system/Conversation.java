package com.example.messenger.system;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;


public class Conversation
{
    @PrimaryKey
    private final int conversationId;

    @ColumnInfo(name = "participants")
    private ArrayList<String> participants;

    @Ignore
    private HashMap<Integer, Message> messages;

    public Conversation (int id)
    {
        this.conversationId = id;
    }

    public int getID() {
        return conversationId;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    /**
     * Puts a message object in current conversation
=     * @param msg message object
     */
    public void putMessage(Message msg)
    {
        this.messages.put(msg.getMessageID(), msg);
    }

    public void addParticipant(String participant) {
        if(!participants.contains(participant)) {
            participants.add(participant);
        }
    }

    /**
     * If a conversation only has 2 participants, that is, it is a peer-to-peer
     * conversation, we can access
     * @return Username/UserID of participant
     */
    public String recipient(String self)
    {
        if(participants.size() == 2)
        {

            if(!this.participants.get(0).equals(self))
                return this.participants.get(0);
            else
                return this.participants.get(1);
        }
        return "GROUP";
    }


    /**
     * Get messages sorted by send date
     *
     * @return TreeMap<Integer, Message>
     */
    public TreeMap<Integer, Message> getSortedMessages()
    {
        TreeMap<Integer, Message> sorted = new TreeMap<>(this.messages);
        return sorted;
    }

    public int HashCode()
    {
        return this.conversationId;
    }
}
