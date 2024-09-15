package net.bounceme.chronos.rulemanager.controller;

import net.bounceme.chronos.app.usuarios.commons.dto.RoleDTO;
import net.bounceme.chronos.rulemanager.service.RoleService;
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
class RoleControllerTest {

    @Test
    void list_ReturnsListOfRoleDTOs_WhenRolesExist() {
        RoleService roleService = mock(RoleService.class);
        RoleController roleController = new RoleController();
        ReflectionTestUtils.setField(roleController, "roleService", roleService);
        RoleDTO roleDTO = new RoleDTO();
        when(roleService.listAll()).thenReturn(List.of(roleDTO));

        ResponseEntity<Map<String, Object>> response = roleController.list();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertFalse(((List<?>) response.getBody().get("roles")).isEmpty());
        Assertions.assertEquals(roleDTO, ((List<?>) response.getBody().get("roles")).get(0));
    }

    @Test
    void list_ReturnsEmptyList_WhenNoRolesExist() {
        RoleService roleService = mock(RoleService.class);
        RoleController roleController = new RoleController();
        ReflectionTestUtils.setField(roleController, "roleService", roleService);
        when(roleService.listAll()).thenReturn(Collections.emptyList());

        ResponseEntity<Map<String, Object>> response = roleController.list();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(((List<?>) response.getBody().get("roles")).isEmpty());
    }
}
