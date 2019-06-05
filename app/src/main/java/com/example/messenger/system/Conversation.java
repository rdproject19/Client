package com.example.messenger.system;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.messenger.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

@Entity
public class Conversation implements Comparable<Conversation>
{
    @PrimaryKey @NonNull
    private final String conversationId;

    @ColumnInfo(name = "participants")

    private ArrayList<String> participants;


    @Ignore
    private HashMap<Integer, Message> messages;
    @Ignore
    private Global global;


    public Conversation(String conversationId, Global global)
    {
        this(conversationId);
        this.global = global;
    }

    public Conversation(String conversationId)
    {
        this.conversationId = conversationId;
        this.participants = new ArrayList<>();
        this.messages = new HashMap<>();
    }

    /*public static Conversation createConversation() {
        List<String> participantId, boolean isGroup
    }*/

    public String getConversationId() {return this.conversationId; }
    public void setConversationId(int convId) { return;  }

    public ArrayList<String> getParticipants()
    {return this.participants; }
    public void setParticipants(ArrayList<String> x) { return; }
    /**
     * Puts a message object in current conversation
     =     * @param msg message object
     */
    public void putMessage(Message msg)
    {
        this.messages.put(msg.getMessageID(), msg);
    }

    public void addParticipant(String participant) {
        if(!participants.contains(participant) || !global.getUserData().getString(Keys.USERNAME).equals(participant)) {
            participants.add(participant);
        }
    }

    /**
     * Update instance to newest database information
     */
    public void update()
    {
        List<Message> messages = this.global.db().messageDao().getFromConversation(this.conversationId);
        for(Message msg : messages) {
            this.messages.putIfAbsent(msg.getMessageID(), msg);
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
        TreeMap<Integer, Message> sorted = new TreeMap<>();
        if(messages.size() != 0) {
            sorted.putAll(this.messages);
        }
        return sorted;
    }

    public int HashCode()
    { // conversationID is unique
        return conversationId.hashCode();
    }

    @Override
    public int compareTo(Conversation conversation) {
        return 0;
    }
}
