package net.bounceme.chronos.rulemanager.controller;

import net.bounceme.chronos.app.usuarios.commons.dto.RoleDTO;
import net.bounceme.chronos.app.usuarios.commons.dto.RuleDTO;
import net.bounceme.chronos.rulemanager.domain.UpdateRules;
import net.bounceme.chronos.rulemanager.service.RoleService;
import net.bounceme.chronos.rulemanager.service.RuleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RuleControllerTest {

    @Test
    void list_ReturnsListOfRuleDTOs_WhenRulesExist() {
        RuleService ruleService = mock(RuleService.class);
        RuleController ruleController = new RuleController();
        ReflectionTestUtils.setField(ruleController, "ruleService", ruleService);
        RuleDTO ruleDTO = new RuleDTO();
        when(ruleService.listAll()).thenReturn(List.of(ruleDTO));

        ResponseEntity<Map<String, Object>> response = ruleController.list();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(((List<?>) response.getBody().get("rules")).isEmpty());
        Assertions.assertEquals(ruleDTO, ((List<?>) response.getBody().get("rules")).get(0));
    }

    @Test
    void list_ReturnsEmptyList_WhenNoRulesExist() {
        RuleService ruleService = mock(RuleService.class);
        RuleController ruleController = new RuleController();
        ReflectionTestUtils.setField(ruleController, "ruleService", ruleService);
        when(ruleService.listAll()).thenReturn(Collections.emptyList());

        ResponseEntity<Map<String, Object>> response = ruleController.list();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(((List<?>) response.getBody().get("rules")).isEmpty());
    }

    @Test
    void countLast_ReturnsTotalCountOfRulesInLastDay() {
        RuleService ruleService = mock(RuleService.class);
        RuleController ruleController = new RuleController();
        ReflectionTestUtils.setField(ruleController, "ruleService", ruleService);
        RuleDTO ruleDTO = new RuleDTO();
        when(ruleService.listByDate(any(), any())).thenReturn(List.of(ruleDTO));

        ResponseEntity<Map<String, Object>> response = ruleController.countLast();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().get("total"));
    }

    @Test
    void listByMethod_ReturnsListOfRulesByMethod() {
        RuleService ruleService = mock(RuleService.class);
        RuleController ruleController = new RuleController();
        ReflectionTestUtils.setField(ruleController, "ruleService", ruleService);
        RuleDTO ruleDTO = new RuleDTO();
        when(ruleService.listByMethod("method")).thenReturn(List.of(ruleDTO));

        ResponseEntity<Map<String, Object>> response = ruleController.listByMethod("method");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(((List<?>) response.getBody().get("rules")).isEmpty());
        Assertions.assertEquals(ruleDTO, ((List<?>) response.getBody().get("rules")).get(0));
    }

    @Test
    void listByAccess_ReturnsListOfRulesByAccess() {
        RuleService ruleService = mock(RuleService.class);
        RuleController ruleController = new RuleController();
        ReflectionTestUtils.setField(ruleController, "ruleService", ruleService);
        RuleDTO ruleDTO = new RuleDTO();
        when(ruleService.listByAccess("access")).thenReturn(List.of(ruleDTO));

        ResponseEntity<Map<String, Object>> response = ruleController.listByAccess("access");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(((List<?>) response.getBody().get("rules")).isEmpty());
        Assertions.assertEquals(ruleDTO, ((List<?>) response.getBody().get("rules")).get(0));
    }

    @Test
    void getRule_ReturnsRule_WhenRuleExists() {
        RuleService ruleService = mock(RuleService.class);
        RuleController ruleController = new RuleController();
        ReflectionTestUtils.setField(ruleController, "ruleService", ruleService);
        RuleDTO ruleDTO = new RuleDTO();
        when(ruleService.getRule(1L)).thenReturn(ruleDTO);

        ResponseEntity<Map<String, Object>> response = ruleController.getRule(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ruleDTO, response.getBody().get("rule"));
    }

    @Test
    void getRule_ReturnsNotFound_WhenRuleDoesNotExist() {
        RuleService ruleService = mock(RuleService.class);
        RuleController ruleController = new RuleController();
        ReflectionTestUtils.setField(ruleController, "ruleService", ruleService);
        when(ruleService.getRule(1L)).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = ruleController.getRule(1L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("La regla con ID 1 no existe", response.getBody().get("mensaje"));
    }

    @Test
    void addRule_CreatesNewRule() {
        UpdateRules updateRules = mock(UpdateRules.class);
        RuleController ruleController = new RuleController();
        ReflectionTestUtils.setField(ruleController, "updateRules", updateRules);
        RuleDTO ruleDTO = new RuleDTO();
        when(updateRules.insertRule(ruleDTO)).thenReturn(ruleDTO);

        ResponseEntity<Map<String, Object>> response = ruleController.addRule(ruleDTO);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("Regla creada", response.getBody().get("mensaje"));
        Assertions.assertEquals(ruleDTO, response.getBody().get("rule"));
    }

    @Test
    void updateRule_UpdatesExistingRule() {
        UpdateRules updateRules = mock(UpdateRules.class);
        RuleController ruleController = new RuleController();
        ReflectionTestUtils.setField(ruleController, "updateRules", updateRules);
        RuleDTO ruleDTO = new RuleDTO();
        when(updateRules.updateRule(1L, ruleDTO)).thenReturn(ruleDTO);

        ResponseEntity<Map<String, Object>> response = ruleController.updateRule(1L, ruleDTO);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Regla actualizada", response.getBody().get("mensaje"));
        Assertions.assertEquals(ruleDTO, response.getBody().get("rule"));
    }

    @Test
    void deleteRule_DeletesExistingRule() {
        RuleService ruleService = mock(RuleService.class);
        RuleController ruleController = new RuleController();
        ReflectionTestUtils.setField(ruleController, "ruleService", ruleService);

        ResponseEntity<Map<String, Object>> response = ruleController.deleteRule(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Regla borrada", response.getBody().get("mensaje"));
    }
}
