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

import net.bounceme.chronos.app.usuarios.commons.dto.AccessDTO;
import net.bounceme.chronos.app.usuarios.commons.model.Access;
import net.bounceme.chronos.rulemanager.repository.AccessRepository;
import net.bounceme.chronos.rulemanager.service.impl.AccessServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccessServiceTest {

    @Mock
    private AccessRepository accessRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AccessServiceImpl accessServiceImpl;

    @Test
    void listAll_ReturnsListOfAccessDTOs_WhenAccessesExist() {
        Access access = new Access();
        AccessDTO accessDTO = new AccessDTO();
        when(accessRepository.findAll()).thenReturn(List.of(access));
        when(modelMapper.map(access, AccessDTO.class)).thenReturn(accessDTO);

        List<AccessDTO> result = accessServiceImpl.listAll();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(accessDTO, result.get(0));
    }

    @Test
    void listAll_ReturnsEmptyList_WhenNoAccessesExist() {
        when(accessRepository.findAll()).thenReturn(Collections.emptyList());

        List<AccessDTO> result = accessServiceImpl.listAll();

        Assertions.assertTrue(result.isEmpty());
    }
}
