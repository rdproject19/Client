package com.example.messenger.system.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.messenger.system.GFG;

import okhttp3.Response;

public class Image
{
    private final String HOSTNAME = "134.209.205.126:8080";
    private final String GROUPNAME = "images";

    /**
     * Uploads an image
     * @param id Group/User id
     * @param type 'group' or 'user'
     * @param image The image, encoded as base64
     * @return The image id, or empty if error
     * @throws ResponseException
     */
    public String updateImage(String id, String type, String image) throws ResponseException
    {
        REST rest = new REST(HOSTNAME, GROUPNAME, "/");
        rest.bindParam("id", id);
        rest.bindParam(" type", type);
        rest.bindParam("data", image);
        if (rest.PUT()) {
            return rest.getContents();
        } else {
            if (rest.getResponse().code() == HttpStatus.InternalServerError.getCode()) {
                return "";
            } else if (rest.getResponse().code() == HttpStatus.Gone.getCode()) {
                return "";
            }
        }
        throw new ResponseException(rest.getResponse());
    }

    /**
     * Downloads an image
     * @param id group/user id
     * @param type 'group' or 'user'
     * @return The filename to the downloaded image, or nothing if there was an error
     * @throws IOException
     */
    public String getImage(String id, String type) throws IOException {
        REST rest = new REST(HOSTNAME, GROUPNAME, "/");
        rest.bindParam("id", id);
        rest.bindParam("type", type);
        if (rest.GET()) {
            String filename = id + "_img.jpg";
            if (saveImageToFile(rest.getContents(), filename)) {
                return filename;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    private boolean saveImageToFile(String data, String filename) {
        byte[] decoded = Base64.decode(data, Base64.DEFAULT);

        File f = new File(Environment.getExternalStorageDirectory() + File.pathSeparator + filename);
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(decoded);
            fo.flush();
            fo.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}