package com.example.messenger;
import org.json.*;

/**
 *
 * @author Cas Haaijman (s4372662)
 */
public class Message {
    private String senderID;
    private long timeStamp;
    private String message;
    private String conversationID;
    private String sessionToken;
    private String messageID;
    private boolean parsed;

    public Message (JSONObject json) throws JSONException {
        senderID = json.getString("SENDER_ID");
        timeStamp = json.getLong("TIMESTAMP");
        message = json.getString("MESSAGE");
        conversationID = json.getString("CONVERSATION_ID");
        sessionToken = json.getString("SESSION_TOKEN");
        messageID = json.getString("MESSAGE_ID");
        parsed = false;
    }

    public Message(String senderID, long timeStamp, String message, String conversationID, String sessionToken, String messageID) {
        this.senderID = senderID;
        this.timeStamp = timeStamp;
        this.message = message;
        this.conversationID = conversationID;
        this.sessionToken = sessionToken;
        this.messageID = messageID;
        this.parsed = false;
    }

    public String getSenderID() {
        return senderID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getConversationID() {
        return conversationID;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getMessageID() {
        return messageID;
    }

    public boolean isParsed() {
        return parsed;
    }

    public void setParsed(boolean parsed) {
        this.parsed = parsed;
    }

}