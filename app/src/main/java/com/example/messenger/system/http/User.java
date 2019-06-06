package com.example.messenger.system.http;

public class User
{
	private final String HOSTNAME  = "";
	private final String GROUPNAME = "users";
	
	public boolean userLogin(String username, String password) throws Exception
	{
		REST request = new REST(HOSTNAME, GROUPNAME, "login");
		request.bindParam("uname", username);
		request.bindParam("pass", password);
		request.POST();
		
		if(request.lastResponse.code() == HttpStatus.OK.getCode())
			return true;
		else if(request.lastResponse.code() == HttpStatus.Unauthorized.getCode())
			return false;
		
		throw new Exception("Response of server does not match protocol, got: " + HttpStatus.getByCode(request.lastResponse.code()).compactDesc());
	}


	public String getName(String username) throws Exception
	{
		REST request = new REST(HOSTNAME, GROUPNAME, "name");
		request.bindParam("uname", username);
		if(request.POST())
			return request.lastResponseBody;
		throw new Exception("Could not fetch username of user with username " + username + " from the server. ");
	}
	
	public boolean createUser(String username, String password, String fullname)
	{
		REST request = new REST(HOSTNAME, GROUPNAME, "name");
	}
	
	
	
	
}
