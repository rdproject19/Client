package com.example.messenger.system;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * A class that is compatible with the http API of the server.
 * This class is used to, for example, retrieve a list of conversations
 * and create new conversations.
 */
public class WebAPI
{

    /** The IP of the server on which the http server runs */
    private String IP;
    /** The port of the web server application */
    private int port;

    private URL url;
    private URLConnection con;


    /**
     * Constructor
     * @param IP {@see #IP}
     * @param port {@see #port}
     */
    public WebAPI(String IP, int port)
    {
        this.IP = IP;
        this.port = port;

        try {

            this.url = new URL("http://" + this.IP + ":" + this.port);
            this.con = url.openConnection();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public boolean createConversation(List<String> participantId, boolean isGroup)
    {
        StringBuilder sb = new StringBuilder();
        for(String p : participantId)
        {
            sb.append(p + ",");
        }




    }





}
