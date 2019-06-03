package com.example.messenger.system;

import android.content.Context;

import com.example.messenger.Global;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.*;
import java.net.InetSocketAddress;
import java.net.URI;

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
            int convId = msg.getConversationID();

            Conversation conv;
            if(this.ch.conversationExists(convId)) {
                conv = this.ch.getConversation(convId);
            } else {
                this.send(String.format("{TYPE: \"conversation_request\", CONVERSATION_ID:%d}", convId));
                conv = new Conversation(convId);
                ch.putConversation(conv);
            }

            global.db().messageDao().putMessage(msg);

            //conv.putMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleConversation(JSONObject jsonConvo) {
        Gson gson = new Gson();

        try {
            if (jsonConvo.has("PARTICIPANTS"))
            {
                int convId = jsonConvo.getInt("CONVERSATION_ID");
                Conversation conv;
                if(this.ch.conversationExists(convId)) {
                    conv = ch.getConversation(convId);
                } else {
                    conv = new Conversation(convId);
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

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Gson gson = new Gson();
        UserData prefs = new UserData(global.getApplicationContext());

        //making the handshake
        String userId = prefs.getString(Keys.USERNAME);
        LSFR lsfr = new LSFR(prefs.getString(Keys.SEED), prefs.getLong(Keys.COUNTER));
        int authToken = lsfr.shift();
        prefs.setInt(Keys.TOKEN, authToken);

        String myHandshake =
                "{TYPE: \"handshake\"," +
                        "USER_ID:\"" +
                        userId +
                        "\", AUTHENTICATION_TOKEN: " +
                        authToken +
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
                case "update": handleUpdate(json); break;
                case "message": handleMessage(json); break;
                case "conversation" : handleConversation(json); break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
