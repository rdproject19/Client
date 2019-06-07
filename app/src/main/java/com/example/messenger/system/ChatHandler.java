package com.example.messenger.system;

import com.example.messenger.Global;

import org.java_websocket.enums.ReadyState;

import java.net.URI;


public class ChatHandler
{

    private CommunicationHandler ch;
    Socket socket;
    Global global;

    public ChatHandler(Global global)
    {
        this.ch = new CommunicationHandler(global);
        this.global = global;
        connect();
    }

    private void connect() {
        try {
            URI uri = new URI("ws://134.209.205.126:7070");
            socket = new Socket(uri, this.ch, global);
            socket.connect();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public void sendMessage(Message message) {
        ReadyState state;
        do {
            state = socket.getReadyState();
            connect();
        } while (state.compareTo(ReadyState.OPEN) != 0);
        socket.send(message.toJSON());
    }



    public CommunicationHandler ch()
    {
        return this.ch;
    }

    public void sendUpdateRequest() {
        ReadyState state;
        do {
            state = socket.getReadyState();
            connect();
        } while (state.compareTo(ReadyState.OPEN) != 0);

        socket.send("{" +
                "\"TYPE\":\"update\"," +
                "\"SENDER_ID\": \"" + global.getUserData().getString(Keys.USERNAME) + "\"}");
    }
}

