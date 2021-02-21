package com.favoritemedium.sttops;


//import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
//@RunWith(SpringRunner.class)
public abstract class BaseTest {

    static {
        System.setProperty("SPRING_PROFILES_ACTIVE", "test");
        System.setProperty("spring.profiles.active", "test");
    }
}
