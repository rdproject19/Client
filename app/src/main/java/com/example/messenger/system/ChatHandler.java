package com.example.messenger.system;

import com.example.messenger.Global;

import org.java_websocket.enums.ReadyState;

import java.net.URI;


public class ChatHandler
{

    private CommunicationHandler ch;
    Socket socket;

    public ChatHandler(Global global)
    {
        this.ch = new CommunicationHandler(global);

        URI uri = null;
        try {
            uri = new URI("ws://134.209.205.126:7070");
            socket = new Socket(uri, this.ch, global);
            socket.connect();
            ReadyState state = socket.getReadyState();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void sendMessage(Message message) {
        socket.send(message.toJSON());
    }


    public CommunicationHandler ch()
    {
        return this.ch;
    }

}

