package com.example.messenger.system.API;

import com.example.messenger.system.API.util.WebResource;
import com.example.messenger.system.GFG;

import java.util.HashMap;

public class User extends WebAPI
{
    /** Section of the API */
    private static final String SECTION = "user/";


    /**
     * Expected result: {@link ResponseEnum#OK} or {@link ResponseEnum#UNAUTHORIZED}
     * @param username username
     * @param password password
     * @return {@link ResponseEnum}
     */
    public ResponseEnum login(String username, String password)
    {
        final String sha512 = GFG.encryptThisString(password);
        HashMap<String, String> params = new HashMap<>();
        params.putIfAbsent("uname", username);
        params.putIfAbsent("pass", sha512);
        return this.sendPostRequest(SECTION, "login", params);

    }
    /**
     * Expected result: {@link ResponseEnum#OK} or {@link ResponseEnum#GONE}
     * @param username username
     * @return String full name of user if successful
     */
    public String getFullName(String username)
    {
        HashMap<String, String> params = new HashMap<>();
        params.putIfAbsent("uname", username);
        return this.sendGetRequest(SECTION, "name", params);
    }

    /**
     * change user profile data on server
     * Expected result:
     * {@link ResponseEnum#OK} On success,
     * {@link ResponseEnum#GONE} Probably the user does not exist,
     * {@link ResponseEnum#BAD_REQUEST} Probably an illegal argument was passed
     *
     * @param fullname new fullname
     * @param password new password
     * @param username new username
     * @return {@link ResponseEnum}
     *
     *
     *  @todo think about default param values such that a single attribute may be changed
     */
    public ResponseEnum editUserServlet(String username, String fullname, String password)
    {
        final String sha512 = GFG.encryptThisString(password);
        HashMap<String, String> params = new HashMap<>();
        params.putIfAbsent("uname", username);
        params.putIfAbsent("pwd", sha512);
        params.putIfAbsent("fullname", fullname);
        return this.sendPostRequest(SECTION, "edit", params);
    }



    public ResponseEnum createUser(String username, String fullname, String password)
    {
        final String sha512 = GFG.encryptThisString(password);
        HashMap<String, String> params = new HashMap<>();
        params.putIfAbsent("uname", username);
        params.putIfAbsent("pwd", sha512);
        params.putIfAbsent("fullname", fullname);
        params.putIfAbsent("hasImage", "false");
        return this.sendPostRequest(SECTION, "create", params);
    }



}
