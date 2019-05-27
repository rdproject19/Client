package com.example.messenger.system;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.*;
import java.net.InetSocketAddress;

public class Socket extends WebSocketServer {

        CommunicationHandler ch;

        public Socket(InetSocketAddress IA, CommunicationHandler ch)
        {
            super(IA);
            this.ch = ch;
        }

        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            //Send Handshake
            //Receive question
            //Send Answer
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {

        }

        @Override
        public void onMessage(WebSocket conn, String message)
        {
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
        public void onError(WebSocket conn, Exception ex) {

        }

        @Override
        public void onStart() {

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
                    this.broadcast(String.format("{TYPE: \"conversation_request\", CONVERSATION_ID:%d}", convId));
                    conv = new Conversation(convId);
                    ch.putConversation(conv);
                }

                conv.putMessage(msg);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private void handleConversation(JSONObject jsonConvo) {
            Gson gson = new Gson();

            try {
                if (jsonConvo.has("PARTICIPANTS")) {
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
}
