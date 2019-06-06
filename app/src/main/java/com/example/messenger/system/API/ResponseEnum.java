package com.example.messenger.system.API;

public enum ResponseEnum {

    OK(200, "Successfully executed request"),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    GONE(410),
    NOT_FOUND(404),
    SERVER_ERROR(500),
    IM_A_TEAPOT(418, "If you see this, then probably something has logically gone wrong. Check exception trace. ");

    private int code;
    private String desc;

    ResponseEnum(int code, String desc)
    {
        this(code);
        this.desc = desc;
    }

    ResponseEnum(int code)
    {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


    public String getDesc() {
        return this.desc;
    }


}
