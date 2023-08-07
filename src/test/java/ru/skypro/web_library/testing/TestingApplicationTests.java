package ru.skypro.web_library.testing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class TestingApplicationTests {
    @Autowired
    private WebLibraryApplication webLibraryApplication;
    @Test
    void contextLoads() {
        assertThat(webLibraryApplication).isNotNull();
    }

}
