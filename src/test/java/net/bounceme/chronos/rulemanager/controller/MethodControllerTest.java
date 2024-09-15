package net.bounceme.chronos.rulemanager.controller;

import net.bounceme.chronos.app.usuarios.commons.dto.MethodDTO;
import net.bounceme.chronos.rulemanager.service.MethodService;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MethodControllerTest {

    @Test
    void list_ReturnsListOfMethodDTOs_WhenMethodsExist() {
        MethodService methodService = mock(MethodService.class);
        MethodController methodController = new MethodController();
        ReflectionTestUtils.setField(methodController, "methodService", methodService);
        MethodDTO methodDTO = new MethodDTO();
        when(methodService.listAll()).thenReturn(List.of(methodDTO));

        ResponseEntity<Map<String, Object>> response = methodController.list();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(((List<?>) response.getBody().get("methods")).isEmpty());
        Assertions.assertEquals(methodDTO, ((List<?>) response.getBody().get("methods")).get(0));
    }

    @Test
    void list_ReturnsEmptyList_WhenNoMethodsExist() {
        MethodService methodService = mock(MethodService.class);
        MethodController methodController = new MethodController();
        ReflectionTestUtils.setField(methodController, "methodService", methodService);
        when(methodService.listAll()).thenReturn(Collections.emptyList());

        ResponseEntity<Map<String, Object>> response = methodController.list();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(((List<?>) response.getBody().get("methods")).isEmpty());
    }
}
