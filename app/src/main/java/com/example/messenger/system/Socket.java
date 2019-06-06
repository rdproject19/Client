package com.example.messenger.system;

import android.content.Context;

import com.example.messenger.Global;
import com.example.messenger.RecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.*;
import java.net.URI;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Socket extends WebSocketClient {

    CommunicationHandler ch;
    Global global;

    public Socket(URI uri, CommunicationHandler ch, Global global)
    {
        super(uri);
        this.ch = ch;
        this.global = global;
    }

    /**
     * Whenever a message with type 'update' is recieved, this function is called.
     * The 'update' message is combination of possibly multiple new conversations and/or messages.
     * The function therefor calls the handleMessage and handleConversation functions the required
     * amount of times.
     * @param jsonUpdate The message as a JSON object
     */
    private void handleUpdate(JSONObject jsonUpdate) {
        Gson gson = new Gson();

        try {
            if (jsonUpdate.has("NEW_CONVERSATIONS")) {
                JSONArray cons = jsonUpdate.getJSONArray("NEW_CONVERSATIONS");
                for (int i=0; i<cons.length(); i++) {
                    handleConversation((JSONObject) cons.get(i));
                }

            }
            if (jsonUpdate.has("NEW_MESSAGES")) {

                JSONArray msgs = jsonUpdate.getJSONArray("NEW_MESSAGES");
                for (int i=0; i<msgs.length(); i++) {
                    handleMessage((JSONObject) msgs.get(i));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Whenever a message with type 'message' is recieved, this function is called.
     * The function creates a Message that gets added to the correct conversation in memory.
     * If that conversation does not yet exist in memory, a new one is created and a message
     * to the server is created to get the participants.
     * @param jsonMessage The message as a JSON object
     */
    private void handleMessage(JSONObject jsonMessage) {
        try {
            Message msg = new Message(jsonMessage);
            String convId = msg.getConversationID();
            String userId = global.getUserData().getString(Keys.USERNAME);

            Conversation conv;
            if(this.ch.conversationExists(convId)) {
                conv = this.ch.getConversation(convId);
            } else {
                this.send("{TYPE: \"update\", USER_ID:"+ userId + "}");
                conv = Conversation.newConversation(convId, global);
                ch.putConversation(conv);
            }

            // Stores the message in the database
            global.db().messageDao().putMessage(msg);

            // Sends a receipt to confirm it was recieved
            sendReceipt(msg);

            // Puts the message in the correct conversation
            conv.putMessage(msg);

            // Updates the screen
            RecyclerViewAdapter adapter = global.getAdapter();
            if (!(adapter == null)){
                adapter.update(msg);
                adapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Sends a receipt that a Message was recieved correctly.
     * @param msg the message that was recieved (only the ID matters)
     */
    private void sendReceipt(Message msg) {
        this.send(
                    "{TYPE=\"receipt\"," +
                         "MESSAGE_ID=" + msg.getMessageID() +
                         "}"
        );
    }

    /**
     * Whenever a message with type 'conversation' is recieved, this function is called.
     * If the conversation exists in the communicationHandler, it simply adds the participants
     * to that conversation. If it did not yet exist, a new one is created.
     * @param jsonConvo The message as a JSON object
     */
    private void handleConversation(JSONObject jsonConvo) {
        Gson gson = new Gson();
        try {
            if (jsonConvo.has("PARTICIPANTS"))
            {
                String convId = jsonConvo.getString("CONVERSATION_ID");
                Conversation conv;
                if(this.ch.conversationExists(convId)) {
                    conv = ch.getConversation(convId);
                } else {
                    conv = Conversation.newConversation(convId, global);
                    ch.putConversation(conv);
                }
                for (String name : gson.fromJson((JsonElement) jsonConvo.get("PARTICIPANTS"), String[].class)) {
                    conv.addParticipant(name);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Whenever a message with type 'update' is recieved, this function is called.
     * This function sets the parsed tag of the message that was recieved to true.
     * @param jsonReceipt The message as a JSON object
     */
    private void handleReceipt(JSONObject jsonReceipt) {
        try {
            if (jsonReceipt.has("MESSAGE_ID"))
            {
                int msgId = jsonReceipt.getInt("MESSAGE_ID");
                Message msg = global.db().messageDao().getMessage(msgId);
                msg.setParsed(true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

        Gson gson = new Gson();
        UserData prefs = new UserData(global.getApplicationContext());
        //making the handshake
        String userId = prefs.getString(Keys.USERNAME);
        //String seed = prefs.getString(Keys.SEED);
        //long counter = prefs.getLong(Keys.COUNTER);
        //LSFR lsfr = new LSFR(seed, counter);
        //int authToken = lsfr.shift();
        //prefs.setInt(Keys.TOKEN, authToken);
        String myHandshake =
                "{TYPE: \"handshake\"," +
                        "USER_ID:\"" +
                        userId +
                        "\", AUTHENTICATION_TOKEN: " +
                        //authToken +
                        1337 +
                        "}";

        //Send Handshake
        this.send(myHandshake);
    }

    @Override
    public void onMessage(String message) {

        try {
            JSONObject json = new JSONObject(message);
            String type = json.getString("TYPE");
            switch (type){
                case "error": handleError(json); break;
                case "connected": handleConnected(json); break;
                case "update": handleUpdate(json); break;
                case "message": handleMessage(json); break;
                case "conversation" : handleConversation(json); break;
                case "receipt" : handleReceipt(json); break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //this.send("{TYPE:\"message\", SENDER_ID:\"koen1\", TIMESTAMP:12345656, MESSAGE:\"Hello\", " +
    //                           "CONVERSATION_ID:\"5cf0f1c78bd43f6613fbe21e\", MESSAGE_ID:12345}");

    private void handleError(JSONObject json) {
        try {
            if (json.has("CODE")) {
                int code = json.getInt("CODE");
                switch (code) {
                    case 401: sendDesync(); break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendDesync() {
        long newCount = ThreadLocalRandom.current().nextLong(10000);
        UserData prefs = new UserData(global.getApplicationContext());
        LSFR lsfr = new LSFR(prefs.getString(Keys.SEED), newCount);
        prefs.setCounter(0);
        //The new seed is the old seed shifted a random amount of times.
        prefs.setSeed(GFG.encryptThisString(new String(lsfr.getState())));
        this.send( "{TYPE:desync,USER_ID: \""
                + prefs.getString(Keys.USERNAME) + "\", " +
                "COUNT:" + newCount + "}");
    }

    private void handleConnected(JSONObject json) {
        UserData prefs = new UserData(global.getApplicationContext());
        prefs.increaseCounter();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
