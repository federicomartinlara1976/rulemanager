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

import net.bounceme.chronos.app.usuarios.commons.dto.MethodDTO;
import net.bounceme.chronos.app.usuarios.commons.model.Method;
import net.bounceme.chronos.rulemanager.repository.MethodRepository;
import net.bounceme.chronos.rulemanager.service.impl.MethodServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MethodServiceTest {

    @Mock
    private MethodRepository methodRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MethodServiceImpl methodServiceImpl;

    @Test
    void listAll_ReturnsListOfMethodDTOs_WhenMethodsExist() {
        Method method = new Method();
        MethodDTO methodDTO = new MethodDTO();
        when(methodRepository.findAll()).thenReturn(List.of(method));
        when(modelMapper.map(method, MethodDTO.class)).thenReturn(methodDTO);

        List<MethodDTO> result = methodServiceImpl.listAll();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(methodDTO, result.get(0));
    }

    @Test
    void listAll_ReturnsEmptyList_WhenNoMethodsExist() {
        when(methodRepository.findAll()).thenReturn(Collections.emptyList());

        List<MethodDTO> result = methodServiceImpl.listAll();

        Assertions.assertTrue(result.isEmpty());
    }
}
