package net.bounceme.chronos.rulemanager.services;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import net.bounceme.chronos.app.usuarios.commons.dto.RoleDTO;
import net.bounceme.chronos.app.usuarios.commons.model.Role;
import net.bounceme.chronos.rulemanager.repository.RoleRepository;
import net.bounceme.chronos.rulemanager.service.impl.RoleServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    @Test
    void listAll_ReturnsListOfRoleDTOs_WhenRolesExist() {
        Role role = new Role();
        RoleDTO roleDTO = new RoleDTO();
        when(roleRepository.findAll()).thenReturn(List.of(role));
        when(modelMapper.map(role, RoleDTO.class)).thenReturn(roleDTO);

        List<RoleDTO> result = roleServiceImpl.listAll();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(roleDTO, result.get(0));
    }

    @Test
    void listAll_ReturnsEmptyList_WhenNoRolesExist() {
        when(roleRepository.findAll()).thenReturn(Collections.emptyList());

        List<RoleDTO> result = roleServiceImpl.listAll();

        Assertions.assertTrue(result.isEmpty());
    }
}
