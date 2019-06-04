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
public class WebAPI {

    /**
     * The IP of the server on which the http server runs
     */
    private String IP;
    /**
     * The port of the web server application
     */
    private int port;

    /** Holds latest response from server, necessary because we cannot
     * access accessor methods that we add to anonymous methods.
     */
    private static JSONObject latestResponse;


    /**
     * Constructor
     *
     * @param IP   {@see #IP}
     * @param port {@see #port}
     */
    public WebAPI(String IP, int port) {
        this.IP = IP;
        this.port = port;
    }

    /**
     * Let the server know we want a conversation with this list of participants.
     * On success it will return true, if false then somehow the conversation could not
     * be initialised.
     * @param participantId A list of participant IDs
     * @param isGroup If it is a group
     * @return True on success
     */
    public boolean createConversation(List<String> participantId, boolean isGroup) {

        StringBuilder sb = new StringBuilder();
        for (String p : participantId) {
            sb.append(p);
            sb.append(",");
        }


        String url = this.IP + ":" + this.port + "/conversations/create";
        JSONObject request = new JSONObject();
        try {
            request.put("members", sb.toString());
            request.put("isgroup", isGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest r = new JsonObjectRequest(Request.Method.GET, url, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        WebAPI.latestResponse = response;
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    WebAPI.latestResponse = new JSONObject().put("error", true);
                } catch (Exception e)  {
                    e.printStackTrace();
                }
                System.out.println("An error occurred, could not get response from HTTP Api (Create Conversation)");
            }
        });

        return !latestResponse.has("error");


    }


    /**
     * Authenticate user with given credentials
     * @param userID Username/UserID
     * @param password Plain text password, will be hashed before sending over internet
     * @return boolean
     */
    public boolean userLogin(String userID, String password)
    {
        String sha256 = GFG.encryptThisString(password);
        String url = this.IP + ":" + this.port + "/user/login";
        JSONObject request = new JSONObject();
        try {
            request.put("uname", userID);
            request.put("pass", sha256);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest r = new JsonObjectRequest(Request.Method.GET, url, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        WebAPI.latestResponse = response;
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    WebAPI.latestResponse = new JSONObject().put("error", true);
                } catch (Exception e)  {
                    e.printStackTrace();
                }
                System.out.println("An error occurred, could not get response from HTTP Api (UserLogin())");
            }
        });


        return !latestResponse.has("error");
    }


    /**
     * Request server to create a user with these parameters.
     * @param userID Username
     * @param password Password
     * @param fullname Fullname
     * @param hasImage If the user has a profile pciture
     * @return true on success
     */
    public boolean createUser(String userID, String password, String fullname, boolean hasImage)
    {
        String sha256 = GFG.encryptThisString(password);
        String url = this.IP + ":" + this.port + "/user/new";
        JSONObject request = new JSONObject();
        try {
            request.put("uname", userID);
            request.put("pwd", sha256);
            request.put("fullname", fullname);
            request.put("hasImage", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest r = new JsonObjectRequest(Request.Method.GET, url, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        WebAPI.latestResponse = response;
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    WebAPI.latestResponse = new JSONObject().put("error", true);
                } catch (Exception e)  {
                    e.printStackTrace();
                }
                System.out.println("An error occurred, could not get response from HTTP Api (createUser())");
            }
        });


        return !latestResponse.has("error");

    }



}
