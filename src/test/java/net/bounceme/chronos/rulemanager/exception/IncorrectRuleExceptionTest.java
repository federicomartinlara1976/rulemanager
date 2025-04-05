package net.bounceme.chronos.rulemanager.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IncorrectRuleExceptionTest {

    @Test
    void incorrectRuleException_HasCorrectMessage() {
        IncorrectRuleException exception = new IncorrectRuleException("Rule is incorrect");
        Assertions.assertEquals("Rule is incorrect", exception.getMessage());
    }
}
