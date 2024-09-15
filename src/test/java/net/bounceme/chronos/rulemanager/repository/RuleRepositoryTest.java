package net.bounceme.chronos.rulemanager.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import net.bounceme.chronos.app.usuarios.commons.model.Access;
import net.bounceme.chronos.app.usuarios.commons.model.Method;
import net.bounceme.chronos.app.usuarios.commons.model.Rule;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RuleRepositoryTest {

    @Mock
    private RuleRepository ruleRepository;

    @Test
    void getByCreationDate_ReturnsRules_WhenRulesExistInDateRange() {
        Date from = new Date();
        Date to = new Date();
        Rule rule = new Rule();
        when(ruleRepository.getByCreationDate(from, to)).thenReturn(List.of(rule));

        List<Rule> result = ruleRepository.getByCreationDate(from, to);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(rule, result.get(0));
    }

    @Test
    void getByCreationDate_ReturnsEmptyList_WhenNoRulesExistInDateRange() {
        Date from = new Date();
        Date to = new Date();
        when(ruleRepository.getByCreationDate(from, to)).thenReturn(Collections.emptyList());

        List<Rule> result = ruleRepository.getByCreationDate(from, to);

        assertTrue(result.isEmpty());
    }

    @Test
    void getByMethod_ReturnsRules_WhenRulesExistForMethod() {
        String methodName = "GET";
        Rule rule = new Rule();
        when(ruleRepository.getByMethod(methodName)).thenReturn(List.of(rule));

        List<Rule> result = ruleRepository.getByMethod(methodName);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(rule, result.get(0));
    }

    @Test
    void getByMethod_ReturnsEmptyList_WhenNoRulesExistForMethod() {
        String methodName = "GET";
        when(ruleRepository.getByMethod(methodName)).thenReturn(Collections.emptyList());

        List<Rule> result = ruleRepository.getByMethod(methodName);

        assertTrue(result.isEmpty());
    }

    @Test
    void getByAccess_ReturnsRules_WhenRulesExistForAccess() {
        String accessName = "ADMIN";
        Rule rule = new Rule();
        when(ruleRepository.getByAccess(accessName)).thenReturn(List.of(rule));

        List<Rule> result = ruleRepository.getByAccess(accessName);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(rule, result.get(0));
    }

    @Test
    void getByAccess_ReturnsEmptyList_WhenNoRulesExistForAccess() {
        String accessName = "ADMIN";
        when(ruleRepository.getByAccess(accessName)).thenReturn(Collections.emptyList());

        List<Rule> result = ruleRepository.getByAccess(accessName);

        assertTrue(result.isEmpty());
    }

    @Test
    void findRule_ReturnsRule_WhenRuleExistsForPathMethodAndAccess() {
        String path = "/api/resource";
        Method method = new Method();
        Access access = new Access();
        Rule rule = new Rule();
        when(ruleRepository.findRule(path, method, access)).thenReturn(rule);

        Rule result = ruleRepository.findRule(path, method, access);

        assertNotNull(result);
        assertEquals(rule, result);
    }

    @Test
    void findRule_ReturnsNull_WhenNoRuleExistsForPathMethodAndAccess() {
        String path = "/api/resource";
        Method method = new Method();
        Access access = new Access();
        when(ruleRepository.findRule(path, method, access)).thenReturn(null);

        Rule result = ruleRepository.findRule(path, method, access);

        assertNull(result);
    }
}
