package com.example.messenger.system.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.messenger.system.GFG;

import okhttp3.Response;

import java.awt.Image;
public class Image
{
    private final String HOSTNAME = "";
    private final String GROUPNAME = "images";

    public boolean updateImage(String id, String type, String image) throws ResponseException
    {
        REST rest = new REST(HOSTNAME, GROUPNAME, "/");
        rest.bindParam("id", id);
        rest.bindParam(" type", type);
        rest.bindParam("data", image);
        if (rest.PUT()) {
            return true;
        } else {
            if (rest.getResponse().code() == HttpStatus.InternalServerError.getCode()) {
                return false;
            } else if (rest.getResponse().code() == HttpStatus.Gone.getCode()) {
                return false;
            }
        }
        throw new ResponseException(rest.getResponse());
    }

    //public
}