package com.example.messenger.system;

import com.example.messenger.Global;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Central point for all communication actions a person may perform
 */
public class CommunicationHandler {
    /**
     * @var conversations a list of conversations indexed by their conversationId
     */
    private HashMap<String, Conversation> conversations;
    /**
     * Holds the central instance of the {@link Global} class
     */
    private Global global;


    /**
     * Public constructor
     *
     * @param global The central instance of {@link Global}
     * @throws Exception If something goes wrong
     */
    public CommunicationHandler(Global global) {
        this.global = global;
        conversations = new HashMap<>();
        List<Conversation> conversationList = global.db().conversationDao().getAll();
        for (Conversation conv : conversationList) {
            conv.update(global);
            this.conversations.put(conv.getConversationId(), conv);
        }
    }

    /**
     * Conversations exist ?
     *
     * @return true if the person has a conversation in their conversation list.
     */
    public boolean hasConversations() {
        return !(this.conversations.size() == 0);
    }

    /**
     * Checks if conversation is known
     *
     * @param id conversation id
     * @return boolean
     */
    public boolean conversationExists(String id) {
        return this.conversations.containsKey(id);
    }


    /**
     * Get conversation object
     *
     * @param id conversation id
     * @return conversation object
     */
    public Conversation getConversation(String id) {
        return this.conversations.get(id);
    }


    /**
     * Put conversation object in list
     *
     * @param convo conversation
     */
    public void putConversation(Conversation convo) {
        String id = convo.getConversationId();
        this.conversations.put(id, convo);
    }
    



    /**
     * Get list of conversations
     *
     * @param limit number of conversations to pull from list
     * @return HashMap of conversations
     */
    public HashMap<String, Conversation> getConversations(int limit) throws Exception {
        if (!this.hasConversations()) {
            throw new Exception("The client has no conversations. ");
        }
        return this.conversations;
    }

}
