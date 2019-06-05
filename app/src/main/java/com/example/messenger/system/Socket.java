package com.example.messenger.system;

import android.content.Context;

import com.example.messenger.Global;
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

    private void handleUpdate(JSONObject jsonUpdate) {
        Gson gson = new Gson();

        try {
            if (jsonUpdate.has("NEW_CONVERSATIONS")) {

                for(JSONObject convo : gson.fromJson((JsonElement) jsonUpdate.get("NEW_CONVERSATIONS"),JSONObject[].class)) {
                    handleConversation(convo);
                }

            }
            if (jsonUpdate.has("NEW_MESSAGES")) {

                for(JSONObject message : gson.fromJson((JsonElement) jsonUpdate.get("NEW_MESSAGES"),JSONObject[].class)) {
                    handleMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                conv = new Conversation(convId);
                ch.putConversation(conv);
            }

            // Stores the message in the database
            global.db().messageDao().putMessage(msg);

            // Sends a receipt to confirm it was recieved
            sendReceipt(msg);

            // Puts the message in the correct conversation
            conv.putMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendReceipt(Message msg) {
        this.send(
                    "{TYPE=\"receipt\"," +
                         "MESSAGE_ID=" + msg.getMessageID() +
                         "}"
        );
    }

    private void handleConversation(JSONObject jsonConvo) {
        Gson gson = new Gson();
        String userName = global.getUserData().getString(Keys.FULLNAME);

        try {
            if (jsonConvo.has("PARTICIPANTS"))
            {
                String convId = jsonConvo.getString("CONVERSATION_ID");
                Conversation conv;
                if(this.ch.conversationExists(convId)) {
                    conv = ch.getConversation(convId);
                } else {
                    conv = new Conversation(convId);
                    ch.putConversation(conv);
                }
                for (String name : gson.fromJson((JsonElement) jsonConvo.get("PARTICIPANTS"), String[].class)) {
                    if (name != userName) {
                        conv.addParticipant(name);
                    }
                }

                global.db().conversationDao().putConversation(conv);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        String seed = prefs.getString(Keys.SEED);
        long counter = prefs.getLong(Keys.COUNTER);
        LSFR lsfr = new LSFR(seed, counter);
        int authToken = lsfr.shift();
        prefs.setInt(Keys.TOKEN, authToken);
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
        this.send("{TYPE:\"message\", SENDER_ID:\"koen1\", TIMESTAMP:12345656, MESSAGE:\"Hello\", " +
                        "CONVERSATION_ID:\"5cf0f1c78bd43f6613fbe21e\", MESSAGE_ID:12345}");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
