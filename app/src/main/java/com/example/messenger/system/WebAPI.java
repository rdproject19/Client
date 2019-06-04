package com.example.messenger.system;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

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



    }

    private String getResponse(String IP, int port) throws Exception
    {

        String url = null;

        JsonObjectRequest r = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("An error occurred, could not get response from HTTP Api");
            }
        });
        return "";

    }

    public boolean createConversation(List<String> participantId, boolean isGroup)
    {
        StringBuilder sb = new StringBuilder();
        for(String p : participantId)
        {
            sb.append(p + ",");
        }


        return true;

    }





}
