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
            //Recieve question
            //Send Answer
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {

        }

        @Override
        public void onMessage(WebSocket conn, String message)
        {
            try {
                String type = new JSONObject(message).getString("TYPE");
                if (type.equals("message")) {
                    handleMessage(new Message(message));
                } else if (type.equals("conversation")) {
                    handleConversation(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            int convId = msg.getConversationID();


            Conversation conv;
            if(this.ch.conversationExists(convId)) {
                conv = this.ch.getConversation(convId);
            } else {
                conv = new Conversation(convId);
            }



        }

        @Override
        public void onError(WebSocket conn, Exception ex) {

        }

        @Override
        public void onStart() {

        }
}
