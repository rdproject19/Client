package com.example.messenger.system;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.messenger.Global;

import org.json.*;

import java.text.SimpleDateFormat;
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
    private String conversationID;

    @ColumnInfo(name = "delayed")
    private boolean delayed;

    @ColumnInfo(name = "recieveDate")
    private long recieveDate;

    //@ColumnInfo(name = "sessionToken")
    //private String sessionToken;

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
        String s = json.getString("TYPE");
        if (!s.equals("message")) {
            throw new JSONException("Wrong message type");
        }
        senderID = json.getString("SENDER_ID");
        timeStamp = json.getLong("TIMESTAMP");
        message = json.getString("MESSAGE");
        conversationID = json.getString("CONVERSATION_ID");
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
     */
    public Message(String senderID, long timeStamp, String message, String conversationID, boolean delayed, long recieveDate) {
        this.senderID = senderID;
        this.timeStamp = timeStamp;
        this.message = message;
        this.conversationID = conversationID;
        this.parsed = false;
        this.delayed = delayed;
        this.recieveDate = recieveDate;
    }

    /**
     * Used for generating Message class giving only the string and the conversation ID.
     * @param message The actual message as typed by the user
     * @param conversationID The ConversationId beloning to the message
     * @param global The Global class
     * @return
     */
    public static Message makeMessage(String message, String conversationID, Global global) {
        UserData userData = global.getUserData();
        MessageDao db = global.db().messageDao();
        String name = userData.getString(Keys.USERNAME);
        Message msg = new Message (
                name,
                (int) (System.currentTimeMillis() / 1000L),
                message,
                conversationID,
                false,
                (int) (System.currentTimeMillis() / 1000L)
        );
        db.putMessage(msg);
        msg.setMessageID(db.getAll().size());

        return msg;

    }

    /**
     * Used for generating Message class giving only the string and the conversation ID.
     * @param message The actual message as typed by the user
     * @param conversationID The ConversationId beloning to the message
     * @param global The Global class
     * @return
     */
    public static Message makeMessage(String message, String conversationID, long recieveDate, Global global) {
        UserData userData = global.getUserData();
        MessageDao db = global.db().messageDao();
        String name = userData.getString(Keys.USERNAME);
        Message msg = new Message (
                name,
                (int) (System.currentTimeMillis() / 1000L),
                message,
                conversationID,
                true,
                recieveDate
        );
        db.putMessage(msg);
        msg.setMessageID(db.getAll().size());

        return msg;

    }


    /**
     * Makes a JSON string of a message that conforms to our server protocol.
     * Requires a message ID (So save first)
     * @return
     */

    public String toJSON() {
        return "{" +
                "\"TYPE\": \"message\"," +
                "\"SENDER_ID\":\"" + senderID + "\"," +
                "\"TIMESTAMP\":" + timeStamp + "," +
                "\"MESSAGE\":\"" + message + "\"," +
                "\"CONVERSATION_ID\": \"" + conversationID + "\"," +
                "\"MESSAGE_ID\":" + messageID + "," +
                "\"DELAYED\":" + delayed + "," +
                "\"SEND_AT\":" + recieveDate +

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

    public String getConversationID() {
        return conversationID;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int j) { this.messageID=j;}

    public boolean isDelayed() {
        return delayed;
    }

    public long getRecieveDate() {
        return recieveDate;
    }

    public void setRecieveDate(long recieveDate) {
        this.recieveDate = recieveDate;
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

    public String getTimeString()
    {
        String time = new SimpleDateFormat("[dd-MM] [HH:mm]").format(timeStamp*1000);
        return time;
    }

    @Override
    public int compareTo(Message message)
    {
        return (int) (this.timeStamp - message.getTimeStamp());
    }
}
