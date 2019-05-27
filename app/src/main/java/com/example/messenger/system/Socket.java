package com.example.messenger.system;

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
            try {
                if (jsonUpdate.has("NEW_CONVERSATIONS")) {

                    for(JSONObject convo : jsonUpdate.getJSONArray("NEW_CONVERSATIONS")) {

                    }

                }
                if (jsonUpdate.has("NEW_MESSAGES")) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void handleMessage(JSONObject jsonMessage) {
            Message msg = new Message(jsonMessage);
            int convId = msg.getConversationID();

            Conversation conv;
            if(this.ch.conversationExists(convId)) {
                conv = this.ch.getConversation(convId);
            } else {
                conv = new Conversation(convId);

            }
        }

        private void handleConversation(JSONObject msg) {

        }
}
