package net.bounceme.chronos.rulemanager.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import net.bounceme.chronos.app.usuarios.commons.dto.RuleDTO;
import net.bounceme.chronos.app.usuarios.commons.model.Role;
import net.bounceme.chronos.app.usuarios.commons.model.Rule;
import net.bounceme.chronos.rulemanager.repository.RuleRepository;
import net.bounceme.chronos.rulemanager.service.impl.RuleServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RuleServiceTest {

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RuleServiceImpl ruleServiceImpl;

    @Test
    void listAll_ReturnsListOfRuleDTOs_WhenRulesExist() {
        Rule rule = new Rule();
        RuleDTO ruleDTO = new RuleDTO();
        when(ruleRepository.findAll()).thenReturn(List.of(rule));
        when(modelMapper.map(rule, RuleDTO.class)).thenReturn(ruleDTO);

        List<RuleDTO> result = ruleServiceImpl.listAll();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(ruleDTO, result.get(0));
    }

    @Test
    void listAll_ReturnsEmptyList_WhenNoRulesExist() {
        when(ruleRepository.findAll()).thenReturn(Collections.emptyList());

        List<RuleDTO> result = ruleServiceImpl.listAll();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void listByMethod_ReturnsListOfRuleDTOs_WhenRulesExistForMethod() {
        String method = "GET";
        Rule rule = new Rule();
        RuleDTO ruleDTO = new RuleDTO();
        when(ruleRepository.getByMethod(method)).thenReturn(List.of(rule));
        when(modelMapper.map(rule, RuleDTO.class)).thenReturn(ruleDTO);

        List<RuleDTO> result = ruleServiceImpl.listByMethod(method);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(ruleDTO, result.get(0));
    }

    @Test
    void listByMethod_ReturnsEmptyList_WhenNoRulesExistForMethod() {
        String method = "GET";
        when(ruleRepository.getByMethod(method)).thenReturn(Collections.emptyList());

        List<RuleDTO> result = ruleServiceImpl.listByMethod(method);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void listByAccess_ReturnsListOfRuleDTOs_WhenRulesExistForAccess() {
        String access = "ADMIN";
        Rule rule = new Rule();
        RuleDTO ruleDTO = new RuleDTO();
        when(ruleRepository.getByAccess(access)).thenReturn(List.of(rule));
        when(modelMapper.map(rule, RuleDTO.class)).thenReturn(ruleDTO);

        List<RuleDTO> result = ruleServiceImpl.listByAccess(access);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(ruleDTO, result.get(0));
    }

    @Test
    void listByAccess_ReturnsEmptyList_WhenNoRulesExistForAccess() {
        String access = "ADMIN";
        when(ruleRepository.getByAccess(access)).thenReturn(Collections.emptyList());

        List<RuleDTO> result = ruleServiceImpl.listByAccess(access);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void getRule_ReturnsRuleDTO_WhenRuleExists() {
        Long id = 1L;
        Rule rule = new Rule();
        RuleDTO ruleDTO = new RuleDTO();
        when(ruleRepository.findById(id)).thenReturn(Optional.of(rule));
        when(modelMapper.map(rule, RuleDTO.class)).thenReturn(ruleDTO);

        RuleDTO result = ruleServiceImpl.getRule(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(ruleDTO, result);
    }

    @Test
    void getRule_ReturnsNull_WhenRuleDoesNotExist() {
        Long id = 1L;
        when(ruleRepository.findById(id)).thenReturn(Optional.empty());

        RuleDTO result = ruleServiceImpl.getRule(id);

        Assertions.assertNull(result);
    }

    @Test
    void deleteRule_RemovesRule_WhenRuleExists() {
        Long id = 1L;
        Rule rule = new Rule();
        
        Role role = new Role();
        
        /*
         * No usar List.of(), ya que da lugar a colecciones inmutables que no se pueden borrar
         */
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        rule.setRoles(roles);
        
        Optional<Rule> oRule = Optional.of(rule);
        
        when(ruleRepository.findById(id)).thenReturn(oRule);

        ruleServiceImpl.deleteRule(id);

        verify(ruleRepository, times(1)).deleteById(id);
    }
    
    @Test
    void deleteRule_RemovesRule_WhenRuleExistsAndRolesIsEmpty() {
        Long id = 1L;
        Rule rule = new Rule();
        
        List<Role> roles = new ArrayList<>();
        rule.setRoles(roles);
        
        Optional<Rule> oRule = Optional.of(rule);
        
        when(ruleRepository.findById(id)).thenReturn(oRule);

        ruleServiceImpl.deleteRule(id);

        verify(ruleRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteRule_DoesNothing_WhenRuleDoesNotExist() {
        Long id = 1L;
        when(ruleRepository.findById(id)).thenReturn(Optional.empty());

        ruleServiceImpl.deleteRule(id);

        verify(ruleRepository, times(0)).deleteById(id);
    }

    @Test
    void listByDate_ReturnsListOfRuleDTOs_WhenRulesExistInDateRange() {
        Date from = new Date();
        Date to = new Date();
        Rule rule = new Rule();
        RuleDTO ruleDTO = new RuleDTO();
        when(ruleRepository.getByCreationDate(from, to)).thenReturn(List.of(rule));
        when(modelMapper.map(rule, RuleDTO.class)).thenReturn(ruleDTO);

        List<RuleDTO> result = ruleServiceImpl.listByDate(from, to);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(ruleDTO, result.get(0));
    }

    @Test
    void listByDate_ReturnsEmptyList_WhenNoRulesExistInDateRange() {
        Date from = new Date();
        Date to = new Date();
        when(ruleRepository.getByCreationDate(from, to)).thenReturn(Collections.emptyList());

        List<RuleDTO> result = ruleServiceImpl.listByDate(from, to);

        Assertions.assertTrue(result.isEmpty());
    }
}
