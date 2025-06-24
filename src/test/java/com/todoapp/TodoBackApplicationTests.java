package com.todoapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "JWT_SECRET=miSuperSecretoUltraLargo123@@12345678")
class TodoBackApplicationTests {

    @Test
    void contextLoads() {
    }

}
