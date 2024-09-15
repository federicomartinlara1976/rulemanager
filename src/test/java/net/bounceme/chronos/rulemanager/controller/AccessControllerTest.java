package net.bounceme.chronos.rulemanager.controller;

import net.bounceme.chronos.app.usuarios.commons.dto.AccessDTO;
import net.bounceme.chronos.rulemanager.exception.IncorrectRuleException;
import net.bounceme.chronos.rulemanager.service.AccessService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccessControllerTest {

    @Test
    void list_ReturnsListOfAccessDTOs_WhenAccessesExist() {
        AccessService accessService = mock(AccessService.class);
        AccessController accessController = new AccessController();
        ReflectionTestUtils.setField(accessController, "accessService", accessService);
        AccessDTO accessDTO = new AccessDTO();
        when(accessService.listAll()).thenReturn(List.of(accessDTO));

        ResponseEntity<Map<String, Object>> response = accessController.list();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(((List<?>) Objects.requireNonNull(response.getBody()).get("accesses")).isEmpty());
        Assertions.assertEquals(accessDTO, ((List<?>) response.getBody().get("accesses")).get(0));
    }

    @Test
    void list_ReturnsEmptyList_WhenNoAccessesExist() {
        AccessService accessService = mock(AccessService.class);
        AccessController accessController = new AccessController();
        ReflectionTestUtils.setField(accessController, "accessService", accessService);
        when(accessService.listAll()).thenReturn(Collections.emptyList());

        ResponseEntity<Map<String, Object>> response = accessController.list();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(((List<?>) response.getBody().get("accesses")).isEmpty());
    }
}
