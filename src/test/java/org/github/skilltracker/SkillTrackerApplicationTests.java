package org.github.skilltracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SkillTrackerApplicationTests {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void testContextIsUpAndRunning() {
        assertTrue(randomServerPort > 1);
    }

}
