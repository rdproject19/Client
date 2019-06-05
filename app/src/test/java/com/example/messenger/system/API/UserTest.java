package com.example.messenger.system.API;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void getFullName() {
        User uq = new User();
        String name = uq.getFullName("koen");
        assertEquals(name, "ja");
    }
}