package com.example.messenger.system;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;


public class SocketHandler
{

    SocketHandler()
    {
        //7070 134.209.205.126
        InetSocketAddress IA = null;
        try {
            InetSocketAddress().getAddress("134.209.205.126");
            IA = InetSocketAddress("134.209.205.126", "7070");
        } catch(Exception e) {
            e.printStackTrace();
        }

        WebSocketServer ws = new WebSocketServer(IA) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {

            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {

            }

            @Override
            public void onMessage(WebSocket conn, String message) {

            }

            @Override
            public void onError(WebSocket conn, Exception ex) {

            }

            @Override
            public void onStart() {

            }
        };

    }

}
