package com.example.messenger;


/**
 *
 * @author Cas Haaijman (s4372662)
 */
public class Message
{
    private final String senderID;
    private final long timeStamp;
    private final String message;
    private final String conversationID;
    private final String sessionToken;
    private final String messageID;

    public Message (String JSON) {
        Gson gson = new Gson();
        gson.fromJson(JSON, Message::class);
        /*String[] splitString = JSON.replace("{", "").split(", ");
        for (String s : splitString) {
            String[] set = s.split(":");
            switch (set[0]) {
                case "senderID":
                    this.senderID = set[1];
                case "timeStamp":
                    this.senderID = set[1];
                case "message":
                    this.senderID = set[1];
                case "conversationID":
                    this.senderID = set[1];
                case "sessionToken":
                    this.senderID = set[1];
                case "messageID":
                    this.senderID = set[1];
            }

        }*/
    }

    public Message(String senderID, long timeStamp, String message, String conversationID, String sessionToken, String messageID) {
        this.senderID = senderID;
        this.timeStamp = timeStamp;
        this.message = message;
        this.conversationID = conversationID;
        this.sessionToken = sessionToken;
        this.messageID = messageID;
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


    }