package com.example.messenger.system;

import android.content.Context;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;


public class ChatHandler
{

    private CommunicationHandler ch;

    public ChatHandler(Context context)
    {
        this.ch = new CommunicationHandler();

        URI uri = null;
        try {
            uri = new URI("ws://134.209.205.126:7070");
            Socket socket = new Socket(uri, this.ch, context);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


    public CommunicationHandler ch()
    {
        return this.ch;
    }

}
