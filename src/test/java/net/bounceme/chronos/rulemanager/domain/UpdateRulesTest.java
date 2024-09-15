package net.bounceme.chronos.rulemanager.domain;

import static org.mockito.Mockito.when;

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

import net.bounceme.chronos.app.usuarios.commons.dto.AccessDTO;
import net.bounceme.chronos.app.usuarios.commons.dto.MethodDTO;
import net.bounceme.chronos.app.usuarios.commons.dto.RuleDTO;
import net.bounceme.chronos.app.usuarios.commons.model.Access;
import net.bounceme.chronos.app.usuarios.commons.model.Method;
import net.bounceme.chronos.app.usuarios.commons.model.Rule;
import net.bounceme.chronos.rulemanager.exception.IncorrectRuleException;
import net.bounceme.chronos.rulemanager.repository.AccessRepository;
import net.bounceme.chronos.rulemanager.repository.MethodRepository;
import net.bounceme.chronos.rulemanager.repository.RoleRepository;
import net.bounceme.chronos.rulemanager.repository.RuleRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UpdateRulesTest {

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AccessRepository accessRepository;

    @Mock
    private MethodRepository methodRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UpdateRules updateRules;

    @Test
    void insertRule_SavesAndReturnsRuleDTO_WhenValidRuleDTOProvided() {
        RuleDTO ruleDTO = new RuleDTO();
        ruleDTO.setPath("/api/resource");
        ruleDTO.setMethod(new MethodDTO(1L, "GET"));
        ruleDTO.setAccess(new AccessDTO(1L, "ADMIN"));
        
        Access access = new Access(1L, "ADMIN");
        
        /*
         * Se han informado todos los campos de la regla porque el Copilot
         * no sabe exactamente qué datos debe de informar para que se cumpla el caso
         */
        Rule rule = new Rule();
        rule.setPath("/api/resource");
        rule.setMethod(new Method(1L, "GET"));
        rule.setAccess(new Access(1L, "ADMIN"));
        
        when(modelMapper.map(ruleDTO, Rule.class)).thenReturn(rule);
        
        /*
         * Se han añadido estas dos llamadas porque el Copilot
         * no sabe exactamente qué es lo que deben devolver las llamadas internas
         * a findall y findById
         */
        when(accessRepository.findById(1L)).thenReturn(Optional.of(access));
        when(accessRepository.findAll()).thenReturn(List.of(access));
        
        when(ruleRepository.save(rule)).thenReturn(rule);
        when(modelMapper.map(rule, RuleDTO.class)).thenReturn(ruleDTO);

        RuleDTO result = updateRules.insertRule(ruleDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(ruleDTO, result);
    }

    @Test
    void insertRule_ThrowsIncorrectRuleException_WhenRuleAlreadyExists() {
        RuleDTO ruleDTO = new RuleDTO();
        ruleDTO.setPath("/api/resource");
        ruleDTO.setMethod(new MethodDTO(1L, "GET"));
        ruleDTO.setAccess(new AccessDTO(1L, "ADMIN"));
        
        Method method = new Method(1L, "GET");
        Access access = new Access(1L, "ADMIN");
        Rule existingRule = new Rule();
        existingRule.setPath("/api/resource");
        existingRule.setMethod(method);
        existingRule.setAccess(access);
        
        when(methodRepository.findById(1L)).thenReturn(Optional.of(method));
        when(accessRepository.findById(1L)).thenReturn(Optional.of(access));
        when(ruleRepository.findRule("/api/resource", method, access)).thenReturn(existingRule);

        Assertions.assertThrows(IncorrectRuleException.class, () -> updateRules.insertRule(ruleDTO));
    }

    @Test
    void updateRule_UpdatesAndReturnsRuleDTO_WhenValidIdAndNewDataProvided() {
        Long idRule = 1L;
        RuleDTO newDataDTO = new RuleDTO();
        newDataDTO.setPath("/api/resource");
        newDataDTO.setMethod(new MethodDTO(1L, "GET"));
        newDataDTO.setAccess(new AccessDTO(1L, "ADMIN"));
        
        Method method = new Method(1L, "GET");
        Access access = new Access(1L, "ADMIN");
        Rule rule = new Rule();
        rule.setPath("/api/resource");
        rule.setMethod(method);
        rule.setAccess(access);
        
        when(ruleRepository.findById(idRule)).thenReturn(Optional.of(rule));
        when(methodRepository.findById(1L)).thenReturn(Optional.of(method));
        when(accessRepository.findById(1L)).thenReturn(Optional.of(access));
        when(accessRepository.findAll()).thenReturn(List.of(access));
        when(modelMapper.map(rule, RuleDTO.class)).thenReturn(newDataDTO);

        RuleDTO result = updateRules.updateRule(idRule, newDataDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(newDataDTO, result);
    }

    @Test
    void updateRule_ReturnsNull_WhenRuleIdDoesNotExist() {
        Long idRule = 1L;
        RuleDTO newDataDTO = new RuleDTO();
        newDataDTO.setMethod(new MethodDTO(1L, "GET"));
        newDataDTO.setAccess(new AccessDTO(1L, "ADMIN"));
        
        Access access = new Access(1L, "ADMIN");
        when(accessRepository.findAll()).thenReturn(List.of(access));
        when(ruleRepository.findById(idRule)).thenReturn(Optional.empty());

        RuleDTO result = updateRules.updateRule(idRule, newDataDTO);

        Assertions.assertNull(result);
    }
}
