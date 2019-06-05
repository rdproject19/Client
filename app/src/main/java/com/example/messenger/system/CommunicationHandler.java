package com.example.messenger.system;

import com.example.messenger.Global;

import java.util.HashMap;
import java.util.List;

public class CommunicationHandler {

    HashMap<String, Conversation> conversations;

    CommunicationHandler(Global global) {
        conversations = new HashMap<>();
        List<Conversation> conversationList = global.db().conversationDao().getAll();
        for(Conversation conv : conversationList ) {
            this.conversations.put(conv.getConversationId(), conv);
        }
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
    public HashMap<String, Conversation> getConversations(int limit) {
        return this.conversations;
    }

}
