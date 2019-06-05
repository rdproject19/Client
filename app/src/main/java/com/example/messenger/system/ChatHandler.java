package com.example.messenger.system;

import android.content.Context;

import com.example.messenger.Global;

import java.net.InetAddress;
import java.net.InetSocketAddress;
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
            uri = new URI("ws://35.180.29.4:8080");
            socket = new Socket(uri, this.ch, global);
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

