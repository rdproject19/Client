package com.example.messenger.system.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.messenger.system.GFG;

import okhttp3.Response;

public class Conversation {
    private final String HOSTNAME = "";
    private final String GROUPNAME = "conversations";

    public String createConversation(List<String> members, boolean group) throws ResponseException {
        REST request = new REST(HOSTNAME, GROUPNAME, "new");
        request.bindParam("members", String.join(";", members));
        request.bindParam("isgroup", Boolean.toString(group));
        if (request.POST())
            return request.getContents();
        else {
            if (request.getResponse().code() == HttpStatus.BadRequest.getCode()) {
                return "";
            } else if (request.getResponse().code() == HttpStatus.Gone.getCode()) {
                return "";
            }
        }
        throw new ResponseException(request.getResponse());
    }

    public String getConversation(String gid) throws ResponseException {
        REST request = new REST(HOSTNAME,  GROUPNAME, "/");
        request.bindParam("gid", gid);
        if (request.GET()) {
            return request.getContents();
        } else {
            if (request.getResponse().code() == HttpStatus.Gone.getCode()) {
                return "";
            }
        }
        throw new ResponseException(request.getResponse());
    }

    public List<String> getConversationMembers(String gid) throws ResponseException {
        REST request = new REST(HOSTNAME, GROUPNAME, "members");
        request.bindParam("gid", gid);
        if (request.GET()) {
            return Arrays.asList(request.getContents().split(";"));
        } else {
            if (request.getResponse().code() == HttpStatus.Gone.getCode()) {
                return null;
            }
        }
        throw new ResponseException(request.getResponse());
    }

    public boolean addConversationMember(String gid, String contact) throws ResponseException {
        REST request = new REST(HOSTNAME, GROUPNAME, "members/edit");
        request.bindParam("gid", gid);
        request.bindParam("contact", contact);
        if (request.PUT()) {
            return true;
        } else {
            if (request.getResponse().code() == HttpStatus.Gone.getCode()) {
                return false;
            } else if (request.getResponse().code() == HttpStatus.NotModified.getCode()) {
                return false;
            }
        }
        throw new ResponseException(request.getResponse());
    }

    public boolean removeConversationMember(String gid, String contact) throws ResponseException {
        REST request = new REST(HOSTNAME, GROUPNAME, "members/edit");
        request.bindParam(" gid", gid);
        request.bindParam("contact", contact);
        if (request.DELETE()) {
            return true;
        } else {
            if (request.getResponse().code() == HttpStatus.Gone.getCode()) {
                return false;
            } else if (request.getResponse().code() == HttpStatus.NotModified.getCode()) {
                return false;
            }
        }
        throw new ResponseException(request.getResponse());
    }

    public boolean editConversation(HashMap<String, String> parameters) throws ResponseException {
        REST request = new REST(HOSTNAME, GROUPNAME, "edit");
        for(Map.Entry<String, String> pair : parameters.entrySet())
            request.bindParam(pair.getKey(), pair.getValue());

        if(request.POST())
            return true;
        else if (request.getHttpStatus() == HttpStatus.Gone || request.getHttpStatus() == HttpStatus.BadRequest)
            return false;

        throw new ResponseException(request.getResponse());
    }
}