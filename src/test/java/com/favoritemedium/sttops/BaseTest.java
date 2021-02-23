package com.favoritemedium.sttops;


import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class BaseTest {

    static {
        System.setProperty("SPRING_PROFILES_ACTIVE", "test");
        System.setProperty("spring.profiles.active", "test");
    }
}
