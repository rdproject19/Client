package com.example.messenger.system;

import java.net.InetAddress;
import java.net.InetSocketAddress;


public class ChatHandler
{

    CommunicationHandler ch;

    ChatHandler()
    {
        this.ch = new CommunicationHandler();

        InetSocketAddress IA = null;
        try {
            InetAddress addr = InetAddress.getByName("134.209.205.126");
            IA = new InetSocketAddress(addr, 7070);
        } catch(Exception e) {
            e.printStackTrace();
        }

        Socket socket = new Socket(IA, this.ch);

    }

}
