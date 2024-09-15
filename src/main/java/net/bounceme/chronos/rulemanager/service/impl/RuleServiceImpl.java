package net.bounceme.chronos.rulemanager.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.app.usuarios.commons.dto.RuleDTO;
import net.bounceme.chronos.app.usuarios.commons.model.Role;
import net.bounceme.chronos.app.usuarios.commons.model.Rule;
import net.bounceme.chronos.rulemanager.repository.RuleRepository;
import net.bounceme.chronos.rulemanager.service.RuleService;

@Service
@Slf4j
public class RuleServiceImpl implements RuleService {

	@Autowired
	private RuleRepository ruleRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(readOnly = true)
	//@Cacheable("rules")
	public List<RuleDTO> listAll() {
		log.info("get rules");
		
		List<Rule> rules = ruleRepository.findAll();
		return CollectionUtils.isNotEmpty(rules) ? rules.stream()
				.map(rule -> modelMapper.map(rule, RuleDTO.class)).collect(Collectors.toList())
				: Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	//@Cacheable("rules")
	public List<RuleDTO> listByMethod(String method) {
		log.info("get rules by method {}", method);
		
		List<Rule> rules = ruleRepository.getByMethod(method);
		return CollectionUtils.isNotEmpty(rules) ? rules.stream()
				.map(rule -> modelMapper.map(rule, RuleDTO.class)).collect(Collectors.toList())
				: Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	//@Cacheable("rules")
	public List<RuleDTO> listByAccess(String access) {
		log.info("get rules by access {}", access);
		
		List<Rule> rules = ruleRepository.getByAccess(access);
		return CollectionUtils.isNotEmpty(rules) ? rules.stream()
				.map(rule -> modelMapper.map(rule, RuleDTO.class)).collect(Collectors.toList())
				: Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	//@Cacheable("rules")
	public RuleDTO getRule(Long id) {
		Optional<Rule> oRule = ruleRepository.findById(id);
		
		return oRule.map(rule -> modelMapper.map(rule, RuleDTO.class)).orElse(null);
	}

	@Override
	@Transactional
	//@CacheEvict(value = "rules", key = "#id")
	public void deleteRule(Long id) {
		Optional<Rule> oRule = ruleRepository.findById(id);
		
		if (oRule.isPresent()) {
			Rule rule = oRule.get();
			List<Role> roles = rule.getRoles();
			
			// TODO - Esta parte no tiene cobertura (no tiene prueba)
			if (CollectionUtils.isNotEmpty(roles)) {
				roles.clear();
			}
			
			/** 
			 * El test ha fallado porque se llamaba a deleteById
			 * indistintamente de si existía la regla o no
			 */
			ruleRepository.deleteById(id);
		}
		
		// ruleRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	//@Cacheable("rules")
	public List<RuleDTO> listByDate(Date from, Date to) {
		List<Rule> rules = ruleRepository.getByCreationDate(from, to);
		return CollectionUtils.isNotEmpty(rules) ? rules.stream()
				.map(rule -> modelMapper.map(rule, RuleDTO.class)).collect(Collectors.toList())
				: Collections.emptyList();
	}

}
