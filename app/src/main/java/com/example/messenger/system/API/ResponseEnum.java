package com.example.messenger.system.API;

public enum ResponseEnum {

    OK(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    GONE(410),
    NOT_FOUND(404),
    SERVER_ERROR(500),
    IM_A_TEAPOT(418);

    private int code;

    ResponseEnum(int code)
    {
        this.code = code;
    }

    public int getCode() {
        return code;
    }



}
