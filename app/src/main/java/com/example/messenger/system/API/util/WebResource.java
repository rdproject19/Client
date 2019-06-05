package com.example.messenger.system.API.util;

import java.time.chrono.MinguoEra;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
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

    /**
     * Send query to server and store results in {@link #lastResponseBody} and {@link #lastResponseCode}
     * @param Method POST or GET
     * @throws Exception Throws whenever the request could not be executed
     */
    public void execute(String Method) throws Exception
    {
        URL obj = new URL(this.url);
        HttpURLConnection con;

        String params = mapToParams(this.arguments);

        if(Method.equals("POST") || Method.equals("PUT") || Method.equals("DELETE") /* Sending post data */) {
            // Send post request
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(Method);
            // adding all headers to request
            con.setRequestProperty("User-Agent", USER_AGENT);
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                final String key = entry.getKey();
                final String value = entry.getValue();
                con.setRequestProperty(key, value);
            }

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(params);
            wr.flush();
            wr.close();
            System.out.println("Post parameters : " + params);
        } else if (Method.equals("GET")) {
            obj = new URL(this.url + "?" + params);
            con = (HttpURLConnection) obj.openConnection();
            // adding all headers to request
            con.setRequestProperty("User-Agent", USER_AGENT);
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                final String key = entry.getKey();
                final String value = entry.getValue();
                con.setRequestProperty(key, value);
            }

            con.setRequestMethod(Method);
        } else {
            throw new IllegalArgumentException("Invalid HTTP method " + Method);
        }


        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null)
        {
            response.append(inputLine);
        }
        in.close();

        this.lastResponseBody = response.toString();
        this.lastResponseCode = responseCode;
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
