package com.example.messenger.system.API.util;

import android.os.StrictMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.impl.client.*;

import java.io.InputStream;
import java.time.chrono.MinguoEra;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.lang.Exception;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author Karim Abdulahi
 */
public class WebResource
{
    private String url;
    private final String USER_AGENT = "Mozilla/5.0";

    private HashMap<String, String> headers;
    private HashMap<String, String> arguments = new HashMap<>();
    private String lastResponseBody;
    private int lastResponseCode;

    /**
     * Public constructor, place in URL to what resource you want to send a request.
     * @param url
     */
    public WebResource(String url)
    {
        this.url = url;
        this.headers = new HashMap<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    /**
     * Add a header to the request
     * @param fieldName Header field name
     * @param recordValue Value of this record with fieldName
     */
    public void addHeader(String fieldName, String recordValue)
    {
        this.headers.putIfAbsent(fieldName, recordValue);
    }
    /**
     * Add a argument to the request
     * @param fieldName Argument field name
     * @param recordValue Value of this record with fieldName
     */
    public void addArgument(String fieldName, String recordValue)
    {
        this.arguments.putIfAbsent(fieldName, recordValue);
    }


    public void executePost(String method) throws Exception
    {
        if(!(method.equals("POST") || method.equals("PUT") || method.equals("DELETE"))) {
            throw new IllegalArgumentException("Post requests may only be POST, PUT and DELETE");
        }
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(this.url);

        List<NameValuePair> params = new ArrayList<NameValuePair>(this.arguments.size());

        for (Map.Entry<String, String> entry : this.arguments.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        httppost.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = httpclient.execute(httppost);
        this.lastResponseCode = response.getStatusLine().getStatusCode();

        HttpEntity entity = response.getEntity();

        StringBuilder output = new StringBuilder();
        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
                // do something useful
                output.append(instream);
            }
        }
        this.lastResponseBody = output.toString();

    }

    public void executeGet() throws Exception {
        URL obj = new URL(this.url + "?" + mapToParams(arguments));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        this.lastResponseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + this.lastResponseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        this.lastResponseBody = response.toString();
    }


    private static String mapToParams(Map<String, String> params) {
        StringBuilder urlParams = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlParams.append(entry.getKey());
            urlParams.append("=");
            urlParams.append(entry.getValue());
            urlParams.append("&");
        }
        urlParams.deleteCharAt(urlParams.length()-1);

        return urlParams.toString();
    }

    public int getResponseCode() { return this.lastResponseCode; }
    public String getResponseBody() { return this.lastResponseBody; }

}
