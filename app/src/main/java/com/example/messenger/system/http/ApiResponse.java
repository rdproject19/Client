package com.example.messenger.system.http;

public class ApiResponse {

    HttpStatus status;
    String response;

    public ApiResponse(HttpStatus s, String r) {
        this.status = s;
        this.response = r;
    }
}
