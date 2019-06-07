package com.example.messenger.system.http;

import okhttp3.Response;

public class ResponseException extends Exception
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
