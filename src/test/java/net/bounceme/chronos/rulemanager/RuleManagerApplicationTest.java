package net.bounceme.chronos.rulemanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RuleManagerApplicationTest {

    @Test
    void applicationContextLoadsSuccessfully() {
        RuleManagerApplication.main(new String[]{});
        
        Assertions.assertTrue(true);
    }
}
