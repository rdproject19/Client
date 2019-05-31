package com.example.messenger.system;
import org.json.*;

/**
 *
 * @author Cas Haaijman (s4372662)
 */
public class Message implements Comparable<Message>{
    private String type;
    private String senderID;
    private long timeStamp;
    private String message;
    private int conversationID;
    private String sessionToken;
    private String messageID;
    private boolean parsed;

    /**
     *
     * @param json The message information as a JSON object
     * @throws JSONException If the required data is not present
     */
    public Message (JSONObject json) throws JSONException {
        if (json.getString("TYPE") != "message") {
            throw new JSONException("Wrong message type");
        }
        senderID = json.getString("SENDER_ID");
        timeStamp = json.getLong("TIMESTAMP");
        message = json.getString("MESSAGE");
        conversationID = json.getInt("CONVERSATION_ID");
        sessionToken = json.getString("SESSION_TOKEN");
        messageID = json.getString("MESSAGE_ID");
        parsed = false;
    }

    /**
     *
     * @param string The message data in JSON format as a string
     * @throws JSONException If the required data is not present
     */
    public Message (String string) throws JSONException {
        this(new JSONObject(string));
    }

    /**
     * @param senderID The ID of the sender (username)
     * @param timeStamp Timestamp in unixtime
     * @param message The substance of the message
     * @param conversationID The ID of the conversation
     * @param sessionToken
     * @param messageID
     */
    public Message(String senderID, long timeStamp, String message, int conversationID, String sessionToken, String messageID) {
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

    public int getConversationID() {
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

    public int hashCode()
    {
        return Long.hashCode(this.getTimeStamp());
    }

    @Override
    public int compareTo(Message message) {
        return (int) (this.timeStamp - message.getTimeStamp());
    }
}