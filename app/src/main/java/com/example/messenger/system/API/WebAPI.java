package com.example.messenger.system.API;

import com.example.messenger.system.API.util.WebResource;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the abstract base class for User, Conversation and Common
 * To get the full name that belongs to a given username one would do
 * User userRequest = new User();
 * userRequest.getFullName("User ID/ Username"); ---> String (fullName)
 *
 * @author Karim Abdulahi
 */
public abstract class WebAPI {
    static final String URL = "http://134.209.205.126:8080/";
    private String lastResponseBody;
    private int lastResponseCode;


    /**
     * Find a response from the {@link ResponseEnum} enum
     * using its integer representation.
     *
     * @param code int of the response
     * @return Element of the enum this code belongs to. If code not found
     * it will hilariously let us know its a teapot *_*
     */
    public static ResponseEnum Response(int code) {
        for (ResponseEnum r : ResponseEnum.values())
            if (r.getCode() == code)
                return r;
        return ResponseEnum.IM_A_TEAPOT;
    }

    /**
     * For example, to send a login request to /user/login/?uname=123&password=1234
     * you'd do
     *
     * @param section user
     * @param command login
     * @param map     map of parameters [uname=123, password=1234]
     * @return {@link ResponseEnum} of this request
     */
    protected ResponseEnum sendPostRequest(String section, String command, HashMap<String, String> map) {
        WebResource wr = new WebResource(URL + section + command);

        for (Map.Entry<String, String> entry : map.entrySet())
            wr.addArgument(entry.getKey(), entry.getValue());

        try
        {
            wr.executePost("POST");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            lastResponseCode = 418;
            return ResponseEnum.SERVER_ERROR;
        }

        lastResponseBody = wr.getResponseBody();
        lastResponseCode = 418;


        return WebAPI.Response(wr.getResponseCode());
    }

    /**
     * Find more elegant way to handle errors with responseBodies
     */
    protected String sendGetRequest(String section, String command, HashMap<String, String> map) {
        WebResource wr = new WebResource(URL + section + command);

        for (Map.Entry<String, String> entry : map.entrySet())
            wr.addArgument(entry.getKey(), entry.getValue());

        lastResponseBody = wr.getResponseBody();
        lastResponseCode = wr.getResponseCode();

        try {
            wr.executeGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (wr.getResponseCode() == 200)
            return wr.getResponseBody();
        else
            return "SERVER ERROR: " + wr.getResponseBody();
    }

    protected ResponseEnum sendPutRequest(String section, String command, HashMap<String, String> map) {
        WebResource wr = new WebResource(URL + section + command);

        for (Map.Entry<String, String> entry : map.entrySet())
            wr.addArgument(entry.getKey(), entry.getValue());

        try {
            wr.executePost("PUT");
        } catch (Exception e) {
            e.printStackTrace();
        }

        lastResponseBody = wr.getResponseBody();
        lastResponseCode = wr.getResponseCode();
        return WebAPI.Response(wr.getResponseCode());
    }

    protected ResponseEnum sendDeleteRequest(String section, String command, HashMap<String, String> map) {
        WebResource wr = new WebResource(URL + section + command);

        for (Map.Entry<String, String> entry : map.entrySet())
            wr.addArgument(entry.getKey(), entry.getValue());

        try {
            wr.executePost("DELETE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        lastResponseBody = wr.getResponseBody();
        lastResponseCode = wr.getResponseCode();

        return WebAPI.Response(wr.getResponseCode());
    }

    public int getResponseCode() {
        return this.lastResponseCode;
    }

    public String getResponseBody() {
        return this.lastResponseBody;
    }
}

