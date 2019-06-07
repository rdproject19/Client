package com.example.messenger.system.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.messenger.system.GFG;

import okhttp3.Response;

public class User
{
	private final String HOSTNAME  = "134.209.205.126:8080";
	private final String GROUPNAME = "user";

	/**
	 * Check user credentials
	 * @param username Username
	 * @param password Password
	 * @return True if the credentials were correct, false if otherwise
	 * @throws Exception
	 */
	public boolean userLogin(String username, String password) throws Exception
	{
		REST request = new REST(HOSTNAME, GROUPNAME, "login");
		request.bindParam("uname", username);
		request.bindParam("pass", password);
		request.POST();
		
		if(request.getResponse().code() == HttpStatus.OK.getCode())
			return true;
		else if(request.getResponse().code() == HttpStatus.Unauthorized.getCode())
			return false;

		throw new ResponseException(request.getResponse());
	}

	/**
	 * Gets a users full name
	 * @param username The username
	 * @return The users full name
	 * @throws Exception
	 */
	public String getName(String username) throws Exception
	{
		REST request = new REST(HOSTNAME, GROUPNAME, "name");
		request.bindParam("uname", username);
		if (request.GET())
			return request.getContents();
		throw new ResponseException(request.getResponse());
	}

	/**
	 * Creates a new user
	 * @param username Username
	 * @param password Password
	 * @param fullname Full name
	 * @return Success or not
	 * @throws Exception
	 */
	public boolean createUser(String username, String password, String fullname) throws Exception
	{
		return createUser(username, password, fullname, false, "");
	}

	/**
	 * Creates a new user
	 * @param username Username
	 * @param password Password
	 * @param fullname Full name
	 * @param hasImage Whether or not this user has an image
	 * @param image The image id (returned by upload image)
	 * @return Success or not
	 * @throws Exception
	 */
	public boolean createUser(String username, String password, String fullname, boolean hasImage, String image) throws Exception
	{
		REST request = new REST(HOSTNAME, GROUPNAME, "new");
		request.bindParam("uname", 	username);
		request.bindParam("pwd", 		GFG.encryptThisString(password));
		request.bindParam("fullname", username);
		request.bindParam("hasimage", Boolean.toString(hasImage));
		if(hasImage)
			request.bindParam("image", image);
		if(request.POST())
			return true;
		else {
			if(request.getResponse().code() == HttpStatus.Conflict.getCode()) {
				return false;
			}
			throw new ResponseException(request.getResponse());
		}
	}

	/**
	 * Get users contacts
	 * @param username username
	 * @return arraylist with usernames of contacts
	 * @throws Exception if unexpected behavior is encountered.
	 */
	public List<String> getUserContacts(String username) throws Exception
	{
		REST request = new REST(HOSTNAME, GROUPNAME, "contacts");
		request.bindParam("uname", username);
		request.GET();
		HttpStatus status = HttpStatus.getByCode(request.getResponse().code());
		if(status == HttpStatus.NoContent) {
			return new ArrayList<>();
		} else if(status != HttpStatus.OK) {
			throw new ResponseException(request.getResponse());
		}

		return (ArrayList<String>) Arrays.asList(request.getContents().split(","));
	}

	/**
	 * Adds a user to the contact list
	 * @param username Username
	 * @param contact User to add
	 * @return Success or not
	 * @throws Exception
	 */
	public boolean addUserContact(String username, String contact) throws Exception
	{
		REST request = new REST(HOSTNAME, GROUPNAME, "contacts/edit");
		request.bindParam("uname", username);
		request.bindParam("contact", contact);
		if(request.PUT())
			return true;
		if(request.getHttpStatus() == HttpStatus.Gone) {
			return false;
		}
		if(request.getHttpStatus() == HttpStatus.NotModified) {
			return false;
		}
		if(request.getHttpStatus() == HttpStatus.Accepted) {
			return false;
		}


		throw new ResponseException(request.getResponse());
	}

	/**
	 * Removes a user from a users contact list
	 * @param username Username
	 * @param contact Contact
	 * @return Success or not
	 * @throws Exception
	 */
	public boolean deleteUserContact(String username, String contact) throws Exception
	{
		REST request = new REST(HOSTNAME, GROUPNAME, "contacts/edit");
		request.bindParam("uname", username);
		request.bindParam("contact", contact);
		if(request.DELETE())
			return true;
		if(request.getHttpStatus() == HttpStatus.Gone) {
			return false;
		}
		if(request.getHttpStatus() == HttpStatus.NotModified) {
			return false;
		}
		if(request.getHttpStatus() == HttpStatus.Accepted) {
			return false;
		}


		throw new ResponseException(request.getResponse());
	}

	/**
	 * Edits a user
	 * @param arguments Map of values to be edited
	 * @return Success or not
	 * @throws Exception
	 */
	public boolean editUser(HashMap<String, String> arguments) throws Exception
	{
		REST request = new REST(HOSTNAME, GROUPNAME, "edit");
		for(Map.Entry<String, String> pair : arguments.entrySet())
			request.bindParam(pair.getKey(), pair.getValue());

		if(request.POST())
			return true;
		else if (request.getHttpStatus() == HttpStatus.Gone || request.getHttpStatus() == HttpStatus.BadRequest)
			return false;

		throw new ResponseException(request.getResponse());
	}


	
}

class ResponseException extends Exception
{
	private Response response;
	ResponseException(Response _response) {
		response = _response;
	}

	@Override
	public String toString() {
		return "Response of server does not match protocol, got: " + HttpStatus.getByCode(response.code()).compactDesc();

	}
}