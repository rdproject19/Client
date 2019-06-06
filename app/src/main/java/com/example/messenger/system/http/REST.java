package com.example.messenger.system.http;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class REST
{
	protected String hostname;
	protected HashMap<String, String> params;
	
	/** @var The group of commands on the server, like User.* or Conversation.*; */
	protected String group; 
	/** @var The specfic instruction from this group */
	protected String instruction;
	
	protected HttpUrl httpUrl;
	protected static OkHttpClient client = new OkHttpClient();
	
	protected String lastResponseBody;
	protected Response lastResponse;

	/**
	 * [_url]/[_group]/[_instruction]
	 * @param _url
	 * @param _group
	 * @param _instruction
	 */
	public REST(String _host, String _group, String _instruction )
	{
		params 		= new HashMap<>();
		hostname 	= _host;
		group 		= _group;
		instruction = _instruction;
		
		httpUrl = new HttpUrl.Builder()
			       .scheme("http")
			       .host(hostname)
			       .addPathSegment(_group)
			       .addPathSegment(_instruction)
			       .build();
	}
	/**
	 * To bind, for example, headers and arguments to the request
	 * @param $key
	 * @param $value
	 */
	public void bindParam(String $key, String $value)
	{
		params.put($key, $value);
	}
	
	/**
	 * If there are parameters, it will generate a querystring.
	 * if not parameters are present, it will return an empty string. 
	 * @return Query String
	 */
	protected String getQueryString()
	{
		if(params.size() == 0)
		{
			System.out.println("This request has no parameters. Will return an empty string.");
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry : params.entrySet())
		{
			sb.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	/** 
	 * @return True if request could successfully be sent
	 */
	public boolean POST()
	{
		RequestBody requestBody = RequestBody.create(getQueryString(), MediaType.get("application/x-www-form-urlencoded"));
		Request request = new Request.Builder()
		        .url(httpUrl)
		        .post(requestBody)
		        .build();
		try {
			lastResponse = client.newCall(request).execute();
			lastResponseBody = lastResponse.body().string();
			if(lastResponse.isSuccessful())
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	/** 
	 * @return True if request could successfully be sent
	 */
	public boolean GET()
	{
		Request request = new Request.Builder()
		        .url(httpUrl + "?" + getQueryString())
		        .build();
		try {
			lastResponse = client.newCall(request).execute();
			lastResponseBody = lastResponse.body().string();
			if(lastResponse.isSuccessful())
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	/** 
	 * @return True if request could successfully be sent
	 */
	public boolean DELETE()
	{
		Request request = new Request.Builder()
				.delete()
		        .url(httpUrl + "?" + getQueryString())
		        .build();
		try {
			lastResponse = client.newCall(request).execute();
			lastResponseBody = lastResponse.body().string();
			if(lastResponse.isSuccessful())
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	
	public String getContents() {
		return lastResponseBody;
	}
	
	public Response getResponse() {
		return lastResponse;
	}
}


