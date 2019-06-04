package com.example.messenger.system;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.messenger.Global;

import org.json.*;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Cas Haaijman (s4372662)
 */
@Entity
public class Message implements Comparable<Message>{
    @ColumnInfo(name = "senderID")
    private String senderID;

    @ColumnInfo(name = "timeStamp")
    private long timeStamp;

    @ColumnInfo(name = "Message")
    private String message;

    @ColumnInfo(name = "convID")
    private int conversationID;

    @ColumnInfo(name = "sessionToken")
    private String sessionToken;

    @PrimaryKey(autoGenerate = true)
    private int messageID;

    @Ignore
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
     */
    public Message(String senderID, long timeStamp, String message, int conversationID, String sessionToken) {
        this.senderID = senderID;
        this.timeStamp = timeStamp;
        this.message = message;
        this.conversationID = conversationID;
        this.sessionToken = sessionToken;
        this.parsed = false;
    }

    /**
     * Used for generating Message class giving only the string and the conversation ID.
     * @param message The actual message as typed by the user
     * @param conversationID The ConversationId beloning to the message
     * @param global The Global class
     * @return
     */
    public static Message makeMessage(String message, int conversationID, Global global) {
        UserData userData = global.getUserData();
        String name = userData.getString(Keys.USERNAME);
        String sessionToken = userData.getString(Keys.TOKEN);
        return new Message (
                name,
                (int) (System.currentTimeMillis() / 1000L),
                message,
                conversationID,
                sessionToken
        );

    }

    public String toJSON() {
        return "{" +
                "TYPE: \"message\"" +
                "SENDER_ID:\"" + senderID + "\"," +
                "TIMESTAMP:" + timeStamp + "," +
                "MESSAGE:\"" + message + "\"," +
                "CONVERSATION_ID: " + conversationID + "," +
                "SESSION_TOKEN:\"" + sessionToken + "\"," +
                "}";
    }

    //Getters and setters
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

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int j) { this.messageID=j;}


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

    public String getTimeString() {
        Calendar calendar = Calendar.getInstance();
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)
                + "   " + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH);
        return time;
    }

    @Override
    public int compareTo(Message message) {
        return (int) (this.timeStamp - message.getTimeStamp());
    }
}
