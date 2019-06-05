package com.example.messenger.system.API.util;

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
    private HashMap<String, String> arguments;
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
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();


        if(Method.equals("GET"))
            con.setRequestMethod("GET");
        else if(Method.equals("POST"))
            con.setRequestMethod("POST");
        else
            throw new IllegalArgumentException("POST or GET, other methods are invalid");

        // adding all headers to request
        con.setRequestProperty("User-Agent", USER_AGENT);
        for (Map.Entry<String, String> entry : this.headers.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            con.setRequestProperty(key, value);
        }


        if(Method.equals("POST") /* Sending post data */)
        {
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            StringBuilder urlParams = new StringBuilder();

            for(Map.Entry<String, String> entry : this.arguments.entrySet())
            {
               urlParams.append(entry.getKey());
               urlParams.append("=");
               urlParams.append(entry.getValue());
               urlParams.append("&");
            }
            urlParams.deleteCharAt(urlParams.length()); /* @TODO Check if this is right */

            wr.writeBytes(urlParams.toString());
            wr.flush();
            wr.close();
            System.out.println("Post parameters : " + urlParams);

        }


        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        this.lastResponseBody = response.toString();
        this.lastResponseCode = responseCode;
    }

    public int getResponseCode() { return this.lastResponseCode; }
    public String getResponseBody() { return this.lastResponseBody; }

}
