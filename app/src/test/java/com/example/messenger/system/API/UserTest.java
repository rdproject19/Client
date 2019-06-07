package com.example.messenger.system.API;

import com.example.messenger.system.http.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void getFullName() throws Exception {
        User uq = new User();
        String name = uq.getName("koen");
        assertEquals(name, "ja");
    }
}