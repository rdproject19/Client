package com.example.messenger.system.http;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testGetUser() {
        try {
            String nm = new User().getName("koen");
            assertEquals("ja", nm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}