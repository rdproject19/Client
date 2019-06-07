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

    @Test
    public void createUser() {
        try {
            boolean f = new User().createUser("koen2", "hallo", "ja");
            assertEquals(true, f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}