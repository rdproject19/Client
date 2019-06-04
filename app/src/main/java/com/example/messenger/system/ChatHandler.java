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
        this.ch = new CommunicationHandler();

        URI uri = null;
        try {
            uri = new URI("ws://134.209.205.126:7070");
            Socket socket = new Socket(uri, this.ch, global);
            socket.connect();
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
