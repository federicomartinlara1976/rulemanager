package net.bounceme.chronos.rulemanager.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.app.usuarios.commons.dto.RuleDTO;
import net.bounceme.chronos.app.usuarios.commons.model.Role;
import net.bounceme.chronos.app.usuarios.commons.model.Rule;
import net.bounceme.chronos.app.usuarios.commons.repository.RuleRepository;
import net.bounceme.chronos.rulemanager.service.RuleService;

@Service
@Slf4j
public class RuleServiceImpl implements RuleService {

	private RuleRepository ruleRepository;
	
	private ModelMapper modelMapper;
	
	public RuleServiceImpl(RuleRepository ruleRepository, ModelMapper modelMapper) {
		this.ruleRepository = ruleRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RuleDTO> listAll() {
		log.info("get rules");
		
		return ruleRepository.findAll().stream()
				.map(rule -> modelMapper.map(rule, RuleDTO.class)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<RuleDTO> listByMethod(String method) {
		log.info("get rules by method {}", method);
		
		return ruleRepository.getByMethod(method).stream()
				.map(rule -> modelMapper.map(rule, RuleDTO.class)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<RuleDTO> listByAccess(String access) {
		log.info("get rules by access {}", access);
		
		return ruleRepository.getByAccess(access).stream()
				.map(rule -> modelMapper.map(rule, RuleDTO.class)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public RuleDTO getRule(Long id) {
		return ruleRepository.findById(id).map(rule -> modelMapper.map(rule, RuleDTO.class)).orElse(null);
	}

	@Override
	@Transactional
	public void deleteRule(Long id) {
		Optional<Rule> oRule = ruleRepository.findById(id);
		
		if (oRule.isPresent()) {
			Rule rule = oRule.get();
			List<Role> roles = rule.getRoles();
			
			if (CollectionUtils.isNotEmpty(roles)) {
				roles.clear();
			}
			
			/** 
			 * El test ha fallado porque se llamaba a deleteById
			 * indistintamente de si existía la regla o no
			 */
			ruleRepository.deleteById(id);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<RuleDTO> listByDate(Date from, Date to) {
		return ruleRepository.getByCreationDate(from, to).stream()
				.map(rule -> modelMapper.map(rule, RuleDTO.class)).toList();
	}

}
